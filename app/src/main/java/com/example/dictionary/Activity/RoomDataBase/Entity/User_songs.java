package com.example.dictionary.Activity.RoomDataBase.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "songUser",foreignKeys = @ForeignKey(
        entity = Song.class,
        parentColumns = "song_id",
        childColumns = "song_id",
        onDelete = ForeignKey.CASCADE
),primaryKeys ={"userId","song_id"})
public class User_songs {
    private int userId;
    private int song_id;
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }
}
