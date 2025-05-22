package com.example.dictionary.Activity.RecentActivity;

import androidx.fragment.app.FragmentActivity;

import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class RecentPresenter {
    RecentInterface recentInterface;

    public RecentPresenter(RecentInterface recentInterface) {
        this.recentInterface = recentInterface;
    }
    public void onTabLayout(TabLayout.Tab tab, int position){
        switch (position){
            case 0:recentInterface.onTabLayout(tab,"Bài hát");break;
            case 1:recentInterface.onTabLayout(tab,"Ca sĩ");break;
            case 2:recentInterface.onTabLayout(tab,"Playlist");break;
            default:recentInterface.onTabLayout(tab,"Bài hát");
        }
    }
    public void onTabs(FragmentActivity context){
        SearchViewPagerAdapter searchViewPagerAdapter=new SearchViewPagerAdapter(context,2);
        recentInterface.onTabs(searchViewPagerAdapter);


    }
}
