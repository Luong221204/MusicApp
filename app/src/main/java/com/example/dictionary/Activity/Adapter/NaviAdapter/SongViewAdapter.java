package com.example.dictionary.Activity.Adapter.NaviAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dictionary.Activity.VIewSongActivity.InforFragment.InforFragment;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragment;

public class SongViewAdapter extends FragmentStatePagerAdapter {
    public SongViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:return new ViewFragment();
            case 1:return new InforFragment();
            default:return new ViewFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
