package com.example.dictionary.Activity.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragment;

public class ViewFragmentBroadcast extends BroadcastReceiver {
    Intent intent;
    public ViewFragmentBroadcast(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        this.intent=intent;
    }
    public void onDeploy(ViewFragment viewFragment){
        //viewFragment.viewFragmentPresenter.receiveFromBroadcast(intent);
        Log.d("DUCLUONG","koko");
    }
}
