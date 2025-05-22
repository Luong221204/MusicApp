package com.example.dictionary.Activity.ContentAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.VIewSongActivity.ViewSongActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NormalHolderPresenter {
    NormalHolder normalHolder;
    ArrayList<Type> types;

    StringBuffer name=new StringBuffer();
    public NormalHolderPresenter(NormalHolder normalHolder) {
        this.normalHolder = normalHolder;
    }

    public void onInit(Behalf behalf) {
        if(behalf instanceof  Song){
            name=new StringBuffer();
            ApiService.apiService.getArtistNameForIndicatedSong(((Song) behalf).getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                    for(int i=0;i<response.body().size();i++){
                        name.append(response.body().get(i).getName()).append(" , ");
                    }
                    name.delete(name.lastIndexOf(","),name.lastIndexOf(" "));
                    normalHolder.onInit(((Song) behalf).getImage(),((Song) behalf).getName(), String.valueOf(name));
                }
                @Override
                public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                }
            });
        }
    }
    public void onTouch(Behalf song, Context context,ViewSongActivity viewSongActivity){
        for(Song song1:MyApplication.FavouriteSongs){
            if(song1.getId() == ((Song) song).getId()  ) {
                ((Song) song).setLoved(true);
            }
        }
        MyApplication.stack.push((Song) song);
        MyApplication.list.add(0, (Song) song);
        Intent intent=new Intent(context, MyService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        intent.putExtra(MyApplication.BUNDLE,bundle);
        context.startService(intent);
        Intent intent1=new Intent(context, ViewSongActivity.class);
        Bundle bundle1=new Bundle();
        bundle1.putSerializable(MyApplication.SONG,song);
        intent1.putExtra(MyApplication.BUNDLE,bundle1);
        context.startActivity(intent1);
        ApiService.apiService.getTypeOnSongId(((Song) song).getId()).enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Type>> call, @NonNull Response<ArrayList<Type>> response) {
                types=response.body();
                assert types != null;
                for(Type type1:types){
                    ApiService.apiService.getSongsBasedOnType(type1.getId()).enqueue(new Callback<ArrayList<Song>>() {
                        @Override
                        public void onResponse(@NonNull Call<ArrayList<Song>> call, @NonNull Response<ArrayList<Song>> response) {
                            MyApplication.auto=response.body();
                            Song song_temp=null;
                            if( MyApplication.auto != null &&!MyApplication.auto.isEmpty() ){
                                for(Song song:MyApplication.auto){
                                    if(song.getId()==MyApplication.song.getId()) song_temp=song  ;
                                }
                                MyApplication.auto.remove(song_temp);
                            }
                            Gson gson=new Gson();
                            JsonArray json=gson.toJsonTree(MyApplication.auto).getAsJsonArray();
                            String data=json.toString();
                            Intent intent2=new Intent(MyApplication.DATA);
                            intent2.putExtra(MyApplication.SONG,data);
                            context.sendBroadcast(intent2);
                        }
                        @Override
                        public void onFailure(@NonNull Call<ArrayList<Song>> call, @NonNull Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Type>> call, @NonNull Throwable t) {
            }
        });
        if(viewSongActivity != null) viewSongActivity.finish();
    }
}
