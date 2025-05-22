package com.example.dictionary.Activity.RoomDataBase.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.RoomDataBase.DAO.UserDAO;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.RoomDataBase.Entity.User_songs;

@Database(entities = {Song.class, User_songs.class, com.example.dictionary.Activity.Model.Song.class,
        Artist.class,
        Album.class},version =7)
public abstract class MyDatabase extends RoomDatabase {
    private static final Migration migration_from_6_to_7=new Migration(6,7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE song_recently ADD COLUMN time INTEGER NOT NULL DEFAULT 0");

            supportSQLiteDatabase.execSQL("ALTER TABLE songs ADD COLUMN time INTEGER NOT NULL DEFAULT 0");

            long currentTime = System.currentTimeMillis();

            supportSQLiteDatabase.execSQL("Update songs set time = "+currentTime);

            supportSQLiteDatabase.execSQL("Update song_recently set time = "+currentTime);

            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS playlists (" +
                    "userId INTEGER NOT NULL, " +
                    "playlist_id INTEGER NOT NULL, " +
                    "icon INTEGER , " +
                    "name TEXT, " +
                    "image TEXT, " +
                    "message TEXT, " +
                    "PRIMARY KEY(userId, playlist_id))");
        }
    };
    private static final String DATABASE_NAME="song_download3";
    private static MyDatabase instance;
    public static synchronized MyDatabase getInstance(Context context){
        if(instance == null){
            instance= Room.databaseBuilder(context, MyDatabase.class,DATABASE_NAME).allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();

}
