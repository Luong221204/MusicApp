package com.example.dictionary.Activity.ArtistActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ArtistAdapter.ArtistAdapter;
import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface ArtistActivityInterface {
    void onInit(String name,String image,int followers);
    void onSongs(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void onRelateArtists(ArtistAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void onSmallImage(int action);
    void onAlbums(AlbumAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void showBottomSheet(BottomFragment bottomFragment);
    void onAppearIn(RecycleAdapter recycleAdapter,LinearLayoutManager layoutManager);
    void onMemberOf(ArtistAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void onMembers(ArtistAdapter recycleAdapter,LinearLayoutManager layoutManager);
    void onFollowButton(String text);
    void onMessage(String message);

}
