package com.example.dictionary.Activity.VIewSongActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Adapter.NaviAdapter.SongViewAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.Finisher;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.OutstandingFragment.OutStandFragmentInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.OutstandingFragment.OutstandingPresenter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

public class ViewSongActivity extends AppCompatActivity implements OutStandFragmentInterface, ViewSongActivityInterface, ItemClickListener, Finisher {
    ViewPager viewPager;
    TextView textView;
    ImageView back,more,info,cont;
    OutstandingPresenter outstandingPresenter;
    ViewSongPresenter viewSongPresenter=new ViewSongPresenter(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ISBACK.equals(intent.getAction())) viewSongPresenter.onInit(intent);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.ISBACK);
        intentFilter.addAction(MyApplication.ISBACKOFF);
        registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_song);
        outstandingPresenter=new OutstandingPresenter(this);
        back=findViewById(R.id.down);
        more=findViewById(R.id.more);
        viewPager=findViewById(R.id.view_pager);
        textView=findViewById(R.id.title);
        info=findViewById(R.id.info);
        cont=findViewById(R.id.cont);
        textView.setSelected(true);
        back.setOnClickListener(v-> {
            viewSongPresenter.onBackFinish(this,this,this);
        });
        more.setOnClickListener(v -> {
            outstandingPresenter.showBottomSheet(MyApplication.song);
        });
        viewSongPresenter.onCreateAc(getSupportFragmentManager());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onBackFinish() {

    }

    @Override
    public void onRecycle(ContentAdapter albumAdapter, LinearLayoutManager layoutManager) {

    }

    @Override
    public void onSongName(String name) {
        textView.setText(name);

    }

    @Override
    public void onTabLayout(boolean textSate,int sourceCont, int sourceDot,String badge) {
        textView.setSelected(textSate);
        cont.setImageResource(sourceCont);
        info.setImageResource(sourceDot);
        textView.setText(badge);
    }

    @Override
    public void onFinish(Dialog dialog, RecycleAdapter resultSearchAdapter,LinearLayoutManager layoutManager) {
        if(MyApplication.list ==null) finish();
        RelativeLayout relativeLayout;
        RecyclerView recyclerView;
        recyclerView=dialog.findViewById(R.id.recycle);
        relativeLayout=dialog.findViewById(R.id.back);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultSearchAdapter);
        relativeLayout.setOnClickListener(v->{finish();});
        dialog.show();

    }

    @Override
    public void onInit(SongViewAdapter songViewAdapter, String song) {
        viewPager.setAdapter(songViewAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                viewSongPresenter.onTabLayout(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        textView.setText(song);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public void onClickListener(Song song) {

    }

    @Override
    public void onFinish() {
        finish();
    }
}