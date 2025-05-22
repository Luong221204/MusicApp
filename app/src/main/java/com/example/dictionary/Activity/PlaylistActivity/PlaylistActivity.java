package com.example.dictionary.Activity.PlaylistActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.Objects;

public class PlaylistActivity extends AppCompatActivity implements PlaylistActivityInterface, ItemClickListener {
    ImageView avatar;
    Toolbar toolbar;
    PlaylistActivityPresenter playlistPresenter=new PlaylistActivityPresenter(this);
    RecyclerView recyclerView;
    ActivityResultLauncher<Intent> launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result->{
                if(result.getResultCode()== Activity.RESULT_OK){
                    playlistPresenter.getData(result.getData(),PlaylistActivity.this);
                }
            });
    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        toolbar=findViewById(R.id.toolbar);
        avatar=findViewById(R.id.image);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        avatar.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                try {
                    playlistPresenter.checkRequest(PlaylistActivity.this);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        recyclerView=findViewById(R.id.recycle);
        playlistPresenter.onInit(getIntent());
        playlistPresenter.onListSongs(getIntent(),PlaylistActivity.this,this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onImage(String source) {
        avatar.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(this).load(source).into(avatar);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onToolbar(String name) {
        toolbar.setTitle(name);

    }

    @Override
    public void onListSongs(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(), MyApplication.SONG);
    }

    @Override
    public ActivityResultLauncher<Intent> getLauncher() {
        return launcher;
    }

    @Override
    public void requestPermission(String[] permissions,int request_code) {
        ActivityCompat.requestPermissions(this,permissions,request_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        playlistPresenter.onResponsePermissions(requestCode,grantResults,permissions);
    }

    @Override
    public void onClickListener(Song song) {
        playlistPresenter.showBottomSheet(song);
    }


}