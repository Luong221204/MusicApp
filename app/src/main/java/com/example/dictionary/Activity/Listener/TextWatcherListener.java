package com.example.dictionary.Activity.Listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.dictionary.Activity.View.NaviFragment.SearchFragment;

public class TextWatcherListener implements TextWatcher {
    SearchFragment searchFragment;
    public TextWatcherListener(SearchFragment searchFragment){
        this.searchFragment=searchFragment;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length()>0){
            searchFragment.searchView.setVisibility(View.GONE);
            searchFragment.delete.setVisibility(View.VISIBLE);
            searchFragment.recyclerView.setVisibility(View.VISIBLE);
            searchFragment.relativeLayout3.setVisibility(View.GONE);
            this.searchFragment.searchFragmentPresenter.showOnResultRecycleView(s);

        }else{
            searchFragment.relativeLayout3.setVisibility(View.VISIBLE);
            searchFragment.historyView.setVisibility(View.VISIBLE);
            searchFragment.searchView.setVisibility(View.VISIBLE);
            searchFragment.delete.setVisibility(View.GONE);
            searchFragment.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
