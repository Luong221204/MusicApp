package com.example.dictionary.Activity.PlayMusicFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Broadcast.MyReciever;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.MainActivity.MainActivity;

public class PlayMusicPresenter {
    public void sendBroadcastOption(MainActivity context, int action){
        Intent intent=new Intent(context, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putInt(MyApplication.ACTION,action);
        bundle.putSerializable(MyApplication.SONG,MyApplication.song);
        intent.putExtra(MyApplication.ACTION,bundle);
        context.sendBroadcast(intent);
    }

    public void sendMusicIntent(Context context, Song song){
        Intent intent=new Intent(context, MyService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        intent.putExtra(MyApplication.BUNDLE,bundle);
        context.startService(intent);
    }
}
