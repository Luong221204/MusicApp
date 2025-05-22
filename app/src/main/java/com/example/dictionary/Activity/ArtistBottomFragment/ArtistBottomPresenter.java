package com.example.dictionary.Activity.ArtistBottomFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.ArtistBottomAdapter.ArtistBottomAdapter;
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
    public void onInit(Bundle bundle, Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            Song song=bundle.getSerializable(MyApplication.SONG,Song.class);
            if(song != null){
                ApiService.apiService.getArtistNameForIndicatedSong(song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                        ArtistBottomAdapter optionAdapter=new ArtistBottomAdapter(context, response.body());
                        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
                        artistBottomFragmentInterface.onInit(optionAdapter,layoutManager);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                    }
                });

            }
        }
    }
}
