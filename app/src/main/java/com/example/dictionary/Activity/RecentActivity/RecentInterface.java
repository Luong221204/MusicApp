package com.example.dictionary.Activity.RecentActivity;

import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public interface RecentInterface {
    void onTabLayout(TabLayout.Tab tab, String title);
    void onTabs(SearchViewPagerAdapter searchViewPagerAdapter);
}
