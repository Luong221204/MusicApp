package com.example.dictionary.Activity.Model;

import java.util.ArrayList;

public class Artist extends Behalf {
    int artist_id;
    String artist_name;
    int date_of_born;
    String artist_image;

    public Artist( String name, int date_of_born, int id,String image,int followed) {
        super(1,followed);
        this.artist_name = name;
        this.date_of_born = date_of_born;
        this.artist_image=image;
        this.artist_id = id;
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


}
