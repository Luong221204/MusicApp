package com.example.dictionary.Activity.Listener;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.SearchFragment.SearchFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActionListener implements TextView.OnEditorActionListener {
    SearchFragment searchFragment;
    static ArrayList<Song> songs=new ArrayList<>();
    static ArrayList<Artist> artists=new ArrayList<>();
    public EditorActionListener(SearchFragment searchFragment){
        this.searchFragment=searchFragment;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v==searchFragment.editText){
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                if(v.getText().toString().isEmpty()){

                    return true;
                }
                searchFragment.searchFragmentPresenter.showOrHideSearch(v,1,searchFragment.getContext());
                MyApplication.behalves.clear();
                searchAlbums(v.getText().toString());
                searchSongs(v.getText().toString());
                searchArtist(v.getText().toString());
                handlerMessage(searchFragment.requireContext());
                return true;
            }
            return false;
        }
        return true;
    }
    public static void handlerMessage(Context context){
        MyApplication.cnt=0;
        MyApplication.handler=new Handler(MyApplication.handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){
                    songs= (ArrayList<Song>) msg.obj;
                    onSetupSongs(songs);
                    MyApplication.behalves.addAll(songs);
                    MyApplication.cnt++;
                }else if(msg.what == 1){
                    artists= (ArrayList<Artist>) msg.obj;
                    onSetupArtists(artists);
                    MyApplication.behalves.addAll(artists);
                    MyApplication.cnt++;
                }else{
                    MyApplication.albums= (ArrayList<Album>) msg.obj;
                    MyApplication.cnt++;
                }
                if(MyApplication.cnt == 3){
                    sendBroadcast(context);

                }
            }

        };

    }
    private void searchSongs(String text){
        ApiService.apiService.getSearchSongs(text).enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                Message message=Message.obtain();
                message.what=0;
                message.obj=response.body();
                MyApplication.handler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

            }
        });
    }
    private void searchArtist(String text){
        ApiService.apiService.getSearchedArtists(text).enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                Message message=Message.obtain();
                message.what=1;
                message.obj=response.body();
                MyApplication.handler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
            }
        });
    }
    private void searchAlbums(String text){
        ApiService.apiService.getSearchedAlbums(text).enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                Message message=Message.obtain();
                message.what=2;
                message.obj=response.body();
                MyApplication.handler.sendMessage(message);
            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

            }
        });
    }
    public static void onSetupSongs(ArrayList<Song> songs){
        if(!songs.isEmpty()){
            for(int i=0;i<songs.size();i++){
                songs.get(i).setType(0);
            }
        }
    }
    public static void onSetupArtists(ArrayList<Artist> artists){
        int max=0,index=0;
        if(!artists.isEmpty()){
            for(int i=0;i<artists.size();i++){
                if(artists.get(i).getFollowed()>=max) {
                    max=artists.get(i).getFollowed();
                    index=i;
                }
                artists.get(i).setType(1);
            }
            artists.get(index).setType(2);
        }
    }
    public static void sendBroadcast(Context context){
        //Collections.sort(MyApplication.behalves);
        Intent intent=new Intent(MyApplication.ACTION);
        Gson gson=new Gson();
        JsonArray jsonArray=gson.toJsonTree(MyApplication.albums).getAsJsonArray();
        String data=jsonArray.toString();
        intent.putExtra(MyApplication.DATA,data);
        context.sendBroadcast(intent);
    }
}
