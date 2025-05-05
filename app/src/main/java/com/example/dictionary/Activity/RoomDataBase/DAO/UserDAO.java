package com.example.dictionary.Activity.RoomDataBase.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dictionary.Activity.RoomDataBase.Entity.Artist;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("Select * FROM songs where LOWER(name) like '%' || LOWER(:string) ||'%'")
    List<Song> getListSongs(String string);

    @Insert
    void insertSong(Song song);
    @Query("SELECT * FROM songs")
    List<Song> getAllSongs();
    @Query("Select * from songs where song_id != :song_id ")
    List<Song> getSongs(int song_id);

}
