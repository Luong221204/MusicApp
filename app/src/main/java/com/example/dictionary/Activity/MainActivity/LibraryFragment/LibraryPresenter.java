package com.example.dictionary.Activity.MainActivity.LibraryFragment;

import android.app.Dialog;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;
import com.example.dictionary.Activity.LibFavouriteAdapter.LibFavouriteAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.List;

public class LibraryPresenter {
    LibFragmentInterface libFragmentInterface;
    private boolean isStopped=false;
    public LibraryPresenter(LibFragmentInterface libFragmentInterface){
        this.libFragmentInterface=libFragmentInterface;
    }
    public void onFavouritePart(Context context){
        List<Song> songs= MyDatabase.getInstance(context).userDAO().getSongsOnUserId(MyApplication.user.getUserId());
        MyApplication.SongDownloaded.setCount(songs.size());
        LibFavouriteAdapter libFavouriteAdapter=new LibFavouriteAdapter((MainActivity) context,MyApplication.libs);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        libFragmentInterface.onFavouritePart(libFavouriteAdapter,layoutManager);
    }
    public void onHistory(Context context){
        ArrayList<com.example.dictionary.Activity.Model.Song> songs= (ArrayList<com.example.dictionary.Activity.Model.Song>) MyDatabase.getInstance(context).userDAO().getRecentlySongs(MyApplication.user.getUserId());
        LaterAdapter laterAdapter=new LaterAdapter(context,MyApplication.history,songs);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        libFragmentInterface.onHistory(laterAdapter,layoutManager1);
    }
    public void onPlaylist(Context context){
        BottomsAdapter bottomsAdapter=new BottomsAdapter(context, R.layout.add_playlist,0,null);
        LinearLayoutManager playlistAdapter=new LinearLayoutManager(context);
        libFragmentInterface.onPlaylist(bottomsAdapter,playlistAdapter);
    }
    public void onStop(){
        isStopped=true;
    }
    public void reset(Context context){
        Dialog dialog= LoginPresenter.dialog(context);
        if(isStopped) {
            dialog.show();
            onFavouritePart( context);
            onHistory(context);
            onPlaylist(context);
            dialog.dismiss();

        }
    }
}
