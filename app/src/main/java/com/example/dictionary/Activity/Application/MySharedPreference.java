package com.example.dictionary.Activity.Application;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dictionary.Activity.Model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

public class MySharedPreference {
    private SharedPreferences sharedPreferences;
    private final Context context;
    private SharedPreferences.Editor editor;
    public MySharedPreference(Context context){
        this.context=context;
    }
    public void setRoutine(Set<String> routines){
        sharedPreferences=context.getSharedPreferences(MyApplication.MYROUTINE,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putStringSet(MyApplication.MYROUTINE,routines);
        editor.apply();
    }
    public Set<String> getRoutines(){
        sharedPreferences=context.getSharedPreferences(MyApplication.MYROUTINE,Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(MyApplication.MYROUTINE,null);
    }
    public void setFirstInstalled(){
        sharedPreferences=context.getSharedPreferences(MyApplication.MySTORE,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean(MyApplication.MySTORE,true);
    }
    public boolean getFirstInstalled(){
        sharedPreferences=context.getSharedPreferences(MyApplication.MYROUTINE,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(MyApplication.MySTORE,false);
    }
    public void saveUser(User user){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String user_data=gson.toJson(user);
        editor=sharedPreferences.edit();
        editor.putString(MyApplication.DATA,user_data);
        editor.apply();
    }
    public User getUser(){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        String user_data=sharedPreferences.getString(MyApplication.DATA,"");
        if(user_data.isEmpty()) return null;
        Gson gson=new Gson();
        return gson.fromJson(user_data,User.class);
    }
    public void saveUserToken(String token){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putString(MyApplication.TOKEN,token);
        editor.apply();
    }
    public String getToken(){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        return sharedPreferences.getString(MyApplication.TOKEN,"");
    }
    public void setLoginState(boolean isLogged){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putBoolean(MyApplication.LOG,isLogged);
        editor.apply();
    }
    public boolean getLoginState(){
        sharedPreferences=context.getSharedPreferences(MyApplication.USER,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(MyApplication.LOG,false);
    }

}
