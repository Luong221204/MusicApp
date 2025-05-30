package com.example.dictionary.Activity.FavouriteArtistActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.R;

import java.util.Objects;

public class FavouriteArtistActivity extends AppCompatActivity implements FavouriteArtistActivityInterface {
    Toolbar toolbar;
    RecyclerView recyclerView,suggest;
    TextView textView;
    FavouriteArtistPresenter favouriteArtistPresenter=new FavouriteArtistPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourited_artist);
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycle);
        suggest=findViewById(R.id.recycle1);
        textView=findViewById(R.id.text);
        favouriteArtistPresenter.onInit(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra(MyApplication.ACTION));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    public void onInit(ContentAdapter contentAdapter, LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onSuggest(ContentAdapter contentAdapter, LinearLayoutManager layoutManager) {
        suggest.setAdapter(contentAdapter);
        suggest.setLayoutManager(layoutManager);
    }

    @Override
    public void hideSuggest() {
        textView.setVisibility(View.GONE);
    }
}