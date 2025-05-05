package com.example.dictionary.Activity.Presenter.ViewPresenter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.LibFragmentInterface;

public class LibraryPresenter {
    LibFragmentInterface libFragmentInterface;
    public LibraryPresenter(LibFragmentInterface libFragmentInterface){
        this.libFragmentInterface=libFragmentInterface;
    }
    public void onFavouritePart(){
        libFragmentInterface.onFavouritePart(MyApplication.libs);
    }
    public void onHistory(){
        libFragmentInterface.onHistory(MyApplication.history);
    }
    public void onPlaylist(){
        libFragmentInterface.onPlaylist(MyApplication.playlists);
    }

}
