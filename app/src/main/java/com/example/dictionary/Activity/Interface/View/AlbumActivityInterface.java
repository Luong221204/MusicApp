package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface AlbumActivityInterface {
    public void setAlbumTitle(String artist_name,int year_launched,String image);
    public void setToolbarTitle(String name);
    public void setRecycleView(ArrayList<Song>list);
    public void showBottomSheet(BottomFragment bottomFragment);
}
