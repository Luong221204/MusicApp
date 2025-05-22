package com.example.dictionary.Activity.SongFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface SongFragmentInterface {
    void onRecycleView(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void showBottomSheet(BottomFragment bottomFragment);
    void onRecycleViewHistory(DatabaseAdapter databaseAdapter, LinearLayoutManager layoutManager);
}
