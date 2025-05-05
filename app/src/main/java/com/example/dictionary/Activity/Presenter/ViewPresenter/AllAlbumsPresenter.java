package com.example.dictionary.Activity.Presenter.ViewPresenter;

import com.example.dictionary.Activity.Interface.View.AllAlbumsInterface;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Model.Album;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAlbumsPresenter {
    AllAlbumsInterface allAlbumsInterface;

    public AllAlbumsPresenter(AllAlbumsInterface allAlbumsInterface) {
        this.allAlbumsInterface = allAlbumsInterface;
    }
    public void onInit(){
        ApiService.apiService.getAlbums().enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                allAlbumsInterface.onInit(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

            }
        });
    }
}
