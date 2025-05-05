package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Lib;
import com.example.dictionary.Activity.Model.Playlist;

import java.util.ArrayList;

public interface LibFragmentInterface {
    void onFavouritePart(ArrayList<Lib> libs);
    void onPlaylist(ArrayList<Playlist> playlists);
    void onHistory(ArrayList<Later> laters);
}
