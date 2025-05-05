package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.InformFragmentInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformFragmentPresenter {
    InformFragmentInterface informFragmentInterface;
    ArrayList<Artist> artists;
    ArrayList<Song> songs;
    ArrayList<Type> types;
    String album;
    StringBuffer singers_name=new StringBuffer();
    StringBuffer type=new StringBuffer();
    public InformFragmentPresenter(InformFragmentInterface informFragmentInterface){
        this.informFragmentInterface=informFragmentInterface;
    }
    public void showInfo(){
        informFragmentInterface.showInformImage(MyApplication.song.getImage());
        informFragmentInterface.showInformYear(String.valueOf(MyApplication.song.getDay_launched()));
        informFragmentInterface.showInformSong(MyApplication.song.getName());
        if(MyApplication.song.getArtistId()==-1){
            informFragmentInterface.showInformSingers(MyApplication.song.getArtist_name());
            informFragmentInterface.showInformType(MyApplication.song.getType_name());

            informFragmentInterface.showOnRecycleOffline(MyApplication.autoOff);

        }else{
            ApiService.apiService.getArtistNameForIndicatedSong(MyApplication.song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                    artists=response.body();
                    singers_name=new StringBuffer();
                    assert artists != null;
                    for(Artist artist:artists){
                        singers_name.append(artist.getName()).append(" , ");
                    }
                    singers_name.delete(singers_name.lastIndexOf(","),singers_name.lastIndexOf(" "));
                    informFragmentInterface.showInformSingers(String.valueOf(singers_name));

                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Artist>> call, @NonNull Throwable t) {

                }
            });
            ApiService.apiService.getTypeOnSongId(MyApplication.song.getId()).enqueue(new Callback<ArrayList<Type>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Type>> call, @NonNull Response<ArrayList<Type>> response) {
                    types=response.body();
                    if(types != null && !types.isEmpty()){
                        type=new StringBuffer();
                        for(Type type1:types){
                            type.append(type1.getTitle()).append(" , ");
                        }
                        type.delete(type.lastIndexOf(","),type.lastIndexOf(" "));
                        informFragmentInterface.showInformType(String.valueOf(type));
                    }


                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Type>> call, @NonNull Throwable t) {

                }
            });
            ApiService.apiService.getAlbumOnSongId(MyApplication.song.getId()).enqueue(new Callback<ArrayList<Album>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Album>> call, @NonNull Response<ArrayList<Album>> response) {
                    ArrayList<Album> albums=response.body();
                    assert albums != null;
                    if(albums.isEmpty()) album = null;
                    else album=albums.get(0).getName();
                    informFragmentInterface.showInformAlbum(album);

                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Album>> call, @NonNull Throwable t) {

                }
            });

        }

    }
    public void showOnRecycleView(){
        informFragmentInterface.showOnRecycleView(MyApplication.auto);
    }
    public void onReceive(Intent intent) throws JSONException {
        String data=intent.getStringExtra(MyApplication.SONG);
        informFragmentInterface.showOnRecycleView(getSongs(data));

    }
    private ArrayList<Song> getSongs(String data) throws JSONException {
        ArrayList<Song> list = new ArrayList<>();
        Gson gson = new Gson();

        list = gson.fromJson(data, new TypeToken<ArrayList<Song>>() {}.getType());

        return list;
    }
    public void onRecycleOffline(){
        informFragmentInterface.showOnRecycleOffline(MyApplication.autoOff);
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        informFragmentInterface.showBottomSheet(bottomFragment);
    }
}
