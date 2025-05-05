package com.example.dictionary.Trash;

import android.content.Context;

public class DataManager {
    public static DataManager instance;
    public MySharedPreference mySharedPreference;
    public static void init(Context context){
            instance=new DataManager();
            instance.mySharedPreference=new MySharedPreference(context);

    }
    public static DataManager getInstance(){
        if(instance==null){
            instance=new DataManager();
        }
        return instance;
    }
    public static void setStatusInstall(String key,boolean value){
        DataManager.getInstance().mySharedPreference.setInstall(key,true);
    }
    public static boolean getStaticInstalled(String key){
        return DataManager.getInstance().mySharedPreference.getInstalled(key);
    }

}
