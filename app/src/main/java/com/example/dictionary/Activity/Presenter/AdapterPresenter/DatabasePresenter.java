package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Application.MyLiveData;
import com.example.dictionary.Activity.Interface.Adapter.DatabaseInterface;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;

import java.util.ArrayList;

public class DatabasePresenter {
    public DatabaseInterface databaseInterface;

    public DatabasePresenter(DatabaseInterface databaseInterface) {
        this.databaseInterface = databaseInterface;
    }
    public void onInit(int mode){
        if(mode==1){
            databaseInterface.onView(View.VISIBLE,View.VISIBLE,View.GONE);
        }else if(mode == 2){
            databaseInterface.onView(View.GONE,View.GONE,View.VISIBLE);

        }
    }
    public static com.example.dictionary.Activity.Model.Song convert(Song song1){
        return new
                com.example.dictionary.Activity.Model.Song
                (song1.getName(),song1.getUrl(),-1,song1.getSong_id(),song1.getImage(),song1.getYear_launched(),-1,song1.getSinger(),song1.getType_name(),song1.getAlbum_name(),0);
    }
    public static ArrayList<Song> matchToList(ArrayList<Song> songs1, int song_id){
        ArrayList<Song> halfOne=new ArrayList<>();
        for(Song song1:songs1){
            if(song1.getSong_id() == song_id) break;
            halfOne.add(song1);
        }
        songs1.removeAll(halfOne);
        songs1.addAll(halfOne);
        int cnt=0;
        for(Song song1:songs1){
            if(song1.getSong_id() == song_id) break;
            cnt++;
        }
        songs1.remove(cnt);
        return songs1;
    }
    public void  sendBroadcast(Context context, DatabaseAdapter databaseAdapter,Song song){
        MyLiveData.getInstance().addToSongsOffList(song);
        MyApplication.autoOff.remove(song);
        Intent intent2=new Intent(MyApplication.AGAIN3);
        context.sendBroadcast(intent2);
        databaseAdapter.updateData(MyApplication.autoOff);
    }
    public void  sendBroadcast2(Context context, ViewSongActivity viewSongActivity, Song song){
        MyApplication.stackOff.push(song);
        Intent intent=new Intent(context, MyService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,convert(song));
        intent.putExtra(MyApplication.BUNDLE,bundle);
        context.startService(intent);
        Intent intent1=new Intent(context, ViewSongActivity.class);
        Bundle bundle1=new Bundle();
        bundle1.putSerializable(MyApplication.SONG,convert(song));
        intent1.putExtra(MyApplication.BUNDLE,bundle1);
        context.startActivity(intent1);
        MyApplication.autoOff=matchToList((ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getAllSongs(),song.getSong_id());
        if(viewSongActivity != null) viewSongActivity.finish();
    }
}
