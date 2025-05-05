package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Application.MyLiveData;
import com.example.dictionary.Activity.Interface.Adapter.RecycleInterface;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclePresenter {
    ArrayList<Type> types;
    StringBuffer stringBuffer;
    RecycleInterface recycleInterface;
    public  RecyclePresenter(RecycleInterface recycleInterface) {
        this.recycleInterface=recycleInterface;
    }
    public void onInitMode(int mode,int position){
        if(mode ==1 ){
            recycleInterface.onInitMode(View.VISIBLE,View.VISIBLE,View.GONE,View.GONE,1F);
        }else if(mode ==2 ){
            recycleInterface.onInitMode(View.GONE,View.GONE,View.VISIBLE,View.GONE,1F);
        }else if(mode ==3){
            if(position==0) recycleInterface.onInitMode(View.GONE,View.GONE,View.GONE,View.VISIBLE,0.85F);
            else recycleInterface.onInitMode(View.GONE,View.GONE,View.GONE,View.GONE,0.85F);

        }
    }
    public void onInit(Song song){
        ApiService.apiService.getArtistNameForIndicatedSong(song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                ArrayList<Artist> artists=response.body();
                stringBuffer=new StringBuffer();
                assert artists != null;
                for(Artist artist:artists){
                    stringBuffer.append(artist.getName()).append(" , ");
                }
                stringBuffer.delete(stringBuffer.lastIndexOf(","),stringBuffer.lastIndexOf(" "));
                recycleInterface.onInit(String.valueOf(stringBuffer),song.getName(),song.getImage());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Artist>> call, Throwable t) {

            }
        });
    }
    public void sendBroadcast(Context context, RecycleAdapter recycleAdapter, Song song){
        MyLiveData.getInstance().addToSongsList(song);
        MyApplication.auto.remove(song);
        Intent intent2=new Intent(MyApplication.AGAIN);
        context.sendBroadcast(intent2);
        recycleAdapter.updateData(MyApplication.auto);
    }
    public void sendBroadcast2(Context context, ViewSongActivity viewSongActivity, Song song){
        MyApplication.stack.push(song);
        MyApplication.list.add(0,song);
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
        ApiService.apiService.getTypeOnSongId(song.getId()).enqueue(new Callback<ArrayList<Type>>() {
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
