package com.example.dictionary.Activity.Model;

import java.io.Serializable;

public class Type implements Serializable {
    String title,image;
    int theme_id;

    public Type(String title, int id,String image) {
        this.title = title;
        this.theme_id = id;
        this.image=image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return theme_id;
    }

    public void setId(int id) {
        this.theme_id = id;
    }
}
