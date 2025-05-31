package com.example.dictionary.Activity.MainActivity.ExploreFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.Adapter.FormAdapter.RecentAdapter;
import com.example.dictionary.Activity.TypeAdapter.TypeAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface ExploreFragmentInterface {
    void showOnHintRecycle(LinearLayoutManager linearLayoutManager, RecentAdapter adapter);
    void showOnIdol(String image,String name);
    void showOnIdolAlbumRecycle(AlbumAdapter albumAdapter, LinearLayoutManager layoutManager);
    void showOnThemeRecycle(TypeAdapter typeAdapter, LinearLayoutManager layoutManager2);
    void showBottomSheet(BottomFragment bottomFragment);
    void showOnHistoryRecycle(LaterAdapter laterAdapter, LinearLayoutManager layoutManager1);
    void showOnHotAlbum(AlbumAdapter albumAdapter,LinearLayoutManager layoutManager);
    void onInternetConnect(int show,int hide,int hint);
}
