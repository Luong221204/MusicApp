package com.example.dictionary.Activity.MainActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Broadcast.UIBroadcast;
import com.example.dictionary.Activity.Listener.FragmentChosen;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.example.dictionary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    public ImageView imageView,status,back,next;
    public TextView name,singers;
    public ArrayList<Fragment> listFragment;
    public Song song,song2;
    public int id=0;
    public Toolbar toolbar;
    public BottomNavigationView bottomNavigationView;
    public RelativeLayout relativeLayout;
    public FrameLayout frameLayout;
    public boolean isShowed=false;
    public final UIPresenter uiPresenter=new UIPresenter(this);
    private final UIBroadcast uiBroadcast=new UIBroadcast(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.SUCCESS.equals(intent.getAction())) {
                Bundle bundle = intent.getBundleExtra(MyApplication.DATA);
                if(bundle != null){
                    String uri=bundle.getString(MyApplication.URI);
                    long downloadId=bundle.getLong(MyApplication.ID,-1);
                    DownloadSong downloadSong= ViewFragmentPresenter.checkOver(downloadId,uri);
                    if( downloadSong!= null){
                        Toast.makeText(MainActivity.this,"Đã tải thành công bài "+downloadSong.getSong_name(),Toast.LENGTH_LONG).show();
                        ViewFragmentPresenter.saveDownloadSong(downloadSong,context);
                    }
                }
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SMALLVIEW);
        IntentFilter intentFilter1=new IntentFilter();
        intentFilter1.addAction(MyApplication.SUCCESS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(uiBroadcast,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
        registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        uiPresenter.onInit();
    }

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        listFragment=new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        relativeLayout=findViewById(R.id.relative);
        frameLayout=findViewById(R.id.fr2);
        listFragment=uiPresenter.getFragments(savedInstanceState,this,R.id.frame);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        FragmentChosen fragmentChosen = new FragmentChosen(this, uiPresenter);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) fragmentChosen);
        uiPresenter.onReplicate(savedInstanceState,getSupportFragmentManager().beginTransaction());
        uiPresenter.downloadForFirstLogin(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        uiPresenter.inflateMenu(menu,getMenuInflater());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        uiPresenter.verifyPressToSearchFragment(item.getItemId(),getSupportFragmentManager().beginTransaction());
        return true;
    }
    @Override
    public void bottomNavigationView(int show,int hide1,int hide2,String title, FragmentTransaction transaction) {
        transaction.hide(listFragment.get(hide1)).
                hide(listFragment.get(hide2)).
                show(listFragment.get(show)).commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        findViewById(R.id.relative).setVisibility(View.VISIBLE);
        findViewById(R.id.fr2).setVisibility(View.GONE);
    }
    @Override
    public void showSearchFragment(FragmentTransaction fragmentTransaction,int action) {
        findViewById(R.id.relative).setVisibility(View.GONE);
        findViewById(R.id.fr2).setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(action);
        fragmentTransaction.commit();
    }

    @Override
    public void showTitleToolbar(String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(text);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onInit() {
        Window window =getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.status_bar));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.status_bar));

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thư viện");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        uiPresenter.saveState(outState,getSupportActionBar());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}