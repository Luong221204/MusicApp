package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface ArtistActivityInterface {
    void onInit(String name,String image,int followers);
    void onSongs(ArrayList<Song> songs);
    void onRelateArtists(ArrayList<Artist> artists);
    void onSmallImage(int action);
    void onAlbums(ArrayList<Album> albums);
    void showBottomSheet(BottomFragment bottomFragment);
    void onAppearIn(ArrayList<Song> songs);
    void onMemberOf(ArrayList<Artist> bands);
    void onMembers(ArrayList<Artist> artists);

}
