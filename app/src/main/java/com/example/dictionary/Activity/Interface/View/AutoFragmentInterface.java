package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface AutoFragmentInterface {
    void onInit(String theme);
    void onPlaylist(ArrayList<Song> songs);
    void onAutoPlay(ArrayList<Song>songs);
    void onAutoPlayOff(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs);
    void onPlaylistOff(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs);
    void showBottomSheet(BottomFragment bottomFragment);

}
