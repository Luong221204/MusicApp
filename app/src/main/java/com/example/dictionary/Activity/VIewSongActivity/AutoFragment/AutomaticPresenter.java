package com.example.dictionary.Activity.VIewSongActivity.AutoFragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Application.MyLiveData;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import java.util.ArrayList;

public class AutomaticPresenter {
    int[] location = new int[2];
    int[] location2 = new int[2];
    ArrayList<Type> types;
    ArrayList<Song> songs;
    private final AutoFragmentInterface autoFragmentInterface;
    public AutomaticPresenter(AutoFragmentInterface autoFragmentInterface){
        this.autoFragmentInterface=autoFragmentInterface;
    }
    public void onInit(){
        if(MyApplication.song.getAlbum_id() ==-1){
            autoFragmentInterface.onAutoPlayOff(MyApplication.autoOff);
        }else{
            autoFragmentInterface.onAutoPlay(MyApplication.auto);

        }
    }
    public void onScroll(NestedScrollView nestedScrollView, TextView td){
        nestedScrollView.getLocationOnScreen(location);
        td.getLocationOnScreen(location2);
        if(location2[1]<location[1]){
            autoFragmentInterface.onInit("Tự động phát");
        }else{
            autoFragmentInterface.onInit("Danh sách phát");
        }

    }
    public void onPlaylistOff(LifecycleOwner lifecycleOwner){
        MyLiveData.getInstance().getListSongs().observe(lifecycleOwner, new Observer<ArrayList<Song>>() {
            @Override
            public void onChanged(ArrayList<Song> songs) {
                autoFragmentInterface.onPlaylist(songs);
            }
        });
    }
    public void onAutoList(){
        autoFragmentInterface.onAutoPlay(MyApplication.auto);
    }
    public void onPlaylist(LifecycleOwner lifecycleOwner){
        if(MyApplication.song.getAlbum_id()==-1){
            MyLiveData.getInstance().getListSongsOff().observe(lifecycleOwner, new Observer<ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>>() {
                @Override
                public void onChanged(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs) {
                    autoFragmentInterface.onPlaylistOff(songs);
                }
            });
        }else{
            MyLiveData.getInstance().getListSongs().observe(lifecycleOwner, new Observer<ArrayList<Song>>() {
                @Override
                public void onChanged(ArrayList<Song> songs) {
                    autoFragmentInterface.onPlaylist(songs);
                }
            });
        }

    }
    public void onAutoListOff(){
        autoFragmentInterface.onAutoPlayOff(MyApplication.autoOff);
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        autoFragmentInterface.showBottomSheet(bottomFragment);
    }

}
