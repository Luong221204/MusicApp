package com.example.dictionary.Activity.AddToPlistFragment;

import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;

public interface AddToPlayListFragmentInterface {
    void onSearch();
    void onInit(int action);
    void onRecycle(BottomsAdapter playlistAdapter, LinearLayoutManager linearLayoutManager);
    EditText getEditText();
    void hideKeyBoard();
}
