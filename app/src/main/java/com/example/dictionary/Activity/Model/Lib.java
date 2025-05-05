package com.example.dictionary.Activity.Model;

public class Lib {
    int icon;
    String name;
    int count=0;

    public Lib(int icon, String name) {
        this.icon = icon;
        this.name = name;
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
    public void setCount(int count){
        this.count=count;
    }
    public void addMore(int count){
        this.count=this.count+count;
    }
    public int getCount(){
        return this.count;
    }
}
