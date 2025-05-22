package com.example.dictionary.Activity.ContentAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;

public interface LargeHolder {
    void onInit(String image,String name,String for_u,int action,String entitle);
    void onRecycleView(AlbumAdapter albumAdapter, LinearLayoutManager layoutManager);
    void onInitOff(String image,String name,String for_u,int action,String entitle);

}
