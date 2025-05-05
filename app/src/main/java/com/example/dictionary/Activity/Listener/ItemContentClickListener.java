package com.example.dictionary.Activity.Listener;

import android.view.View;

import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;

public class ItemContentClickListener implements View.OnClickListener{
    ItemClickListener itemClickListener;
    Song song;
    public ItemContentClickListener(ItemClickListener itemClickListener,Song song){
        this.itemClickListener=itemClickListener;
        this.song=song;
    }
    @Override
    public void onClick(View v) {
        this.itemClickListener.onClickListener(this.song);
    }
}
