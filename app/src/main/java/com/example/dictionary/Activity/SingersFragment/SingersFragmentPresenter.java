package com.example.dictionary.Activity.SingersFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;

import java.util.ArrayList;

public class SingersFragmentPresenter {
    SingersFragmentInterface singersFragmentInterface;

    public SingersFragmentPresenter(SingersFragmentInterface singersFragmentInterface) {
        this.singersFragmentInterface = singersFragmentInterface;
    }
    private void onSearch(Context context){
        ArrayList<Behalf> artists=new ArrayList<>();
        for(int i = 0; i< MyApplication.behalves.size(); i++){
            if(MyApplication.behalves.get(i) instanceof Artist) {
                artists.add( MyApplication.behalves.get(i));
            }
        }
        for(int i = 0; i< artists.size(); i++){
            artists.get(i).setType(1);
        }
        ContentAdapter contentAdapter=new ContentAdapter(context,artists,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        singersFragmentInterface.onInit(contentAdapter,layoutManager);
    }
    public void onInit(Context context, Bundle bundle){
        if(bundle != null &&  bundle.getString(MyApplication.ACTION,MyApplication.BUNDLE).equals(MyApplication.OFFLINE)){
            ArrayList<Artist> artists= (ArrayList<Artist>) MyDatabase.getInstance(context).userDAO().getArtistsOnUserId(MyApplication.user.getUserId());
            ArrayList<Behalf> behalves = new ArrayList<>(artists);
            ContentAdapter contentAdapter=new ContentAdapter(context,behalves,null);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            singersFragmentInterface.onInit(contentAdapter,layoutManager);
            return;
        }
        onSearch(context);
    }
}
