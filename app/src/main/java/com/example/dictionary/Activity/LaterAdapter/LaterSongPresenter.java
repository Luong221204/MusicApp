package com.example.dictionary.Activity.LaterAdapter;

import android.content.Context;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaterSongPresenter {
    LaterSongInterface laterSongInterface;
    public LaterSongPresenter(LaterSongInterface laterSongInterface){
        this.laterSongInterface=laterSongInterface;
    }
    public void onInit(Object object, Context context){
        if(object instanceof Song){
            ApiService.apiService.getSongOnSongId(((Song)object).getId()).enqueue(new Callback<Song>() {
                @Override
                public void onResponse(Call<Song> call, Response<Song> response) {
                    if(response.body() != null){
                        ((Song)object).setUrl(response.body().getUrl());
                    }
                    if(((Song)object).getUrl() == null){
                        laterSongInterface.onInitOff(((Song) object).getImage(),
                                ((Song) object).getName());
                    }else{
                        laterSongInterface.onInit(((Song) object).getImage(),
                                ((Song) object).getName());
                    }
                }
                @Override
                public void onFailure(Call<Song> call, Throwable t) {
                    ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> oldSongs=
                            (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getDownloaded(MyApplication.user.getUserId(),((Song)object).getId());
                    if( oldSongs.size()==1) {
                        ((Song)object).setUrl(oldSongs.get(0).getUrl());
                    }
                    if(((Song)object).getUrl() == null){
                        laterSongInterface.onInitOff(((Song) object).getImage(),
                                ((Song) object).getName());
                    }else{
                        laterSongInterface.onInitOffButDownloaded(oldSongs.get(0));
                    }
                }
            });

        }
    }

}
