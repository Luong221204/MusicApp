package com.example.dictionary.Activity.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dictionary.Activity.Model.Song;

import java.util.ArrayList;

public class MyLiveData {
    private final MutableLiveData<ArrayList<Song>> songs=new MutableLiveData<>( new ArrayList<>());
    private final MutableLiveData<ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>> songsOff=new MutableLiveData<>( new ArrayList<>());

    private static MyLiveData instance;
    public static void init(){
        instance = new MyLiveData();
    }
    public static synchronized MyLiveData getInstance(){
        if(instance == null){
            instance = new MyLiveData();
        }
        return instance;
    }
    public LiveData<ArrayList<Song>> getListSongs(){
        return songs;
    }
    public void addToSongsList(Song song){
        ArrayList<Song> getSongs = MyLiveData.getInstance().songs.getValue();
        if(getSongs == null) getSongs=new ArrayList<>();
        getSongs.add(song);
        MyLiveData.getInstance().songs.setValue(getSongs);
    }
    public LiveData<ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>> getListSongsOff(){
        return songsOff;
    }
    public void addToSongsOffList(com.example.dictionary.Activity.RoomDataBase.Entity.Song song){
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> getSongs = MyLiveData.getInstance().songsOff.getValue();
        if(getSongs == null) getSongs=new ArrayList<>();
        getSongs.add(song);
        MyLiveData.getInstance().songsOff.setValue(getSongs);
    }

}
