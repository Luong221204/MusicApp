package com.example.dictionary.Activity.RoomDataBase.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.RoomDataBase.Entity.User_songs;

import java.util.List;
@Dao
public interface UserDAO {
    @Query("Select * FROM songs where LOWER(name) like '%' || LOWER(:string) ||'%'")
    List<Song> getListSongs(String string);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSong(Song song);
    @Query("SELECT * FROM songs")
    List<Song> getAllSongs();
    @Query("Select * from songs where song_id != :song_id ")
    List<Song> getSongs(int song_id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser_Song(User_songs userSongs);

    @Query("SELECT songs.* FROM songUser INNER JOIN songs ON songUser.song_id = songs.song_id WHERE songUser.userId = :userId and songUser.song_id = :song_id")
    List<Song> getDownloaded(int userId,int song_id);

    @Query("SELECT * from songs where song_id = :song_id")
    List<Song> checkDownloaded(int song_id);

    @Query("SELECT *FROM songs inner join songUser on songs.song_id = songUser.song_id where songUser.userId = :userId")
    List<Song> getSongsOnUserId(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecentlySong(com.example.dictionary.Activity.Model.Song song);

    @Query("Select * from song_recently where userId = :userId")
    List<com.example.dictionary.Activity.Model.Song> getRecentlySongs(int userId);

    @Query("SELECT * from song_recently where userId = :userId and song_id = :song_id")
    com.example.dictionary.Activity.Model.Song checkSongRecentlyId(int userId, int song_id);

    @Query("Select * from song_recently where userId = :userId and isLoved = :isLoved")
    List<com.example.dictionary.Activity.Model.Song> getFavouriteSongs(int userId,boolean isLoved);

    @Query("Update song_recently set isLoved = :isLoved where userId = :userId and song_id = :song_id  ")
    void updateStatus(boolean isLoved,int userId,int song_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArtist(com.example.dictionary.Activity.Model.Artist artist);

    @Query("SElect * from artists2 where userId = :userId and artist_id = :artist_id")
    Artist checkArtist(int userId, int artist_id);

    @Query("SElect * from artists2 where userId = :userId ")
    List<Artist> getArtistsOnUserId(int userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album album);

    @Query("SElect * from albums2 where userId = :userId and album_id = :album_id")
    Album checkAlbum(int userId,int album_id);

    @Query("SElect * from albums2 where userId = :userId ")
    List<Album> getAlbumsOnUserId(int userId);

    @Query("Update artists2 set isLoved = :isLoved where userId = :userId and artist_id =  :artist_id")
    void updateStatusArtist(boolean isLoved,int userId,int artist_id);

    @Query("UPDATE song_recently set time = :time where userId = :userId and song_id = :song_id")
    void updateTimeForSongRecently(long time,int userId,int song_id);

    @Query("Select * from song_recently where userId = :userId and isLoved = :isLoved ORDER BY time asc")
    List<com.example.dictionary.Activity.Model.Song> getOldestFavouriteSongs(int userId,boolean isLoved);

    @Query("Select * from song_recently where userId = :userId and isLoved = :isLoved ORDER BY song_name asc")
    List<com.example.dictionary.Activity.Model.Song> getASCNameFavouriteSongs(int userId,boolean isLoved);

    @Query("Select * from song_recently where userId = :userId and isLoved = :isLoved ORDER BY time desc")
    List<com.example.dictionary.Activity.Model.Song> getNewestFavouriteSongs(int userId,boolean isLoved);

    @Query("Select * from songs where userId = :userId ORDER BY time asc")
    List<Song> getOldestDownloadedSongs(int userId);

    @Query("Select * from songs where userId = :userId  ORDER BY name asc")
    List<Song> getASCNameDownloadedSongs(int userId);

    @Query("Select * from songs where userId = :userId  ORDER BY time desc")
    List<Song> getNewestDownloadedSongs(int userId);

    @Query("Select * from songs where userId = :userId  ORDER BY singer asc")
    List<Song> getArtistASCDownloadedSongs(int userId);
}

