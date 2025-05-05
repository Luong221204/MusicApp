package com.example.dictionary.Activity.View.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.AlbumAdapter;
import com.example.dictionary.Activity.Interface.View.AllAlbumsInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Presenter.ViewPresenter.AllAlbumsPresenter;
import com.example.dictionary.R;

import java.util.ArrayList;

public class AllAlbumsActivity extends AppCompatActivity implements AllAlbumsInterface {
    RecyclerView recyclerView;
    ImageView back;
    AllAlbumsPresenter allAlbumsPresenter=new AllAlbumsPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_albums);
        recyclerView=findViewById(R.id.recycle);
        back=findViewById(R.id.back);
        back.setOnClickListener(v->{finish();});
        allAlbumsPresenter.onInit();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public void onInit(ArrayList<Album> albums) {
        AlbumAdapter albumAdapter=new AlbumAdapter(this,albums,0);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumAdapter);
    }
}