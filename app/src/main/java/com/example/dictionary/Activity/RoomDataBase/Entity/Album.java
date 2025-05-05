package com.example.dictionary.Activity.RoomDataBase.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "albums"
,foreignKeys =@ForeignKey(entity = Artist.class,parentColumns = "artist_id",childColumns = "artist_id",onDelete = ForeignKey.CASCADE)
,indices ={@Index(value="artist_id")} )
public class Album {
    private String name,image;
    private int year_launched;
    private int artist_id;
    @PrimaryKey(autoGenerate = true)
    private int album_id;
    public Album(String name, int year_launched, String image, int id, int artist_id) {
        this.name = name;
        this.year_launched = year_launched;
        this.image = image;
        this.album_id = id;
        this.artist_id = artist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear_launched() {
        return year_launched;
    }

    public void setYear_launched(int year_launched) {
        this.year_launched = year_launched;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int id) {
        this.album_id = id;
    }
}
