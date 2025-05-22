package com.example.dictionary.Activity.AlbumActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface AlbumActivityInterface {
    public void setAlbumTitle(String artist_name,int year_launched,String image);
    void setLoved(int icon);
    public void setToolbarTitle(String name);
    public void setRecycleView(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager);
    public void showBottomSheet(BottomFragment bottomFragment);
}
