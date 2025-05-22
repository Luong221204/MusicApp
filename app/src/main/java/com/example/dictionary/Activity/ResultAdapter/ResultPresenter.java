package com.example.dictionary.Activity.ResultAdapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Listener.EditorActionListener;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.SearchFragment.SearchFragmentInterface;

import java.util.ArrayList;

public class ResultPresenter {
    ArrayList<Artist> artists=new ArrayList<>();
    ArrayList<Song> songs=new ArrayList<>();
    public void onClick(Context context, int position, int index, ArrayList<Search> searches, SearchFragmentInterface searchFragmentInterface){
        if(position>=index){
            getSearchForSong(searches.get(position).getSearch(),context);
            searchFragmentInterface.showSearch();
            searchFragmentInterface.hideKeyBoard(searchFragmentInterface.getSearchView());
            return;
        }
        getSearchForArtist(searches.get(position).getSearch(),context);
        searchFragmentInterface.showSearch();
        searchFragmentInterface.hideKeyBoard(searchFragmentInterface.getSearchView());

    }
    @SuppressLint("CheckResult")
    private void getSearchForArtist(String hint,Context context) {
        MyApplication.behalves.clear();
        MyApplication.albums.clear();
        artists.clear();
        songs.clear();
        ApiService.apiService.getSearchedArtists2(hint)
                .doOnNext(artists1 -> artists.addAll(artists1))
                .map(this::getArtists)
                .doOnNext(s->{ApiService.apiService.getAlbumOnArtistId2(s).subscribe(value->MyApplication.albums.addAll(value));})
                .flatMap(ApiService.apiService::getSongsOnArtistId2)
                .subscribe(value ->{
                    songs.addAll(value);
                    EditorActionListener.onSetupSongs(songs);
                    EditorActionListener.onSetupArtists(artists);
                    MyApplication.behalves.addAll(artists);
                    MyApplication.behalves.addAll(songs);
                    EditorActionListener.sendBroadcast(context);
                });
    }
    @SuppressLint("CheckResult")
    private void getSearchForSong(String hint,Context context) {
        MyApplication.behalves.clear();
        MyApplication.albums.clear();
        artists.clear();
        songs.clear();
        ApiService.apiService.getSearchSongs2(hint)
                .doOnNext(songs1 -> songs.addAll(songs1))
                .map(this::getSongs)
                .doOnNext(s->{ApiService.apiService.getAlbumOnSongId2(s).subscribe(value->MyApplication.albums.addAll(value));})
                .flatMap(ApiService.apiService::getArtistNameForIndicatedSong2)
                .subscribe(value ->{
                    artists.addAll(value);
                    EditorActionListener.onSetupSongs(songs);
                    EditorActionListener.onSetupArtists(artists);
                    MyApplication.behalves.addAll(artists);
                    MyApplication.behalves.addAll(songs);
                    EditorActionListener.sendBroadcast(context);
                });
    }
    private String getSongs(ArrayList<Song> songs){
        StringBuilder stringBuilder=new StringBuilder();
        for(Song song :songs){
            stringBuilder.append(song.getId()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
    private String getArtists(ArrayList<Artist> artists){
        StringBuilder stringBuilder=new StringBuilder();
        for(Artist artist :artists){
            stringBuilder.append(artist.getId()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }
}
