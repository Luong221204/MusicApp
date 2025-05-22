package com.example.dictionary.Activity.AlbumActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.Objects;

public class AlbumActivity extends AppCompatActivity implements AlbumActivityInterface, ItemClickListener {
    Toolbar toolbar;
    TextView artist,born;
    Button play;
    RecyclerView recyclerView;
    ImageView love,down,avatar;
    AlbumActivityPresenter albumActivityPresenter=new AlbumActivityPresenter(this);
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
                        Toast.makeText(AlbumActivity.this,"Đã tải thành công bài "+downloadSong.getSong_name(),Toast.LENGTH_LONG).show();
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
        intentFilter.addAction(MyApplication.SUCCESS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_album);
        artist = findViewById(R.id.singers);
        born = findViewById(R.id.born);
        play = findViewById(R.id.play);
        love = findViewById(R.id.like);
        down = findViewById(R.id.down);
        avatar = findViewById(R.id.image);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycle);
        albumActivityPresenter.setAlbumTitle(getIntent(),this,this);
        play.setOnClickListener(v->{albumActivityPresenter.playMusic(this);});
        love.setOnClickListener(v->{albumActivityPresenter.sendFavourite();});
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

    @SuppressLint("SetTextI18n")
    @Override
    public void setAlbumTitle(String artist_name, int year_launched,String image) {
        artist.setText(artist_name);
        born.setText("Album * "+year_launched);
        Glide.with(this).load(image).into(avatar);
    }

    @Override
    public void setLoved(int icon) {
        love.setImageResource(icon);
    }

    @Override
    public void setToolbarTitle(String name) {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
    }

    @Override
    public void setRecycleView(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(), MyApplication.SONG);
    }

    @Override
    public void onClickListener(Song song) {
        albumActivityPresenter.showBottomSheet(song);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}