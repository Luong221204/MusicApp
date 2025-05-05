package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.os.Bundle;
import android.util.Log;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ArtistBottomFragmentInterface;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistBottomPresenter {
    ArtistBottomFragmentInterface artistBottomFragmentInterface;
    public ArtistBottomPresenter(ArtistBottomFragmentInterface artistBottomFragmentInterface){
        this.artistBottomFragmentInterface=artistBottomFragmentInterface;
    }
    public void onInit(Bundle bundle){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Song song=bundle.getSerializable(MyApplication.SONG,Song.class);
            if(song != null){
                ApiService.apiService.getArtistNameForIndicatedSong(song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        artistBottomFragmentInterface.onInit(response.body());
                        Log.d("DUCLUONG",response.body().size()+" ");

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                    }
                });

            }
        }
    }
}
