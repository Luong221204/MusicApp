package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.room.Index;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.Adapter.LargeHolder;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.View.Activity.ArtistActivity;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LargeHolderPresenter {
    LargeHolder largeHolder;

    public LargeHolderPresenter(LargeHolder largeHolder) {
        this.largeHolder = largeHolder;
    }
    public void onInit(Behalf behalf){
        if(behalf instanceof Artist){
            ApiService.apiService.getAlbumOnArtistId(((Artist) behalf).getId()).enqueue(new Callback<ArrayList<Album>>() {
                @Override
                public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                    if(behalf.getType() == 2 && !Objects.requireNonNull(response.body()).isEmpty()){
                        largeHolder.onInit(((Artist) behalf).getImage(),
                                ((Artist) behalf).getName(),"Dành cho fan của "+((Artist) behalf).getName(), View.VISIBLE,"Nghệ sĩ * "+behalf.getFollowed()+" Người quan tâm");
                        largeHolder.onRecycleView(response.body());
                    }else{
                        largeHolder.onInit(((Artist) behalf).getImage(),
                                ((Artist) behalf).getName(),"Dành cho fan của "+((Artist) behalf).getName(), View.GONE,"Nghệ sĩ * "+behalf.getFollowed()+" Người quan tâm");
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                }
            });
        }
    }
    public void onTouch(Behalf artist, Context context){
        if(artist instanceof Artist){
            Intent intent=new Intent(context, ArtistActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable(MyApplication.ARTIST,artist);
            intent.putExtra(MyApplication.DATA,bundle);
            context.startActivity(intent);
        }
    }
}
