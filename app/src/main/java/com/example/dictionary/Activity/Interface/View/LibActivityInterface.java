package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface LibActivityInterface {
    void onQuantity(int quantity);
    void onRecycleView(ArrayList<Song> songs);
    void showBottomSheet(BottomFragment bottomFragment);
}
