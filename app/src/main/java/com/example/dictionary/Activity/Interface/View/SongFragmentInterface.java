package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface SongFragmentInterface {
    void onRecycleView(ArrayList<Song> songs);
    void showBottomSheet(BottomFragment bottomFragment);
    void onRecycleViewHistory(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs);
}
