package com.example.dictionary.Activity.BottomAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.PlaylistActivity.PlaylistActivity;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistPresenter {
    PlaylistInterface playlistInterface;

    public PlaylistPresenter(PlaylistInterface playlistInterface) {
        this.playlistInterface = playlistInterface;
    }
    public void onInit(int source, int position, Playlist playlist, int action){
        if(action ==1 ){
            if(source == R.layout.add_playlist && playlist.getImage() == null){
                ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) playlistInterface.textView().getLayoutParams();
                marginLayoutParams.topMargin=55;
                playlistInterface.onInitForDefault(playlist,marginLayoutParams, View.GONE);
            }else if(source == R.layout.add_playlist){
                playlistInterface.onInitForReal2(playlist);
            }
            return;
        }
        if(source == R.layout.add_playlist && playlist.getImage() == null){
            ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) playlistInterface.textView().getLayoutParams();
            marginLayoutParams.topMargin=55;
            playlistInterface.onInitForDefault(playlist,marginLayoutParams, View.GONE);
        }else if(source == R.layout.add_playlist){
            playlistInterface.onInitForReal(playlist);
        }
    }
    public void showDialog(Context context){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        Window window=dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams win=window.getAttributes();//WinDowManager là của toàn bộ ứng dụng
        win.gravity= Gravity.CENTER;

        window.setAttributes(win);
        dialog.setCancelable(true);
        playlistInterface.showDialog(dialog);
    }
    public void onClickPlaylist(Context context, Playlist playlist){
        Intent intent1=new Intent(context, PlaylistActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,playlist);
        intent1.putExtra(MyApplication.BUNDLE,bundle);
        context.startActivity(intent1);
    }
    public void addPlaylist(String name, Dialog dialog, BottomsAdapter bottomsAdapter){
        Dialog dialog_progress= Objects.requireNonNull(LoginPresenter.dialog(dialog.getContext()));
        dialog.dismiss();
        dialog_progress.show();
        Playlist playlist=new Playlist(R.drawable.playlists,name);
        playlist.setUserId(MyApplication.user.getUserId());
        ApiService.apiService.postPlaylist(playlist).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                playlistInterface.showStatus(response.body().getMessage());
                MyApplication.playlists.add(response.body());
                ApiService.apiService.getPlaylists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Playlist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                        MyApplication.playlists.clear();
                        MyApplication.playlists.add(new Playlist(R.drawable.plus,"Tạo Playlist"));
                        MyApplication.playlists.addAll(response.body());
                        bottomsAdapter.setData(MyApplication.playlists);
                        dialog_progress.dismiss();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                playlistInterface.showStatus("Không có kết nối mạng");

            }
        });

    }
    public void addToPlaylist(Playlist playlist, Song song){
        ApiService.apiService.postSongToPlaylist(playlist.getPlaylist_id(),song).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                playlistInterface.showMessage("Đã thêm vào playlist");
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });
    }
}
