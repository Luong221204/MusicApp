package com.example.dictionary.Activity.Model;

public class Options {
    int icon;
    String name;
    boolean flagDisable=false;
    public Options(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public boolean getFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
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
