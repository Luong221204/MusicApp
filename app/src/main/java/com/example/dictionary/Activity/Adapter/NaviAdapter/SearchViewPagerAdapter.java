package com.example.dictionary.Activity.Adapter.NaviAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.OutStandingFragment;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.PlayListFragment;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.SingersFragment;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.SongFragment;

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
            bundle.putString(MyApplication.ACTION,MyApplication.DATA);
            SongFragment songFragment=new SongFragment();
            songFragment.setArguments(bundle);
            switch(position){
                case 0: return songFragment;
                case 1: return new SingersFragment();
                case 2: return new PlayListFragment();
                default:return songFragment;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(tag==1) return 4;
        else return 3;
    }

}
