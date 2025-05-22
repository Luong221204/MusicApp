package com.example.dictionary.Activity.RecentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.example.dictionary.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class RecentActivity extends AppCompatActivity implements RecentInterface {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    RecentPresenter recentPresenter=new RecentPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recent);
        toolbar=findViewById(R.id.toolbar);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recentPresenter.onTabs(this);
        new TabLayoutMediator(tabLayout,viewPager,new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                recentPresenter.onTabLayout(tab,position);
            }
        }).attach();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    public void onTabLayout(TabLayout.Tab tab, String title) {
        tab.setText(title);
    }

    @Override
    public void onTabs(SearchViewPagerAdapter searchViewPagerAdapter) {
        viewPager.setAdapter(searchViewPagerAdapter);
        viewPager.setUserInputEnabled(true);
    }
}