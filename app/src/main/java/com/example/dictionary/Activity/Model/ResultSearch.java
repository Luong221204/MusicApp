package com.example.dictionary.Activity.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultSearch {
    @SerializedName("songs")
    List<Search> songs;
    @SerializedName("artists")
    List<Search> artists;

    public List<Search> getSongs() {
        return songs;
    }

    public void setSongs(List<Search> songs) {
        this.songs = songs;
    }

    public List<Search> getArtists() {
        return artists;
    }

    public void setArtists(List<Search> artists) {
        this.artists = artists;
    }
}

