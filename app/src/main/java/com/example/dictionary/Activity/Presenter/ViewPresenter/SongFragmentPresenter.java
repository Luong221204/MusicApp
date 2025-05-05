package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.SongFragmentInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

public class SongFragmentPresenter {
    SongFragmentInterface songFragmentInterface;
    public SongFragmentPresenter(SongFragmentInterface searchFragmentInterface){
        this.songFragmentInterface=searchFragmentInterface;
    }
    public void onInit(Intent intent) throws JSONException {
        String data=intent.getStringExtra(MyApplication.SONG);
        songFragmentInterface.onRecycleView(getSongs(data));
    }
    private ArrayList<Song> getSongs(String data) throws JSONException {
        ArrayList<Song> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(data, new TypeToken<ArrayList<Song>>() {}.getType());

        return list;
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        songFragmentInterface.showBottomSheet(bottomFragment);
    }
    public void onHistory(Context context){
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context.getApplicationContext()).userDAO().getAllSongs();
        songFragmentInterface.onRecycleViewHistory(songs);
    }
    public void onRecycle(){
        ArrayList<Song> songs=new ArrayList<>();
        for(int i=0;i<MyApplication.behalves.size();i++){
            if(MyApplication.behalves.get(i) instanceof Song) songs.add((Song) MyApplication.behalves.get(i));
        }
        songFragmentInterface.onRecycleView(songs);
    }
}
