package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Interface.View.MainActivityInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.View.NaviFragment.AccountFragment;
import com.example.dictionary.Activity.View.NaviFragment.ExploreFragment;
import com.example.dictionary.Activity.View.NaviFragment.LibraryFragment;
import com.example.dictionary.Activity.View.Activity.MainActivity;
import com.example.dictionary.Activity.View.NaviFragment.SearchFragment;

import java.util.ArrayList;

public class UIPresenter {
    private final MainActivityInterface uiInterface;
    public UIPresenter(MainActivityInterface uiInterface){
        this.uiInterface=uiInterface;
    }
    public void smallSongView(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if(bundle != null){
            int action= bundle.getInt(MyApplication.ACTION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Song song=bundle.getSerializable(MyApplication.SONG,Song.class);
                uiInterface.smallSongView(song,action);
            }
        }
    }
    public void bottomNavigationView(MainActivity mainActivity, MenuItem item){
        FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction transaction=fragmentManager.beginTransaction();
        uiInterface.bottomNavigationView(item,transaction);

    }
    public ArrayList<Fragment> getFragments(Bundle bundle,MainActivity mainActivity,int frameLayoutId){
        ArrayList<Fragment> fragments=new ArrayList<>();
        ExploreFragment homeFragment;
        AccountFragment accountFragment;
        LibraryFragment libraryFragment;
        if(bundle == null){
            homeFragment=new ExploreFragment();
            accountFragment=new AccountFragment();
            libraryFragment=new LibraryFragment();
            mainActivity.getSupportFragmentManager().beginTransaction().add(frameLayoutId,homeFragment,"EXPLORE")
                    .add(frameLayoutId,accountFragment,"ACCOUNT")
                    .add(frameLayoutId,libraryFragment,"LIBRA")
                    .hide(homeFragment).hide(accountFragment).commit();
        }else{
            homeFragment= (ExploreFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("EXPLORE");
            accountFragment= (AccountFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("ACCOUNT");
            libraryFragment= (LibraryFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("LIBRA");
        }
        fragments.add(libraryFragment);
        fragments.add(homeFragment);
        fragments.add(accountFragment);
        return fragments;

    }
    public void showTitleToolbar(Bundle bundle){
        if(bundle != null){
            uiInterface.showTitleToolbar(bundle.getString(MyApplication.FRAGMENTCHOSEN));
        }
    }
    public void showSearchFragment(MainActivity mainActivity,int frameLayoutId){
        FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameLayoutId,new SearchFragment()).addToBackStack(SearchFragment.class.getName());
        mainActivity.isShowed=true;
        uiInterface.showSearchFragment(fragmentTransaction);
    }
    public void restoreSearchFragment(Bundle bundle,MainActivity mainActivity,int frameLayoutId){
        if(bundle != null){
            mainActivity.isShowed=bundle.getBoolean(MyApplication.ISSHOWED);
        }
        if(mainActivity.isShowed){
            FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
            @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutId,new SearchFragment()).addToBackStack(SearchFragment.class.getName());
            uiInterface.showSearchFragment(fragmentTransaction);
        }
    }
}
