package com.example.dictionary.Activity.View.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Interface.View.LibActivityInterface;
import com.example.dictionary.Activity.Presenter.ViewPresenter.LibActivityPresenter;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Objects;

public class LibActivity extends AppCompatActivity implements LibActivityInterface, ItemClickListener {
    Toolbar toolbar;
    TextView quantity;
    RecyclerView recyclerView;
    LibActivityPresenter libActivityPresenter=new LibActivityPresenter(this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lib);
        toolbar=findViewById(R.id.toolbar);
        quantity=findViewById(R.id.quantity);
        recyclerView=findViewById(R.id.recycle);
        setSupportActionBar(toolbar);
        String title=getIntent().getStringExtra(MyApplication.ACTION);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        assert title != null;
        libActivityPresenter.onInit(title,this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)  finish();
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onQuantity(int quantities) {
        quantity.setText(quantities+" * Bài hát");

    }

    @Override
    public void onRecycleView(ArrayList<Song> songs) {
        DatabaseAdapter databaseAdapter=new DatabaseAdapter(this,this,songs,0,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(databaseAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onClickListener(com.example.dictionary.Activity.Model.Song song) {
        libActivityPresenter.showBottomSheet(song);
    }
}