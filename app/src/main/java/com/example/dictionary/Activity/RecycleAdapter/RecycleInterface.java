package com.example.dictionary.Activity.RecycleAdapter;

import com.example.dictionary.Activity.RoomDataBase.Entity.Song;

public interface RecycleInterface {
    void onInitMode(int moreAction,int addAction,int listAction,int cdAction,float scale);
    void onInit(String singer_name,String song_name,String image);
    void loadImage(String image);
    void onInitOff(String singer_name,String song_name,String image);
    void onInitOffButDownloaded(Song song);

}
