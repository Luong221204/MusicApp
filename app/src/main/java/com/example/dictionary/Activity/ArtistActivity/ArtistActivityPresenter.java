package com.example.dictionary.Activity.ArtistActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ArtistAdapter.ArtistAdapter;
import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistActivityPresenter {
    Artist artist=null;
    ArtistActivityInterface artistActivityInterface;
    public ArtistActivityPresenter(ArtistActivityInterface artistActivityInterface){
        this.artistActivityInterface=artistActivityInterface;
    }
    public void onInit(Bundle bundle, Context context, ItemClickListener itemClickListener){
        if(bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist=bundle.getSerializable(MyApplication.ARTIST,Artist.class);
                if(MyDatabase.getInstance(context).userDAO().checkArtist(MyApplication.user.getUserId(),artist.getId())== null){
                    ViewFragmentPresenter.onDownloadArtist(context,MyApplication.PICTURE,artist);
                }
                for(Artist artist1:MyApplication.FavouriteArtists){
                    if(artist1.getId() == artist.getId()) artist.setLoved(true);
                }
            }
            assert artist != null;
            if(artist.isLoved()){
                artistActivityInterface.onFollowButton("Đã quan tâm");
            }else{
                artistActivityInterface.onFollowButton("Quan tâm");
            }
            artistActivityInterface.onInit(artist.getName(),artist.getImage(),artist.getFollowed());
            ApiService.apiService.getSongsOnArtistId(artist.getId()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                    RecycleAdapter recycleAdapter=new RecycleAdapter(response.body(),context,0,null,itemClickListener);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context);
                    artistActivityInterface.onSongs(recycleAdapter,layoutManager);
                }
                @Override
                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                }
            });
            ApiService.apiService.getAlbumOnArtistId(artist.getId()).enqueue(new Callback<ArrayList<Album>>() {
                @Override
                public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                    assert response.body() != null;
                    AlbumAdapter recycleAdapter=new AlbumAdapter(context,response.body(),0);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    if(!response.body().isEmpty()) artistActivityInterface.onAlbums(recycleAdapter,layoutManager);
                }

                @Override
                public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

                }
            });

        }
    }
    public void onChangeAppbar(AppBarLayout appBarLayout, int verticalOffset){
        if(Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
            artistActivityInterface.onSmallImage(View.VISIBLE);
        }
        else  artistActivityInterface.onSmallImage(View.GONE);
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        artistActivityInterface.showBottomSheet(bottomFragment);
    }
    public void onMemberOf(Bundle bundle,Context context){
        Artist artist1=null;
        if(bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist1 = bundle.getSerializable(MyApplication.ARTIST, Artist.class);
                ApiService.apiService.getMemberOf(artist1.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        if(response.body() != null && !response.body().isEmpty()) {
                            ArtistAdapter recycleAdapter=new ArtistAdapter(context,response.body());
                            LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                            artistActivityInterface.onMemberOf(recycleAdapter,layoutManager);
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                    }
                });
            }
        }
    }
    public void onAppearInSong(Bundle bundle, Context context, ItemClickListener itemClickListener){
        Artist artist1=null;
        StringBuffer artist_id=new StringBuffer();
        if(bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist1 = bundle.getSerializable(MyApplication.ARTIST, Artist.class);
                Artist finalArtist = artist1;
                ApiService.apiService.getAppearOnSong(artist1.getId()).enqueue(new Callback<ArrayList<Song>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                        if(response.body() != null && !response.body().isEmpty()){
                            RecycleAdapter recycleAdapter=new RecycleAdapter(response.body(),context,0,null,itemClickListener);
                            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
                            artistActivityInterface.onAppearIn(recycleAdapter,layoutManager);
                            Random random=new Random();
                            int song_id= random.nextInt(response.body().size());
                            ApiService.apiService.getArtistNameForIndicatedSong(response.body().get(song_id).getId()).enqueue(new Callback<ArrayList<Artist>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                    assert response.body() != null;
                                    for(Artist artist2:response.body()){
                                        if(artist2.getId() != finalArtist.getId())artist_id.append(artist2.getId()).append(",");
                                    }
                                    artist_id.deleteCharAt(artist_id.lastIndexOf(","));
                                    ApiService.apiService.getArtistIndexed(String.valueOf(artist_id)).enqueue(new Callback<ArrayList<Artist>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                            assert response.body() != null;
                                            if(!response.body().isEmpty()) {
                                                ArtistAdapter recycleAdapter=new ArtistAdapter(context,response.body());
                                                LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                                                artistActivityInterface.onRelateArtists(recycleAdapter,layoutManager);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                    }
                });
            }
        }
    }
    public void onMembers(Bundle bundle,Context context){
        Artist artist1=null;
        if(bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist1 = bundle.getSerializable(MyApplication.ARTIST, Artist.class);
                ApiService.apiService.getSingersOnGroupId(artist1.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        if(response.body() != null &&!response.body().isEmpty()) {
                            ArtistAdapter recycleAdapter=new ArtistAdapter(context,response.body());
                            LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                            artistActivityInterface.onMembers(recycleAdapter,layoutManager);
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                    }
                });
            }
        }
    }
    public void checkLoved(Context context){
        if(artist.isLoved()){
            artistActivityInterface.onFollowButton("Quan tâm");
            MyDatabase.getInstance(context).userDAO().updateStatusArtist(false,MyApplication.user.getUserId(),artist.getId());
        }else{
            artistActivityInterface.onFollowButton("Đã quan tâm");
            MyDatabase.getInstance(context).userDAO().updateStatusArtist(true,MyApplication.user.getUserId(),artist.getId());
        }
        ApiService.apiService.artistLoveOrNot(MyApplication.user.getUserId(),artist.getId()).enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                artistActivityInterface.onMessage(response.body().getMessage());
                ApiService.apiService.getFavouriteArtists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        MyApplication.FavouriteArtists=response.body();
                        MyApplication.Artists.setCount(MyApplication.FavouriteArtists.size());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

            }
        });
    }
}
