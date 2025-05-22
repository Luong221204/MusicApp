package com.example.dictionary.Activity.MainActivity;

import android.view.MenuItem;

import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Model.Song;

public interface MainActivityInterface {
    public void bottomNavigationView(int show,int hide1,int hide2,String title, FragmentTransaction fragmentTransaction);
    public void showSearchFragment(FragmentTransaction fragmentTransaction,int action);
    public void showTitleToolbar(String text);
    public void onInit();


}
