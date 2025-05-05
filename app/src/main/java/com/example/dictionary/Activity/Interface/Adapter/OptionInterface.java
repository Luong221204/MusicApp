package com.example.dictionary.Activity.Interface.Adapter;

import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.Pager2Fragment.ArtistBottomFragment;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.AddToPlayListFragment;

public interface OptionInterface {
    void onInit(float alpha,boolean enable,String name,int icon);
    void onAddToPlayListFragment(AddToPlayListFragment addToPlayListFragment);
    void onArtistBottomFragment(ArtistBottomFragment artistBottomFragment, Song song);
}
