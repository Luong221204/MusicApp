package com.example.dictionary.Activity.AddToPlistFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

import java.util.ArrayList;

public class AddToPlayListFragmentPresenter implements ItemClickListener {
    AddToPlayListFragmentInterface addToPlayListFragmentInterface;
    BottomsAdapter playlistAdapter;
    LinearLayoutManager linearLayoutManager;
    public AddToPlayListFragmentPresenter(AddToPlayListFragmentInterface addToPlayListFragmentInterface) {
        this.addToPlayListFragmentInterface = addToPlayListFragmentInterface;
    }
    public void onInit(String tag, Context context, Bundle bundle){
        if(!tag.equals(MyApplication.SONG)) {
            addToPlayListFragmentInterface.onInit(View.GONE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                Song song=bundle.getSerializable(MyApplication.SONG,Song.class);
                playlistAdapter=new BottomsAdapter(context, R.layout.add_playlist,1,song);
                linearLayoutManager=new LinearLayoutManager(context);
                addToPlayListFragmentInterface.onRecycle(playlistAdapter,linearLayoutManager);
            }

        }

    }
    public void onSearch(int actionId,String text){
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            ArrayList<Playlist> search=new ArrayList<>();
            for(Playlist playlist:MyApplication.playlists){
                if(playlist.getName().contains(text)){
                    search.add(playlist);
                }
            }
            if(playlistAdapter != null) playlistAdapter.setData(search);

            addToPlayListFragmentInterface.hideKeyBoard();
        }
    }
    public void onTrack(String text){
        ArrayList<Playlist> search=new ArrayList<>();
        for(Playlist playlist:MyApplication.playlists){
            if(playlist.getName().contains(text)){
                search.add(playlist);
            }
        }
        if(playlistAdapter != null) playlistAdapter.setData(search);
    }

    @Override
    public void onClickListener(Song song) {

    }
}
