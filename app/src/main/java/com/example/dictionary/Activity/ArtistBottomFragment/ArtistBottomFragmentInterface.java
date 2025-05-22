package com.example.dictionary.Activity.ArtistBottomFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ArtistBottomAdapter.ArtistBottomAdapter;
import com.example.dictionary.Activity.Model.Artist;

import java.util.ArrayList;

public interface ArtistBottomFragmentInterface {
    void onInit(ArtistBottomAdapter optionAdapter, LinearLayoutManager layoutManager);
}
