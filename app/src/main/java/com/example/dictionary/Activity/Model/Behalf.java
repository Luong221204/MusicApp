package com.example.dictionary.Activity.Model;

import java.io.Serializable;

public class Behalf implements Serializable,Comparable<Behalf> {
    int type;
    int followed;

    public Behalf(int followed, int type) {
        this.followed = followed;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    @Override
    public int compareTo(Behalf o) {
        if(this.followed<o.followed) return 1;
        else return -1;
    }
}
