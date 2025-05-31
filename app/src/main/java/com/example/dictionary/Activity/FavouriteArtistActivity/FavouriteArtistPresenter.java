package com.example.dictionary.Activity.FavouriteArtistActivity;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteArtistPresenter {
      FavouriteArtistActivityInterface favouriteArtistActivityInterface;

    public FavouriteArtistPresenter(FavouriteArtistActivityInterface favouriteArtistActivityInterface) {
        this.favouriteArtistActivityInterface = favouriteArtistActivityInterface;
    }
    public void onInit(Context context){
        ArrayList<Behalf> artists=new ArrayList<>();
        artists.addAll(MyApplication.FavouriteArtists);
        for(int i = 0; i< artists.size(); i++){
            artists.get(i).setType(1);
        }
        ContentAdapter contentAdapter=new ContentAdapter(context, artists,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        favouriteArtistActivityInterface.onInit(contentAdapter,layoutManager);
        ApiService.apiService.getSuggestArtists().enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ArrayList<Artist> lovedArtists = new ArrayList<>(response.body());
                    lovedArtists.removeAll(MyApplication.FavouriteArtists);
                    ArrayList<Behalf> behalves=new ArrayList<>(lovedArtists);
                    for(Behalf behalf:behalves){
                        behalf.setType(1);
                    }
                    ContentAdapter contentAdapter1=new ContentAdapter(context, behalves,null);
                    LinearLayoutManager layoutManager1=new LinearLayoutManager(context);
                    favouriteArtistActivityInterface.onSuggest(contentAdapter1,layoutManager1);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {
                favouriteArtistActivityInterface.hideSuggest();
            }
        });
    }
}
