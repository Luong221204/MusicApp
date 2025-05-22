package com.example.dictionary.Activity.PlaylistFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaylistPresenter {
    PlaylistFragmentInterface playlistFragmentInterface;

    public PlaylistPresenter(PlaylistFragmentInterface playlistFragmentInterface) {
        this.playlistFragmentInterface = playlistFragmentInterface;
    }
    public void onInit(Context context, Bundle bundle){
        if(bundle != null  &&  bundle.getString(MyApplication.ACTION,MyApplication.BUNDLE).equals(MyApplication.OFFLINE)){
            ArrayList<Album> albums= (ArrayList<Album>) MyDatabase.getInstance(context).userDAO().getAlbumsOnUserId(MyApplication.user.getUserId());
            AlbumAdapter albumAdapter=new AlbumAdapter(context,albums,0);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
            playlistFragmentInterface.onInit(albumAdapter,gridLayoutManager);
            return;
        }
        onSearch(context);
    }
    private void onSearch(Context context){
        AlbumAdapter albumAdapter=new AlbumAdapter(context, MyApplication.albums,0);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        playlistFragmentInterface.onInit(albumAdapter,gridLayoutManager);
    }
    public ArrayList<Album> getAlbums(Intent intent){
        ArrayList<Album> albums=new ArrayList<>();
        String data=intent.getStringExtra(MyApplication.DATA);
        try {
            JSONArray jsonArray=new JSONArray(data);
            JSONObject jsonObject;
            Gson gson=new Gson();
            Album album;
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                album=gson.fromJson(jsonObject.toString(),Album.class);
                albums.add(album);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }
}
