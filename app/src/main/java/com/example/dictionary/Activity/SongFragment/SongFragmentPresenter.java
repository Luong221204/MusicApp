package com.example.dictionary.Activity.SongFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import org.json.JSONException;

import java.util.ArrayList;

public class SongFragmentPresenter {
    SongFragmentInterface songFragmentInterface;
    public SongFragmentPresenter(SongFragmentInterface searchFragmentInterface){
        this.songFragmentInterface=searchFragmentInterface;
    }
    public void onInit( Context context, ItemClickListener itemClickListener) throws JSONException {
        RecycleAdapter recycleAdapter=new RecycleAdapter(getSongs(),context,0,null,itemClickListener);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        songFragmentInterface.onRecycleView(recycleAdapter,layoutManager);
    }
    private ArrayList<Song> getSongs() throws JSONException {
        ArrayList<Song> songs=new ArrayList<>();
        for(int i=0;i<MyApplication.behalves.size();i++){
            if(MyApplication.behalves.get(i) instanceof Song) songs.add((Song) MyApplication.behalves.get(i));
        }

        return songs;
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        songFragmentInterface.showBottomSheet(bottomFragment);
    }
    public void onHistory(Context context,ItemClickListener itemClickListener){
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context.getApplicationContext()).userDAO().getAllSongs();
        DatabaseAdapter databaseAdapter =new DatabaseAdapter(itemClickListener,context,songs,0,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        songFragmentInterface.onRecycleViewHistory(databaseAdapter,layoutManager);
    }
    private void onSearch(Context context,ItemClickListener itemClickListener){
        ArrayList<Song> songs=new ArrayList<>();
        for(int i=0;i<MyApplication.behalves.size();i++){
            if(MyApplication.behalves.get(i) instanceof Song) songs.add((Song) MyApplication.behalves.get(i));
        }
        RecycleAdapter recycleAdapter=new RecycleAdapter(songs,context,0,null,itemClickListener);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        songFragmentInterface.onRecycleView(recycleAdapter,layoutManager);
    }
    public void onInit(Bundle bundle,Context context,ItemClickListener itemClickListener){
        if(bundle != null && bundle.getString(MyApplication.ACTION,MyApplication.BUNDLE).equals(MyApplication.OFFLINE)){
            ArrayList<Song> songs= (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getRecentlySongs(MyApplication.user.getUserId());
            RecycleAdapter recycleAdapter=new RecycleAdapter(songs,context,0,null,itemClickListener);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            songFragmentInterface.onRecycleView(recycleAdapter,layoutManager);
            return;
        }
        onSearch(context,itemClickListener);
    }
}
