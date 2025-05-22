package com.example.dictionary.Activity.BottomFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.OptionAdapter.OptionAdapter;

import java.util.ArrayList;

public interface BottomFragmentInterface {
    void onInit(String song_name,String singers,String image);
    void onRecycleView(OptionAdapter optionAdapter, LinearLayoutManager linearLayoutManager);
    void onInitOff(String song_name,String singers,String image);

}
