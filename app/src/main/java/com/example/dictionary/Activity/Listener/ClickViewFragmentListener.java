package com.example.dictionary.Activity.Listener;

import android.view.View;

import com.example.dictionary.Activity.SearchFragment.SearchFragment;

public class ClickViewFragmentListener implements View.OnClickListener {
    SearchFragment fragment;
    public ClickViewFragmentListener(SearchFragment fragment){
        this.fragment=fragment;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==this.fragment.delete.getId()){
            this.fragment.searchFragmentPresenter.stateSearchView();
        }else if(v==this.fragment.back){
            this.fragment.searchFragmentPresenter.backToActivity();
            this.fragment.searchFragmentPresenter.hideKeyBoard(this.fragment.editText);
        }else if(v==this.fragment.editText){
            this.fragment.searchFragmentPresenter.showOrHideSearch(v,0, fragment.getContext());
        }
    }
}
