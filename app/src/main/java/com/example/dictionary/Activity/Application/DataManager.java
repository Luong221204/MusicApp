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
    public static void setRoutine(Set<String> routines){
        DataManager.getInstance().mySharedPreference.setRoutine(routines);
    }
    public static Set<String> getRoutines(){
        return DataManager.getInstance().mySharedPreference.getRoutines();
    }
    public static void setFirstInstalled(){
        DataManager.getInstance().mySharedPreference.setFirstInstalled();
    }
    public static boolean getFirstInstalled(){
        return DataManager.getInstance().mySharedPreference.getFirstInstalled();
    }
    public static void saveUser(User user){
        DataManager.getInstance().mySharedPreference.saveUser(user);
    }
    public static User getUser(){
        return DataManager.getInstance().mySharedPreference.getUser();
    }
    public static void saveToken(String token){
        DataManager.getInstance().mySharedPreference.saveUserToken(token);
    }
    public static String getToken(){
        return DataManager.getInstance().mySharedPreference.getToken();
    }
    public void setLoginState(boolean isLog){
        DataManager.getInstance().mySharedPreference.setLoginState(isLog);
    }
    public boolean getLoginState(){
        return DataManager.getInstance().mySharedPreference.getLoginState();
    }
}
