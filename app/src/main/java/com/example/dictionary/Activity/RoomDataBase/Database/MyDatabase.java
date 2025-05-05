package com.example.dictionary.Activity.RoomDataBase.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dictionary.Activity.RoomDataBase.DAO.UserDAO;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;

@Database(entities = {Song.class},version = 2)
public abstract class MyDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="Song_favourite";
    private static MyDatabase instance;
    public static synchronized MyDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context, MyDatabase.class,DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();

}
