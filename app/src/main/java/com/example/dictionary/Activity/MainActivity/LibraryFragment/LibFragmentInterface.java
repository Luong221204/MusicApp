package com.example.dictionary.Activity.MainActivity.LibraryFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;
import com.example.dictionary.Activity.LibFavouriteAdapter.LibFavouriteAdapter;

public interface LibFragmentInterface {
    void onFavouritePart(LibFavouriteAdapter libFavouriteAdapter, LinearLayoutManager layoutManager);
    void onPlaylist(BottomsAdapter playlistAdapter, LinearLayoutManager linearLayoutManager);
    void onHistory(LaterAdapter laterAdapter, LinearLayoutManager layoutManager1);
}
