package com.example.dictionary.Activity.AlbumActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.VIewSongActivity.ViewSongActivity;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivityPresenter {
    ArrayList<Song> songs;
    Album album;
    private final AlbumActivityInterface activityInterface;
    public AlbumActivityPresenter(AlbumActivityInterface activityInterface){
        this.activityInterface=activityInterface;
    }
    public void setAlbumTitle(Intent intent, Context context, ItemClickListener itemClickListener){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            album = Objects.requireNonNull(intent.getBundleExtra(MyApplication.BUNDLE)).getSerializable(MyApplication.ACTION, Album.class);
            if(MyDatabase.getInstance(context).userDAO().checkAlbum(MyApplication.user.getUserId(),album.getId()) == null){
                ViewFragmentPresenter.onDownloadAlbum(context,MyApplication.PICTURE,album);
            }
            assert album != null;
            activityInterface.setToolbarTitle(album.getName());
            for(Album album1:MyApplication.FavouriteAlbums){
                if(album1.getId()==album.getId()){
                    activityInterface.setLoved(R.drawable.heart);
                }
            }
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
                    RecycleAdapter recycleAdapter=new RecycleAdapter(songs, context,0,null,itemClickListener);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context);
                    activityInterface.setRecycleView(recycleAdapter,layoutManager);

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

    } public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        activityInterface.showBottomSheet(bottomFragment);
    }
    public void sendFavourite(){
        activityInterface.setLoved(album.isLoved()? R.drawable.like:R.drawable.heart);
        ApiService.apiService.albumLoveOrNot(MyApplication.user.getUserId(),album.getId()).enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                ApiService.apiService.getFavouriteAlbums(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Album>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                        MyApplication.FavouriteAlbums.clear();
                        MyApplication.FavouriteAlbums=response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });
    }
}
