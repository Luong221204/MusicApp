package com.example.dictionary.Activity.View.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Adapter.NaviAdapter.SongViewAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Interface.View.OutStandFragmentInterface;
import com.example.dictionary.Activity.Interface.View.ViewSongActivityInterface;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.OutstandingPresenter;
import com.example.dictionary.Activity.Presenter.ViewPresenter.ViewSongPresenter;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.example.dictionary.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class ViewSongActivity extends AppCompatActivity implements OutStandFragmentInterface, ViewSongActivityInterface, ItemClickListener {
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
        back.setOnClickListener(v-> {
            if(MyApplication.list.isEmpty()) finish();
            else onBackFinish();
        });
        more.setOnClickListener(v -> {
            outstandingPresenter.showBottomSheet(MyApplication.song);
        });
        viewPager=findViewById(R.id.view_pager);
        SongViewAdapter songViewAdapter=new SongViewAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(songViewAdapter);
        textView=findViewById(R.id.title);
        info=findViewById(R.id.info);
        cont=findViewById(R.id.cont);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.move);
        textView.setText(MyApplication.song.getName());
        textView.setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:{
                        textView.setSelected(true);
                        cont.setImageResource(R.drawable.dot);
                        info.setImageResource(R.drawable.dot_fade);
                        textView.setText(MyApplication.song.getName());
                        break;
                    }
                    case 1:{
                        textView.setSelected(false);
                        cont.setImageResource(R.drawable.dot_fade);
                        info.setImageResource(R.drawable.dot);
                        textView.setText("Thông tin");
                        break;
                    }
                    default:textView.setText(MyApplication.song.getName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        if(MyApplication.list ==null) finish();
        RelativeLayout relativeLayout;
        RecyclerView recyclerView;
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.history_layout);
        Window window=dialog.getWindow();
        if(window==null) return;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams win=window.getAttributes();
        win.gravity= Gravity.CENTER;
        window.setAttributes(win);
        recyclerView=dialog.findViewById(R.id.recycle);
        relativeLayout=dialog.findViewById(R.id.back);
        RecycleAdapter resultSearchAdapter=new RecycleAdapter(MyApplication.list,ViewSongActivity.this,3,ViewSongActivity.this,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ViewSongActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultSearchAdapter);
        relativeLayout.setOnClickListener(v->{finish();});
        dialog.show();

    }

    @Override
    public void onRecycle(ArrayList<Behalf> arrayList) {

    }

    @Override
    public void onSongName(String name) {
        textView.setText(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClickListener(Song song) {

    }
}