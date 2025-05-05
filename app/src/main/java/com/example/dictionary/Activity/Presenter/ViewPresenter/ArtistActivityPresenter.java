package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ArtistActivityInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
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
    public void onInit(Bundle bundle){
        if(bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist=bundle.getSerializable(MyApplication.ARTIST,Artist.class);
            }
            assert artist != null;
            artistActivityInterface.onInit(artist.getName(),artist.getImage(),artist.getFollowed());
            ApiService.apiService.getSongsOnArtistId(artist.getId()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                    artistActivityInterface.onSongs(response.body());
                }
                @Override
                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                }
            });
            ApiService.apiService.getAlbumOnArtistId(artist.getId()).enqueue(new Callback<ArrayList<Album>>() {
                @Override
                public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                    assert response.body() != null;
                    if(!response.body().isEmpty()) artistActivityInterface.onAlbums(response.body());
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
    public void onMemberOf(Bundle bundle){
        Artist artist1=null;
        if(bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist1 = bundle.getSerializable(MyApplication.ARTIST, Artist.class);
                ApiService.apiService.getMemberOf(artist1.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        if(response.body() != null && !response.body().isEmpty()) artistActivityInterface.onMemberOf(response.body());
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                    }
                });
            }
        }
    }
    public void onAppearInSong(Bundle bundle){
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
                            artistActivityInterface.onAppearIn(response.body());
                            Random random=new Random();
                            int song_id= random.nextInt(response.body().size());
                            ApiService.apiService.getArtistNameForIndicatedSong(response.body().get(song_id).getId()).enqueue(new Callback<ArrayList<Artist>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                    assert response.body() != null;
                                    Log.d("DUCLUONG",response.body().size()+"haha ");
                                    for(Artist artist2:response.body()){
                                        if(artist2.getId() != finalArtist.getId())artist_id.append(artist2.getId()).append(",");
                                    }
                                    artist_id.deleteCharAt(artist_id.lastIndexOf(","));
                                    ApiService.apiService.getArtistIndexed(String.valueOf(artist_id)).enqueue(new Callback<ArrayList<Artist>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                            assert response.body() != null;
                                            if(!response.body().isEmpty()) artistActivityInterface.onRelateArtists(response.body());
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
    public void onMembers(Bundle bundle){
        Artist artist1=null;
        if(bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                artist1 = bundle.getSerializable(MyApplication.ARTIST, Artist.class);
                ApiService.apiService.getSingersOnGroupId(artist1.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        if(response.body() != null &&!response.body().isEmpty()) artistActivityInterface.onMembers(response.body());
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                    }
                });
            }
        }
    }
}
