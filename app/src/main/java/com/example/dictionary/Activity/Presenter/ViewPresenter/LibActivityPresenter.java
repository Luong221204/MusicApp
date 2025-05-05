package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.LibActivityInterface;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public class LibActivityPresenter {
    LibActivityInterface libActivityInterface;
    public LibActivityPresenter(LibActivityInterface libActivityInterface){
        this.libActivityInterface=libActivityInterface;
    }
    public void onInit(String title, Context context){
        if(title.equals("Đã tải")){
            ArrayList<Song> songs= (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getAllSongs();
            libActivityInterface.onQuantity(songs.size());
            libActivityInterface.onRecycleView(songs);
        }
    }
    public void showBottomSheet(com.example.dictionary.Activity.Model.Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        libActivityInterface.showBottomSheet(bottomFragment);
    }

}
