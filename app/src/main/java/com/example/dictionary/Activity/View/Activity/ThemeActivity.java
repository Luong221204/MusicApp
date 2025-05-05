package com.example.dictionary.Activity.View.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Interface.View.ThemeInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.ThemePresenter;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;

public class ThemeActivity extends AppCompatActivity implements ThemeInterface, ItemClickListener {
    TextView textView;
    RecyclerView recyclerView;
    Button button;
    ImageView back;
    ThemePresenter themePresenter=new ThemePresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theme);
        textView=findViewById(R.id.name);
        recyclerView=findViewById(R.id.recycle);
        button=findViewById(R.id.play);
        back=findViewById(R.id.back);
        back.setOnClickListener(v->{finish();});
        themePresenter.onInit(getIntent());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onInit(String name, ArrayList<Song> songs) {
        textView.setText(name);
        RecycleAdapter recycleAdapter=new RecycleAdapter(songs,this,0,null,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(), MyApplication.SONG);
    }

    @Override
    public void onClickListener(com.example.dictionary.Activity.Model.Song song) {
        themePresenter.showBottomSheet(song);
    }
}