package com.example.dictionary.Activity.Model;

import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;
@Entity(tableName = "playlists",primaryKeys = {"userId","playlist_id"})
public class Playlist implements Serializable {
    int icon;
    int userId;
    int playlist_id;
    String name,image,message;
    ArrayList<Song> list;
    public Playlist(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public ArrayList<Song> getList() {
        return list;
    }

    public void setList(ArrayList<Song> list) {
        this.list = list;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }
}
