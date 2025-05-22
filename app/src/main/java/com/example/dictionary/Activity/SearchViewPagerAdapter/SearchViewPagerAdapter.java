package com.example.dictionary.Activity.SearchViewPagerAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.OutstandingFragment.OutStandingFragment;
import com.example.dictionary.Activity.PlaylistFragment.PlayListFragment;
import com.example.dictionary.Activity.SingersFragment.SingersFragment;
import com.example.dictionary.Activity.SongFragment.SongFragment;

public class SearchViewPagerAdapter extends FragmentStateAdapter {
    int tag;
    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,int tag) {
        super(fragmentActivity);
        this.tag=tag;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(tag==1){
            switch(position){
                case 0: return new OutStandingFragment();
                case 1: return new PlayListFragment();
                case 2: return new SingersFragment();
                case 3: return new SongFragment();
                default:return new OutStandingFragment();
            }
        }
        else{
            Bundle bundle=new Bundle();
            bundle.putString(MyApplication.ACTION,MyApplication.OFFLINE);
            Fragment fragment=new Fragment();
            switch(position){
                case 0: fragment=new SongFragment();break;
                case 1: fragment=new SingersFragment();break;
                case 2:fragment=new PlayListFragment();break;
                default:fragment=new SongFragment();
            }
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    @Override
    public int getItemCount() {
        if(tag==1) return 4;
        else return 3;
    }

}
