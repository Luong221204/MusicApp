package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.AlbumActivityInterface;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivityPresenter {
    ArrayList<Song> songs;
    private final AlbumActivityInterface activityInterface;
    public AlbumActivityPresenter(AlbumActivityInterface activityInterface){
        this.activityInterface=activityInterface;
    }
    public void setAlbumTitle(Intent intent){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Album album = Objects.requireNonNull(intent.getBundleExtra(MyApplication.BUNDLE)).getSerializable(MyApplication.ACTION, Album.class);
            assert album != null;
            activityInterface.setToolbarTitle(album.getName());
            ApiService.apiService.getArtistOnAlbumId(album.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                    ArrayList<Artist> artists = response.body();
                    assert artists != null;
                    activityInterface.setAlbumTitle(artists.get(0).getName(),album.getYear_launched(),album.getImage());
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Artist>> call, @NonNull Throwable t) {

                }
            });
            ApiService.apiService.getSongsOnAlbumId(album.getId()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Song>> call, @NonNull Response<ArrayList<Song>> response) {
                    songs=response.body();
                    activityInterface.setRecycleView(songs);

                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Song>> call, @NonNull Throwable t) {

                }
            });


        }
    }
    public void playMusic(Context context){
        Intent intent=new Intent(context, MyService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,songs.get(0));
        intent.putExtra(MyApplication.BUNDLE,bundle);
        context.startService(intent);
        Intent intent1=new Intent(context, ViewSongActivity.class);
        context.startActivity(intent1);

    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        activityInterface.showBottomSheet(bottomFragment);
    }
}
