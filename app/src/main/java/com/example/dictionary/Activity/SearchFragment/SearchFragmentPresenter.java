package com.example.dictionary.Activity.SearchFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Adapter.FormAdapter.ResultSearchAdapter;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragmentPresenter {
    SearchFragmentInterface ui;
    ArrayList<com.example.dictionary.Activity.Model.Song> songs;
    SearchFragment searchFragment;
    ResultSearchAdapter resultSearchAdapter;
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
    private void showOnResultRecycleView(ArrayList<Search> s, Context c,int position) {
        ResultSearchAdapter resultSearchAdapter1 = new ResultSearchAdapter(s, c,position,ui);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        this.ui.showOnResultRecycleView(resultSearchAdapter1, layoutManager, s, View.VISIBLE);
    }
    public Disposable getObservable(EditText editText){
        Disposable  disposable = RxTextView.textChanges(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text->text.length()>0)
                .flatMap(flap-> ApiService.apiService.getSearch(flap.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                            ArrayList<Search> searches= new ArrayList<>();
                            searches.addAll(query.getArtists());
                            searches.addAll(query.getSongs());
                            showOnResultRecycleView(searches,editText.getContext(),query.getArtists().size());
                        },
                        throwable -> {
                        }
                );;
        return disposable;
    }
    public void restoreSearchState(Bundle bundle){
        if(bundle != null){
            int action=bundle.getInt(MyApplication.ACTION);
            if(action==1){
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
        String title="Nổi bật";
        switch(position){
            case 0: title="Nổi bật";break;
            case 1: title="PlayList";break;
            case 2: title="Nghệ sĩ";break;
            case 3: title="Bài hát";break;
            default:title="Nổi bật";
        }
        this.ui.showTabLayout(tab,title);
    }
    public void hideKeyBoard(View view){
        this.ui.hideKeyBoard(view);
    }
    public void onInit(Context context){
        ApiService.apiService.ping().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                SearchViewPagerAdapter searchViewPagerAdapter=new SearchViewPagerAdapter(ui.getFragmentActivity(),1);
                ui.onInit(searchViewPagerAdapter);
                ui.getFragmentActivity().getOnBackPressedDispatcher().addCallback(
                        ui.getLifecycleOwner(),
                        new OnBackPressedCallback(true) {
                            @Override
                            public void handleOnBackPressed() {
                                backToActivity();
                            }
                        }
                );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ui.onNetworkDisconnect();
            }
        });


    }
}
