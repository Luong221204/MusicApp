package com.example.dictionary.Activity.Interface.Adapter;

import com.example.dictionary.Activity.Model.Album;

import java.util.ArrayList;

public interface LargeHolder {
    void onInit(String image,String name,String for_u,int action,String entitle);
    void onRecycleView(ArrayList<Album> albums);

}
