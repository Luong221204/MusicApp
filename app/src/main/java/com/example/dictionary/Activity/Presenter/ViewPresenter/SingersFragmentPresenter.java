package com.example.dictionary.Activity.Presenter.ViewPresenter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.SingersFragmentInterface;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;

import java.util.ArrayList;

public class SingersFragmentPresenter {
    SingersFragmentInterface singersFragmentInterface;

    public SingersFragmentPresenter(SingersFragmentInterface singersFragmentInterface) {
        this.singersFragmentInterface = singersFragmentInterface;
    }
    public void onInit(){
        ArrayList<Behalf> artists=new ArrayList<>();
        for(int i = 0; i< MyApplication.behalves.size(); i++){
            if(MyApplication.behalves.get(i) instanceof Artist) {
                artists.add( MyApplication.behalves.get(i));
            }
        }
        for(int i = 0; i< artists.size(); i++){
           artists.get(i).setType(1);
        }
        singersFragmentInterface.onInit(artists);
    }
}
