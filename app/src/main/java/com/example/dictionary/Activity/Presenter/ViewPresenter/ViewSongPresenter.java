package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ViewSongActivityInterface;
import com.example.dictionary.Activity.Model.Song;

public class ViewSongPresenter {
    ViewSongActivityInterface viewSongActivityInterface;
    public ViewSongPresenter(ViewSongActivityInterface viewSongActivityInterface){
        this.viewSongActivityInterface=viewSongActivityInterface;
    }
    public void onInit(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            assert bundle != null;
            Song song=bundle.getSerializable(MyApplication.SONG, Song.class);
            assert song != null;
            viewSongActivityInterface.onSongName(song.getName());
        }
    }
}
