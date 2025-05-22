package com.example.dictionary.Activity.PlaylistFragment;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;

public interface PlaylistFragmentInterface {
    void onInit(AlbumAdapter albumAdapter, GridLayoutManager gridLayoutManager);
}
