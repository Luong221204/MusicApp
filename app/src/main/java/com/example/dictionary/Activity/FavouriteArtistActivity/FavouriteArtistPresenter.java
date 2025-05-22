package com.example.dictionary.Activity.FavouriteArtistActivity;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Behalf;

import java.util.ArrayList;

public class FavouriteArtistPresenter {
      FavouriteArtistActivityInterface favouriteArtistActivityInterface;

    public FavouriteArtistPresenter(FavouriteArtistActivityInterface favouriteArtistActivityInterface) {
        this.favouriteArtistActivityInterface = favouriteArtistActivityInterface;
    }
    public void onInit(Context context){
        ArrayList<Behalf> artists=new ArrayList<>();
        artists.addAll(MyApplication.FavouriteArtists);
        for(int i = 0; i< artists.size(); i++){
            artists.get(i).setType(1);
        }
        ContentAdapter contentAdapter=new ContentAdapter(context, artists,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        favouriteArtistActivityInterface.onInit(contentAdapter,layoutManager);
    }
}
