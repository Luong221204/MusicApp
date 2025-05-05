package com.example.dictionary.Activity.RoomDataBase.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "songs")
public class Song implements Serializable {
    private String name,singer,image,album_name,url,type_name;
    @PrimaryKey()
    private int song_id;
    private int year_launched;

    public Song(String name, String singer, String image, String album_name, String url, int song_id, int year_launched,String type_name) {
        this.name = name;
        this.singer = singer;
        this.image = image;
        this.album_name = album_name;
        this.url = url;
        this.song_id = song_id;
        this.type_name=type_name;
        this.year_launched = year_launched;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
