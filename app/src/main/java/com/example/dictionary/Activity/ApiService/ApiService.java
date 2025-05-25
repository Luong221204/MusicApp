package com.example.dictionary.Activity.ApiService;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Comment;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.ResultSearch;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd/mm/yyyy").create();
    OkHttpClient okHttp = new OkHttpClient.Builder().
            connectTimeout(20, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS)
            .build();
    ApiService apiService = new Retrofit.Builder().baseUrl("http://192.168.1.8:2000").
            addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(ApiService.class);

    @GET("/songs")
    Call<ArrayList<Song>> getSongs();

    @GET("/song/{hint}/songs")
    Call<ArrayList<Song>> getSearchSongs(@Path("hint") String hint);

    @GET("/song/{hint}/songs")
    Observable<ArrayList<Song>> getSearchSongs2(@Path("hint") String hint);

    @GET("/{artist_id}/albums")
    Call<ArrayList<Album>> getAlbumOnArtistId(@Path("artist_id") int artist_id);

    @GET("/albums/artist/{artists}")
    Observable<ArrayList<Album>> getAlbumOnArtistId2(@Path("artists") String artists);

    @GET("/{song_id}/name")
    Call<ArrayList<Artist>> getArtistNameForIndicatedSong(@Path("song_id") int song_id);

    @GET("/{song_id}/song")
    Call<Song> getSongOnSongId(@Path("song_id") int song_id);

    @GET("/artists/name/{songs}")
    Observable<ArrayList<Artist>> getArtistNameForIndicatedSong2(@Path("songs") String songs);

    @GET("/songs/{type_id}/songs")
    Call<ArrayList<Song>> getSongsBasedOnType(@Path("type_id") int type_id);

    @GET("/{artist_id}/artist")
    Call<ArrayList<Artist>> getArtistIndexed(@Path("artist_id") String artist_id);

    @GET("/{album_id}/songs")
    Call<ArrayList<Song>> getSongsOnAlbumId(@Path("album_id") int album_id);

    @GET("/{album_id}/artist/name")
    Call<ArrayList<Artist>> getArtistOnAlbumId(@Path("album_id") int album_id);

    @GET("/{song_id}/type_name")
    Call<ArrayList<Type>> getTypeOnSongId(@Path("song_id") int song_id);

    @GET("/{song_id}/album_name")
    Call<ArrayList<Album>> getAlbumOnSongId(@Path("song_id") int song_id);

    @GET("/albums/name/{songs}")
    Observable<List<Album>> getAlbumOnSongId2(@Path("songs") String songs);

    @GET("/artist/{artist_id}/songs")
    Call<ArrayList<Song>> getSongsOnArtistId(@Path("artist_id") int artist_id);

    @GET("/songs/name/{artists}")
    Observable<ArrayList<Song>> getSongsOnArtistId2(@Path("artists") String artists);

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

    @GET("/artist/{hint}/artists")
    Observable<ArrayList<Artist>> getSearchedArtists2(@Path("hint") String hint);

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

    @GET("/search/{hint}/albums")
    Observable<List<Search>> getSearchAlbums(@Path("hint") String hint);

    @GET("/search/{hint}")
    Observable<ResultSearch> getSearch(@Path("hint") String hint);

    @GET("/playlist/{userId}")
    Call<ArrayList<Playlist>> getPlaylists(@Path("userId") int userId);

    @GET("/playlist/{playlist_id}/songs")
    Call<ArrayList<Song>> getSongsBaseOnPlaylist(@Path("playlist_id") int playlist_id);

    @GET("/favourite/{userId}/songs")
    Call<ArrayList<Song>> getFavouriteSongs(@Path("userId") int userId);

    @GET("/favourite/artist/{userId}}")
    Call<ArrayList<Artist>> getFavouriteArtists(@Path("userId") int userId);

    @GET("/favourite/album/{userId}}")
    Call<ArrayList<Album>> getFavouriteAlbums(@Path("userId") int userId);

    @GET("/downloaded/{userId}")
    Call<ArrayList<Song>> getDownloaded(@Path("userId") int userId);

    @POST("/register")
    Call<User> postRegister(@Body User user);

    @POST("/login")
    Call<User> postLogin(@Body User user);

    @POST("playlist")
    Call<Playlist> postPlaylist(@Body Playlist playlist);

    @Multipart
    @POST("/upload")
    Call<User> installAvatar(@Part("userId") RequestBody userId,
                             @Part MultipartBody.Part avatar);

    @POST("/playlist/{playlist_id}/song")
    Call<Song> postSongToPlaylist(@Path("playlist_id") int playlist_id, @Body Song song);

    @Multipart
    @POST("/uploadPlaylist")
    Call<Playlist> postImage(@Part("playlist_id") RequestBody playlist_id,
                             @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/favourite/song")
    Call<Song> loveOrNot(@Field("userId") int userId,
                         @Field("song_id") int song_id);

    @FormUrlEncoded
    @POST("/favourite/album")
    Call<Album> albumLoveOrNot(@Field("userId") int userId,
                               @Field("album_id") int album_id);

    @FormUrlEncoded
    @POST("/favourite/artist")
    Call<Artist> artistLoveOrNot(@Field("userId") int userId,
                                 @Field("artist_id") int artist_id);

    @FormUrlEncoded
    @POST("/download")
    Call<Song> postDownload(@Field("userId") int userId,
                            @Field("song_id") int song_id);

    @GET("/comments/{song_id}/{parent_id}/{userId}")
    Call<ArrayList<Comment>> getCommentForSong(@Path("song_id") int song_id,@Path("parent_id") int parent_id,@Path("userId")int userId);

    @POST("/comments")
    Call<Comment> postComment(@Body Comment comment);

    @FormUrlEncoded
    @POST("/comments/like")
    Call<Comment> postLikeComment(@Field("userId") int userId,@Field("comment_id") int comment_id,@Field("status") int status);

}
