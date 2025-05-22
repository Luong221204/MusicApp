package com.example.dictionary.Activity.LaterAdapter;

import com.example.dictionary.Activity.RoomDataBase.Entity.Song;

public interface LaterSongInterface {
    void onInit(String source,String name);
    void onInitOff(String source ,String name);
    void onInitOffButDownloaded(
            Song song
    );

}