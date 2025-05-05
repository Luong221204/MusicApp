package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ThemeInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemePresenter {
    ThemeInterface themeInterface;

    public ThemePresenter(ThemeInterface themeInterface) {
        this.themeInterface = themeInterface;
    }
    public void onInit(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            assert bundle != null;
            Type type=bundle.getSerializable(MyApplication.ACTION, Type.class);
            ApiService.apiService.getSongsBasedOnType(type.getId()).enqueue(new Callback<ArrayList<Song>>() {
                @Override
                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                    themeInterface.onInit(type.getTitle().toUpperCase(),response.body());
                }
                @Override
                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {
                }
            });
        }
    }
    public void showBottomSheet(com.example.dictionary.Activity.Model.Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        themeInterface.showBottomSheet(bottomFragment);
    }
}
