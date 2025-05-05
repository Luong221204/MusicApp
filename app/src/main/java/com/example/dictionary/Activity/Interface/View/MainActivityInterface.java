package com.example.dictionary.Activity.Interface.View;

import android.view.MenuItem;

import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Model.Song;

public interface MainActivityInterface {
    public void smallSongView(Song song,int action);
    public void bottomNavigationView(MenuItem item, FragmentTransaction fragmentTransaction);
    public void showSearchFragment(FragmentTransaction fragmentTransaction);
    public void showTitleToolbar(String text);


}
