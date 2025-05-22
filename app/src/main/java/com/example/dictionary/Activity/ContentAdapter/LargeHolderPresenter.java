package com.example.dictionary.Activity.ContentAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.ArtistActivity.ArtistActivity;

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
    public void onInit(Behalf behalf,Context context){
        if(behalf instanceof Artist){
            ApiService.apiService.getAlbumOnArtistId(((Artist) behalf).getId()).enqueue(new Callback<ArrayList<Album>>() {
                @Override
                public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                    if(behalf.getType() == 2 && !Objects.requireNonNull(response.body()).isEmpty()){
                        largeHolder.onInit(((Artist) behalf).getImage(),
                                ((Artist) behalf).getName(),"Dành cho fan của "+((Artist) behalf).getName(), View.VISIBLE,"Nghệ sĩ * "+behalf.getFollowed()+" Người quan tâm");
                        AlbumAdapter albumAdapter=new AlbumAdapter(context, response.body(),0);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false);
                        largeHolder.onRecycleView(albumAdapter,layoutManager);
                    }else{
                        largeHolder.onInit(((Artist) behalf).getImage(),
                                ((Artist) behalf).getName(),"Dành cho fan của "+((Artist) behalf).getName(), View.GONE,"Nghệ sĩ * "+behalf.getFollowed()+" Người quan tâm");
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                    largeHolder.onInitOff(((Artist) behalf).getImage(), ((Artist) behalf).getName(),"",View.GONE,"");
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
