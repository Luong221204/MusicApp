package com.example.dictionary.Activity.Presenter.ViewPresenter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.PlaylistFragmentInterface;
import com.example.dictionary.Activity.Model.Album;

import java.util.ArrayList;

public class PlaylistPresenter {
    PlaylistFragmentInterface playlistFragmentInterface;

    public PlaylistPresenter(PlaylistFragmentInterface playlistFragmentInterface) {
        this.playlistFragmentInterface = playlistFragmentInterface;
    }
    public void onInit(ArrayList<Album> albums){
        playlistFragmentInterface.onInit(albums);
    }
}
