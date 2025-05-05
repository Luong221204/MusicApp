package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.SearchFragmentInterface;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragmentPresenter {
    SearchFragmentInterface ui;
    ArrayList<com.example.dictionary.Activity.Model.Song> songs;
    SearchFragment searchFragment;
    public SearchFragmentPresenter(SearchFragmentInterface ui){
        this.ui=ui;
        this.searchFragment= (SearchFragment) ui;
    }
    public void stateSearchView(){
        this.ui.stateSearchView();
    }
    public void backToActivity(){
        this.ui.backToActivity();
    }
    public void showOnResultRecycleView(CharSequence s){
        ArrayList<String> list=new ArrayList<>();
        String request= s.toString();
        String[] split_request=request.split("\\s+");
        for (String string : split_request) {
            for (String fake : MyApplication.fakeData) {
                if (fake.toLowerCase().contains(string)) {
                    list.add(fake);
                }
            }
        }
        this.ui.showOnResultRecycleView(list);
    }
    public void restoreSearchState(Bundle bundle){
        if(bundle != null){
            int action=bundle.getInt(MyApplication.ACTION);
            if(action==1){
                Log.d("DUCLUONG",action+" ");
                this.searchFragment.recyclerView.setVisibility(View.GONE);
                this.searchFragment.relativeLayout.setVisibility(View.VISIBLE);
            }
        }
    }
    public void showOrHideSearch(View view, int action, Context context) {
        if (action == 0) {this.ui.hideSearch();this.searchFragment.action=0;}
        else {
            //this.searchFragment.action=1;
            EditText editText= (EditText) view;
            ApiService.apiService.getSearchSongs(editText.getText().toString()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                    songs=response.body();
                    Gson gson=new Gson();
                    JsonArray json=gson.toJsonTree(songs).getAsJsonArray();
                    String data=json.toString();
                    Intent intent=new Intent(MyApplication.SEARCH);
                    intent.putExtra(MyApplication.SONG,data);
                    context.sendBroadcast(intent);                }

                @Override
                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                }
            });
            this.ui.showSearch();
            this.ui.hideKeyBoard(view);
        }
    }
    public void showTabLayout(TabLayout.Tab tab, int position){
        this.ui.showTabLayout(tab,position);
    }
    public void hideKeyBoard(View view){
        this.ui.hideKeyBoard(view);
    }
}
