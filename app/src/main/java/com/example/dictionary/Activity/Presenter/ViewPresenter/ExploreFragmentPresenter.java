package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.os.Bundle;

import com.example.dictionary.Activity.Application.DataManager;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ExploreFragmentInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragmentPresenter {
    ExploreFragmentInterface exploreFragmentInterface;
    public ExploreFragmentPresenter(ExploreFragmentInterface exploreFragmentInterface){
        this.exploreFragmentInterface=exploreFragmentInterface;
    }
    public void showOnHintRecycle(){
        ArrayList<String> stringSet= new ArrayList<>(DataManager.getRoutines());
        int index= MyApplication.type_map.get(stringSet.get(1));
        ApiService.apiService.getSongsBasedOnType(index).enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                ArrayList<Song> songs=response.body();
                if(songs != null){
                    ArrayList<ArrayList<Song>> lists=new ArrayList<>();
                    int cnt1=0;
                    for(int i=0;i<songs.size()/3+1;i++){
                        int cnt=0;
                        ArrayList<Song> list1=new ArrayList<>();
                        while( cnt1<songs.size() && cnt<3){
                            list1.add(songs.get(cnt1));
                            cnt++;
                            cnt1++;
                        }
                        lists.add(list1);
                    }
                    exploreFragmentInterface.showOnHintRecycle(lists);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

            }
        });
    }
    public void showOnIdol(){
        ApiService.apiService.getArtistIndexed(String.valueOf(2)).enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                exploreFragmentInterface.showOnIdol(response.body().get(0).getImage(),response.body().get(0).getName());
            }

            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

            }
        });
    }
    public void showOnIdolAlbum(){
        ApiService.apiService.getAlbumOnArtistId(2).enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                exploreFragmentInterface.showOnIdolAlbumRecycle(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
            }
        });
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        exploreFragmentInterface.showBottomSheet(bottomFragment);
    }
    public void showOnThemes(){
        ApiService.apiService.getTypeHot().enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                exploreFragmentInterface.showOnThemeRecycle(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {

            }
        });
    }
    public void showOnHistory(){
        exploreFragmentInterface.showOnHistoryRecycle(MyApplication.history);
    }
    public void showOnAlbumHot(){
        ApiService.apiService.getAlbumsHot().enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                exploreFragmentInterface.showOnHotAlbum(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

            }
        });
    }
}
