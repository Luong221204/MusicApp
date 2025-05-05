package com.example.dictionary.Trash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    public final String MY_STORE="MY_STORE";
    public Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MySharedPreference(Context context){
        this.context=context;
        SharedPreferences sharedPreferences= context.getSharedPreferences(MY_STORE,Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor= sharedPreferences.edit();
    }
    public void setInstall(String key,boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getInstalled(String key){
        return sharedPreferences.getBoolean(key,false);
    }
}
