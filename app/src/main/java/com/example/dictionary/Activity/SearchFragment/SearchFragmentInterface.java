package com.example.dictionary.Activity.SearchFragment;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Adapter.FormAdapter.ResultSearchAdapter;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public interface SearchFragmentInterface {
    public void stateSearchView();
    public void backToActivity();
    public void showOnResultRecycleView(ResultSearchAdapter resultSearchAdapter, LinearLayoutManager layoutManager, ArrayList<Search> list, int action);
    public void hideKeyBoard(View view);
    public void hideSearch();
    public void showSearch();
    void onNetworkDisconnect();
    public void showTabLayout(TabLayout.Tab tab, String title);
    void onInit( SearchViewPagerAdapter searchViewPagerAdapter);
    LifecycleOwner getLifecycleOwner();
    FragmentActivity getFragmentActivity();
    View getSearchView();

}
