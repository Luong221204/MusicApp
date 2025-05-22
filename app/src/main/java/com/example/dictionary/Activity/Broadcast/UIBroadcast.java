package com.example.dictionary.Activity.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dictionary.Activity.MainActivity.MainActivity;

public class UIBroadcast extends BroadcastReceiver {
    MainActivity activity;
    public UIBroadcast(MainActivity mainActivity){
        this.activity=mainActivity;

    }
    @Override
    public void onReceive(Context context, Intent intent) {
       this.activity.uiPresenter.smallSongView(intent);
    }
}
