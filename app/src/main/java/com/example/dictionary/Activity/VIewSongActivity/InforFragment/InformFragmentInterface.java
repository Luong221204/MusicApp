package com.example.dictionary.Activity.VIewSongActivity.InforFragment;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import java.util.ArrayList;

public interface InformFragmentInterface {
    public void showInformSong(String song_name);
    public void showInformSingers(String singers);
    public void showInformAlbum(String album);
    public void showInformImage(String image);
    public void showInformYear(String date);
    public void showInformType(String type);
    public void showOnRecycleView(ArrayList<Song> songs);
    public void showOnRecycleOffline(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs);
    public void showBottomSheet(BottomFragment bottomFragment);
}
