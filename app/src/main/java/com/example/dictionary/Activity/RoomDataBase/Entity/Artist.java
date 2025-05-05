package com.example.dictionary.Activity.RoomDataBase.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "artists")
public class Artist {
    @PrimaryKey(autoGenerate = true)
    private int artist_id;
    private String name;
    private int date_of_born;
    private String image;
    private long popular=0;

    public Artist(int artist_id, int date_of_born, String name, String image, long popular) {
        this.artist_id = artist_id;
        this.date_of_born = date_of_born;
        this.name = name;
        this.image = image;
        this.popular = popular;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int id) {
        this.artist_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate_of_born() {
        return date_of_born;
    }

    public void setDate_of_born(int date_of_born) {
        this.date_of_born = date_of_born;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPopular() {
        return popular;
    }

    public void setPopular(long popular) {
        this.popular = popular;
    }
}
