package com.example.dictionary.Activity.SplashActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.DataManager;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.LoginActivity.LoginActivity;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter {
    SplashInterface splashInterface;
    public SplashPresenter(SplashInterface s){
        splashInterface=s;
    }
    Dialog dialog;
    public void loginOrNot(Context c){
        dialog= LoginPresenter.dialog(c);
        dialog.show();
        if(DataManager.getInstance().getLoginState()){
            Intent intent=new Intent(c, MainActivity.class);
            MyApplication.user=DataManager.instance.getUser();
            ApiService.apiService.getPlaylists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Playlist>>() {
                @Override
                public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                    MyApplication.playlists.add(new Playlist(R.drawable.plus,"Tạo Playlist"));
                    if(response.body() != null){
                        MyApplication.playlists.addAll(response.body());

                    }
                    ApiService.apiService.getFavouriteSongs(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Song>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                            MyApplication.FavouriteSongs.clear();
                            if(response.body() != null){
                                MyApplication.FavouriteSongs=response.body();

                            }
                            MyApplication.Love.setCount(MyApplication.FavouriteSongs.size());
                            ApiService.apiService.getFavouriteArtists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Artist>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                    MyApplication.FavouriteArtists.clear();
                                    if(response.body() != null){
                                        MyApplication.FavouriteArtists=response.body();
                                        MyApplication.Artists.setCount(MyApplication.FavouriteArtists.size());
                                    }
                                    ApiService.apiService.getFavouriteAlbums(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Album>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                                            MyApplication.FavouriteAlbums.clear();
                                            if(response.body() != null){
                                                MyApplication.FavouriteAlbums=response.body();

                                            }
                                            ApiService.apiService.getDownloaded(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Song>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                                                    if(response.body() != null){
                                                        MyApplication.d=new ArrayList<>();
                                                        MyApplication.d=response.body();

                                                        MyApplication.SongDownloaded.setCount(MyApplication.d.size());
                                                    }


                                                    c.startActivity(intent);
                                                    dialog.dismiss();
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                                                }
                                            });

                                        }

                                        @Override
                                        public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                                }
                            });

                        }
                        @Override
                        public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                        }
                    });

                }
                @Override
                public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                    MyApplication.FavouriteSongs.clear();
                    ArrayList<Song> songs= (ArrayList<Song>) MyDatabase.getInstance(c).userDAO().getFavouriteSongs(MyApplication.user.getUserId(),true);
                    if(songs != null) MyApplication.FavouriteSongs=songs;
                    c.startActivity(intent);
                    dialog.dismiss();
                }
            });
        }else{
            dialog.dismiss();
            Intent intent=new Intent(c, LoginActivity.class);
            c.startActivity(intent);
            splashInterface.onFinish();
        }
    }
}
