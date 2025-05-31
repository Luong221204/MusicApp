package com.example.dictionary.Activity.Model;

import androidx.room.Entity;

import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = "artists2",primaryKeys = {"artist_id","userId"})
public class Artist extends Behalf {
    public int artist_id,userId;
    public String artist_name,message;
    public int date_of_born;
    public String artist_image;
    boolean isLoved=false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(artist_name, artist.artist_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(artist_name);
    }

    public Artist(int followed, int type) {
        super(followed, type);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Artist(String name, int date_of_born, int id, String image, int followed) {
        super(1,followed);
        this.artist_name = name;
        this.date_of_born = date_of_born;
        this.artist_image=image;
        this.artist_id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return artist_id;
    }

    public void setId(int id) {
        this.artist_id = id;
    }

    public String getName() {
        return artist_name;
    }

    public void setName(String name) {
        this.artist_name = name;
    }

    public int getDate_of_born() {
        return date_of_born;
    }

    public void setDate_of_born(int date_of_born) {
        this.date_of_born = date_of_born;
    }

    public String getImage() {
        return artist_image;
    }

    public void setImage(String image) {
        this.artist_image = image;
    }

    public boolean isLoved() {
        return isLoved;
    }

    public void setLoved(boolean loved) {
        isLoved = loved;
    }
}
