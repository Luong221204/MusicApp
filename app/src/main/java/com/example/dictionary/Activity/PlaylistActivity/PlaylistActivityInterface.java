package com.example.dictionary.Activity.PlaylistActivity;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface PlaylistActivityInterface {
    void onImage(String source);
    void showMessage(String message);
    void onToolbar(String name);
    void onListSongs(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager);
    void showBottomSheet(BottomFragment bottomFragment);
    ActivityResultLauncher<Intent> getLauncher();
    void requestPermission(String[] permissions,int requestCode);
}
