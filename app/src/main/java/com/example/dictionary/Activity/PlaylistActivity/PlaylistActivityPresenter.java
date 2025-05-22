package com.example.dictionary.Activity.PlaylistActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.ApiService.RealPathUtil;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivityPresenter {
    PlaylistActivityInterface playlistActivityInterface;
    int request_code=101;
    Playlist playlist;
    Dialog dialog;
    public PlaylistActivityPresenter(PlaylistActivityInterface playlistActivityInterface) {
        this.playlistActivityInterface = playlistActivityInterface;
    }
    public void onInit(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            assert bundle != null;
            playlist=bundle.getSerializable(MyApplication.SONG, Playlist.class);
            assert playlist != null;
            if(!playlist.getImage().isEmpty())  {
                playlistActivityInterface.onImage(playlist.getImage());
            }
            playlistActivityInterface.onToolbar(playlist.getName());


        }
    }
    public void onListSongs(Intent intent, Context context, ItemClickListener itemClickListener){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            assert bundle != null;
            Playlist playlist=bundle.getSerializable(MyApplication.SONG,Playlist.class);
            assert playlist != null;
            ApiService.apiService.getSongsBaseOnPlaylist(playlist.getPlaylist_id()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                    RecycleAdapter recycleAdapter=new RecycleAdapter(response.body(),context,0,null,itemClickListener);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context);
                    playlistActivityInterface.onListSongs(recycleAdapter,layoutManager);
                }

                @Override
                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                }
            });
        }

    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        playlistActivityInterface.showBottomSheet(bottomFragment);
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void checkRequest(Context context) throws InterruptedException {
        dialog= LoginPresenter.dialog(context);
        if(context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
            dialog.show();
            openGallery();
        }else{
            String[] permission={Manifest.permission.READ_MEDIA_IMAGES};
            playlistActivityInterface.requestPermission(permission,request_code);
        }
    }
    private void openGallery(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        playlistActivityInterface.getLauncher().launch(Intent.createChooser(intent,"Select picture"));
    }
    public void onResponsePermissions(int requestCode,int[] grants,String[] permission){
        if(request_code == requestCode && grants[0] == PackageManager.PERMISSION_GRANTED ) openGallery();
    }
    public void getData(Intent intent,Context context){
        if(intent != null) {
            Uri uri=intent.getData();
            RequestBody playlist_id=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(playlist.getPlaylist_id()));
            File file= new File(RealPathUtil.getRealPath(context, uri));
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part part=MultipartBody.Part.createFormData("image",file.getName(),requestBody);
            ApiService.apiService.postImage(playlist_id,part).enqueue(new Callback<Playlist>() {
                @Override
                public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                    playlistActivityInterface.onImage(response.body().getImage());
                    ApiService.apiService.getPlaylists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Playlist>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                            MyApplication.playlists=response.body();
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                        }
                    });
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<Playlist> call, Throwable t) {

                }
            });
        }
    }
}
