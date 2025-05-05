package com.example.dictionary.Activity.Application;

public class DownloadSong {
    long image_id,uri_id;
    int song_id,year_launched;
    String image="",uri="",song_name,artists_name,album="",type_name;

    public DownloadSong() {
    }

    public long getImage_id() {
        return image_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public long getUri_id() {
        return uri_id;
    }

    public void setUri_id(long uri_id) {
        this.uri_id = uri_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getYear_launched() {
        return year_launched;
    }

    public void setYear_launched(int year_launched) {
        this.year_launched = year_launched;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getArtists_name() {
        return artists_name;
    }

    public void setArtists_name(String artists_name) {
        this.artists_name = artists_name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
