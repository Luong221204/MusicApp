package com.example.dictionary.Activity.ApiService;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson=new GsonBuilder().setDateFormat("dd/mm/yyyy").create();
    ApiService apiService=new Retrofit.Builder().baseUrl("http://192.168.1.14:2000").addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @GET("/songs")
    Call<ArrayList<Song>> getSongs();
    @GET("/song/{hint}/songs")
    Call<ArrayList<com.example.dictionary.Activity.Model.Song>> getSearchSongs(@Path("hint") String hint);
    @GET("/{artist_id}/albums")
    Call<ArrayList<Album>> getAlbumOnArtistId(@Path("artist_id") int artist_id);
    @GET("/{song_id}/name")
    Call<ArrayList<Artist>> getArtistNameForIndicatedSong(@Path("song_id") int song_id);
    @GET("/songs/{type_id}/songs")
    Call<ArrayList<com.example.dictionary.Activity.Model.Song>> getSongsBasedOnType(@Path("type_id") int type_id);
    @GET("/{artist_id}/artist")
    Call<ArrayList<Artist>> getArtistIndexed(@Path("artist_id") String artist_id);
    @GET("/{album_id}/songs")
    Call<ArrayList<com.example.dictionary.Activity.Model.Song>> getSongsOnAlbumId(@Path("album_id") int album_id);
    @GET("/{album_id}/artist/name")
    Call<ArrayList<Artist>> getArtistOnAlbumId(@Path("album_id") int album_id);
    @GET("/{song_id}/type_name")
    Call<ArrayList<Type>> getTypeOnSongId(@Path("song_id") int song_id);
    @GET("/{song_id}/album_name")
    Call<ArrayList<Album>> getAlbumOnSongId(@Path("song_id") int song_id);
    @GET("/artist/{artist_id}/songs")
    Call<ArrayList<Song>> getSongsOnArtistId(@Path("artist_id") int artist_id);
    @GET("/group/{artist_id}")
    Call<ArrayList<Artist>> getMemberOf(@Path("artist_id") int artist_id);
    @GET("/songs/appear/{artist_id}")
    Call<ArrayList<Song>> getAppearOnSong(@Path("artist_id") int artist_id);
    @GET("/albumshot")
    Call<ArrayList<Album>> getAlbumsHot();
    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();
    @GET("/typeshot")
    Call<ArrayList<Type>> getTypeHot();
    @GET("/types")
    Call<ArrayList<Type>> getTypes();
    @GET("/type/{hint}/types")
    Call<ArrayList<Type>> getSearchedType(@Path("hint") String hint);
    @GET("/artist/{hint}/artists")
    Call<ArrayList<Artist>> getSearchedArtists(@Path("hint") String hint);
    @GET("/album/{hint}/albums")
    Call<ArrayList<Album>> getSearchedAlbums(@Path("hint") String hint);
    @GET("/number/{username}")
    Call<User> getNumberOnUsername(@Path("username") String username);
    @GET("/password/{username}")
    Call<User> getPassword(@Path("username") String username);
    @GET("/artists/{artist_id}/groups")
    Call<ArrayList<Artist>> getSingersOnGroupId(@Path("artist_id") int artist_id);
    @GET("/user/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("/register")
    Call<User> postRegister(@Body User user);
    @POST("/login")
    Call<User> postLogin(@Body User user);


}
