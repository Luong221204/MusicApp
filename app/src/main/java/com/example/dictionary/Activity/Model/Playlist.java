package com.example.dictionary.Activity.Model;

import java.util.ArrayList;

public class Playlist {
    int icon;
    String name;
    ArrayList<Song> list;
    public Playlist(int icon, String name,ArrayList<Song> list) {
        this.icon = icon;
        this.name = name;
        this.list=list;
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

    public void setName(String name) {
        this.name = name;
    }
}
