package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import com.example.dictionary.Activity.Adapter.FavouriteAdapter.FavouriteInterface;

import java.util.Set;

public class FavouriteAdapter {
    FavouriteInterface favouriteInterface;
    public FavouriteAdapter(FavouriteInterface favouriteInterface){
        this.favouriteInterface=favouriteInterface;
    }
    public boolean init(String routine,Set<String> strings){
        if(strings.isEmpty()) return false;
        return strings.contains(routine);
    }


    public void setFavouriteTypes(boolean isSelected,Set<String> strings,String type){
        if(isSelected) strings.add(type);
        else{
            strings.remove(type);
        }
    }
}
