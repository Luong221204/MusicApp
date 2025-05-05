package com.example.dictionary.Activity.Interface.View;

import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public interface SearchFragmentInterface {
    public void stateSearchView();
    public void backToActivity();
    public void showOnResultRecycleView(ArrayList<String> list);
    public void hideKeyBoard(View view);
    public void hideSearch();
    public void showSearch();
    public void showTabLayout(TabLayout.Tab tab, int position);

}
