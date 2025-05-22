package com.example.dictionary.Activity.LibActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.Activity.SortFragment.Filter2Fragment;
import com.example.dictionary.Activity.FilterFragment.FilterBottomFragment;

public interface LibActivityInterface {
    void onQuantity(int quantity);
    void onRecycleViewDown(DatabaseAdapter databaseAdapter, LinearLayoutManager layoutManager);
    void showBottomSheet(BottomFragment bottomFragment);
    void onRecycleViewLove(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void onInit(String title);
    void showFilterSort(Filter2Fragment fragment);
    void showFilter(FilterBottomFragment fragment);
}
