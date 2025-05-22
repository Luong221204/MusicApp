package com.example.dictionary.Activity.Model;

import androidx.room.Entity;

import java.io.Serializable;
@Entity(tableName = "song_recently",primaryKeys = {"song_id","userId"})
public class Song extends Behalf implements Serializable {
    public String song_name,song_uri,song_image,artist_name,type_name,album_name,message;
    public int song_id,artist_id=0,album_id,day_launched, userId;
    public boolean isLoved=false;
    public long time;
    public Song(int followed, int type) {
        super(followed, type);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Song(String name, String uri, int artist_id, int id, String song_image, int day_launched, int album_id, String artist_name, String type_name, String album_name, int followed) {
        super(0,followed);
        this.song_name = name;
        this.album_name=album_name;
        this.song_uri =uri;
        this.artist_id = artist_id;
        this.song_id=id;
        this.song_image=song_image;
        this.album_id=album_id;
        this.type_name=type_name;
        this.artist_name=artist_name;
        this.day_launched=day_launched;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getDay_launched() {
        return day_launched;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setDay_launched(int day_launched) {
        this.day_launched = day_launched;
    }

    public void setImage(String image) {
        this.song_image = image;
    }

    public int getId() {
        return song_id;
    }

    public void setId(int id) {
        this.song_id = id;
    }

    public String getName() {
        return song_name;
    }

    public void setName(String name) {
        this.song_name = name;
    }

    public int getArtistId() {
        return artist_id;
    }

    public void setSinger(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getImage() {
        return song_image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return song_uri;
    }

    public void setUrl(String url) {
        this.song_uri = url;
    }

    public boolean isLoved() {
        return isLoved;
    }

    public void setLoved(boolean loved) {
        isLoved = loved;
    }
}
