package com.example.dictionary.Activity.OptionAdapter;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.ArtistBottomFragment.ArtistBottomFragment;
import com.example.dictionary.Activity.AddToPlistFragment.AddToPlayListFragment;

public interface OptionInterface {
    void onInit(float alpha,boolean enable,String name,int icon);
    void onAddToPlayListFragment(AddToPlayListFragment addToPlayListFragment);
    void onArtistBottomFragment(ArtistBottomFragment artistBottomFragment, Song song);
}
