package com.example.dictionary.Activity.Application;

import android.content.Context;

import com.example.dictionary.Activity.Model.User;

import java.util.Set;

public class DataManager {
    public static DataManager instance;
    public MySharedPreference mySharedPreference;
    public static void init(Context context){
        instance=new DataManager();
        instance.mySharedPreference=new MySharedPreference(context);
    }
    public static DataManager getInstance(){
        if(instance == null){
            instance=new DataManager();
        } return instance;
    }
    public void setRoutine(Set<String> routines){
        DataManager.getInstance().mySharedPreference.setRoutine(routines);
    }
    public Set<String> getRoutines(){
        return DataManager.getInstance().mySharedPreference.getRoutines();
    }
    public void setFirstInstalled(){
        DataManager.getInstance().mySharedPreference.setFirstInstalled();
    }
    public static boolean getFirstInstalled(){
        return DataManager.getInstance().mySharedPreference.getFirstInstalled();
    }
    public void saveUser(User user){
        DataManager.getInstance().mySharedPreference.saveUser(user);
    }
    public User getUser(){
        return DataManager.getInstance().mySharedPreference.getUser();
    }
    public void saveToken(String token){
        DataManager.getInstance().mySharedPreference.saveUserToken(token);
    }
    public String getToken(){
        return DataManager.getInstance().mySharedPreference.getToken();
    }
    public void setLoginState(boolean isLog){
        DataManager.getInstance().mySharedPreference.setLoginState(isLog);
    }
    public boolean getLoginState(){
        return DataManager.getInstance().mySharedPreference.getLoginState();
    }
}
