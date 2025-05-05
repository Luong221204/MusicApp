package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;

import java.util.ArrayList;

public interface BottomFragmentInterface {
    void onInit(String song_name,String singers,String image);
    void onRecycleView(ArrayList<Options> options, Song song);
    void onInitOff(String song_name,String singers,String image);

}
