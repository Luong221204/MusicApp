package com.example.dictionary.Activity.Model;

import androidx.room.Entity;

import java.io.Serializable;
import java.util.ArrayList;
@Entity(tableName = "albums2",primaryKeys = {"album_id","userId"})
public class Album implements Serializable {
    public String album_name,album_image;
    public int year_launched,album_id,userId;
    public boolean isLoved=false;
    public Album(int id,String name,int y,String image){
        this.year_launched=y;
        this.album_image=image;
        this.album_id=id;
        this.album_name=name;
    }

    public Album() {
    }

    public boolean isLoved() {
        return isLoved;
    }

    public void setLoved(boolean loved) {
        isLoved = loved;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return album_name;
    }

    public void setName(String name) {
        this.album_name = name;
    }

    public String getImage() {
        return album_image;
    }

    public void setImage(String image) {
        this.album_image = image;
    }

    public int getYear_launched() {
        return year_launched;
    }

    public void setYear_launched(int year_launched) {
        this.year_launched = year_launched;
    }

    public int getId() {
        return album_id;
    }

    public void setId(int id) {
        this.album_id = id;
    }
}
