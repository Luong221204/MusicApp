package com.example.dictionary.Activity.View.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Broadcast.UIBroadcast;
import com.example.dictionary.Activity.Interface.View.BottomView;
import com.example.dictionary.Activity.Interface.View.MainActivityInterface;
import com.example.dictionary.Activity.Listener.ClickViewMainListener;
import com.example.dictionary.Activity.Listener.FragmentChosen;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Presenter.ViewPresenter.UIPresenter;
import com.example.dictionary.Activity.Presenter.ViewPresenter.PlayMusicPresenter;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.ViewFragmentPresenter;
import com.example.dictionary.Activity.View.NaviFragment.AccountFragment;
import com.example.dictionary.Activity.View.NaviFragment.ExploreFragment;
import com.example.dictionary.Activity.View.NaviFragment.LibraryFragment;
import com.example.dictionary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityInterface,BottomView {
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

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        setContentView(R.layout.activity_main2);
        listFragment=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();
                        if (networkInterface.isUp() && !networkInterface.isLoopback() && !networkInterface.isVirtual()) {
                            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                            while (addresses.hasMoreElements()) {
                                InetAddress address = addresses.nextElement();
                                if (address.getAddress().length == 4) { // Chỉ lấy IPv4
                                    Log.d("DUCLUONG",address.getHostAddress()+"");
                                    return; // Dừng sau khi tìm thấy địa chỉ IPv4
                                }
                            }
                        }
                    }
                    System.out.println("Không tìm thấy địa chỉ IP WiFi.");
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        toolbar = findViewById(R.id.toolbar);
        relativeLayout=findViewById(R.id.relative);
        frameLayout=findViewById(R.id.fr2);
        uiPresenter.restoreSearchFragment(savedInstanceState,this,R.id.fr2);
        listFragment=uiPresenter.getFragments(savedInstanceState,this,R.id.frame);
        PlayMusicPresenter playMusicModel = new PlayMusicPresenter();
        ClickViewMainListener clickViewListener = new ClickViewMainListener(this, playMusicModel);
       /* imageView=findViewById(R.id.image);
        next=findViewById(R.id.next);
        next.setOnClickListener(clickViewListener );
        back=findViewById(R.id.previous);
        back.setOnClickListener(clickViewListener );
        name=findViewById(R.id.song);
        singers=findViewById(R.id.singers);
        status=findViewById(R.id.status);
        status.setOnClickListener(clickViewListener );*/

        bottomNavigationView = findViewById(R.id.bottom_nav);
        FragmentChosen fragmentChosen = new FragmentChosen(this, uiPresenter);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) fragmentChosen);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thư viện");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        uiPresenter.showTitleToolbar(savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.search){
            uiPresenter.showSearchFragment(this,R.id.fr2);
        }
        return true;
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        assert bundle != null;
        int action=bundle.getInt(MyApplication.ACTION);
        Toast.makeText(this,"hihi"+action,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void smallSongView(Song song, int action) {
       // findViewById(R.id.song_view).setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.eli);
        name.setText(song.getName());
        int resource=action==MyApplication.PLAY?R.drawable.baseline_pause_24:R.drawable.baseline_play_arrow_24;
        status.setImageResource(resource);
    }

    @Override
    public void bottomNavigationView(MenuItem item, FragmentTransaction transaction) {
        if(item.getItemId()==R.id.libra){
            transaction.hide((ExploreFragment)listFragment.get(1)).
                    hide((AccountFragment)listFragment.get(2)).
                    show(listFragment.get(0)).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Thư viện");
        }else if(item.getItemId()==R.id.home){
            transaction.hide((LibraryFragment)listFragment.get(0)).
                    hide((AccountFragment)listFragment.get(2)).
                    show(listFragment.get(1)).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Khám phá");
        }else if(item.getItemId()==R.id.account){
            transaction.hide(listFragment.get(0)).
                    hide(listFragment.get(1)).
                    show(listFragment.get(2)).commit();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Cá nhân");
        }
        findViewById(R.id.relative).setVisibility(View.VISIBLE);
        findViewById(R.id.fr2).setVisibility(View.GONE);
    }
    @Override
    public void showSearchFragment(FragmentTransaction fragmentTransaction) {
        if(isShowed){
            findViewById(R.id.relative).setVisibility(View.GONE);
            findViewById(R.id.fr2).setVisibility(View.VISIBLE);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void showTitleToolbar(String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(text);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }




    @Override
    public void setVisible(Boolean isVisible) {
        if(isVisible) findViewById(R.id.bottom_nav).setVisibility(View.GONE);
        else findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MyApplication.ISSHOWED,isShowed);
        outState.putString(MyApplication.FRAGMENTCHOSEN, (String) Objects.requireNonNull(getSupportActionBar()).getTitle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}