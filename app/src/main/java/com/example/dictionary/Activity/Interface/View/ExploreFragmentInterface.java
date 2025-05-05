package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface ExploreFragmentInterface {
    void showOnHintRecycle(ArrayList<ArrayList<Song>> lists);
    void showOnIdol(String image,String name);
    void showOnIdolAlbumRecycle(ArrayList<Album> albums);
    void showOnThemeRecycle(ArrayList<Type> themes);
    void showBottomSheet(BottomFragment bottomFragment);
    void showOnHistoryRecycle(ArrayList<Later> histories);
    void showOnHotAlbum(ArrayList<Album> albums);
}
