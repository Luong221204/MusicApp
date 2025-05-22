package com.example.dictionary.Activity.Listener;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.MainActivity.UIPresenter;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentChosen implements BottomNavigationView.OnNavigationItemSelectedListener  {
    private final MainActivity mainActivity;
    private final UIPresenter uiPresenter;
    public FragmentChosen(MainActivity mainActivity, UIPresenter uiPresenter){
        this.mainActivity=mainActivity;
        this.uiPresenter=uiPresenter;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.mainActivity.isShowed=false;
        this.uiPresenter.bottomNavigationView(this.mainActivity,item);
        return true;
    }
}
