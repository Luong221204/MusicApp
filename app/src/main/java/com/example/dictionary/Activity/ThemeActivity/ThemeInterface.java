package com.example.dictionary.Activity.ThemeActivity;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import java.util.ArrayList;

public interface ThemeInterface {
    void onInit(String name, ArrayList<Song> songs);
    void showBottomSheet(BottomFragment bottomFragment);
}
