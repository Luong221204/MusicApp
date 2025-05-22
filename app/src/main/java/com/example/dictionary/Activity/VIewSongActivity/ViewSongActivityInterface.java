package com.example.dictionary.Activity.VIewSongActivity;

import android.app.Dialog;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Adapter.NaviAdapter.SongViewAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;

public interface ViewSongActivityInterface {
    void onSongName(String name);
    void onTabLayout(boolean textState,int sourceCont,int sourceDot,String badge);
    void onFinish(Dialog dialog, RecycleAdapter resultSearchAdapter, LinearLayoutManager layoutManager);
    void onInit(SongViewAdapter songViewAdapter, String song);}
