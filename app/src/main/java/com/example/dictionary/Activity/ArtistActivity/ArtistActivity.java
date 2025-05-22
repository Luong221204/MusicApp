package com.example.dictionary.Activity.ArtistActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.ArtistAdapter.ArtistAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistActivity extends AppCompatActivity implements ArtistActivityInterface, ItemClickListener {
    Toolbar toolbar;
    CircleImageView imageView,small;
    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4,recyclerView5,recyclerView6;
    Button follow,play;
    RelativeLayout frame,frame2,frame3,frame4,frame5;
    TextView followers;
    AppBarLayout appBarLayout;
    ArtistActivityPresenter artistActivityPresenter=new ArtistActivityPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        toolbar=findViewById(R.id.toolbar);
        imageView=findViewById(R.id.image);
        recyclerView=findViewById(R.id.recycle);
        small=findViewById(R.id.small);
        frame=findViewById(R.id.frame);
        frame2=findViewById(R.id.frame2);
        frame3=findViewById(R.id.frame3);
        frame4=findViewById(R.id.frame4);
        frame5=findViewById(R.id.frame5);
        appBarLayout=findViewById(R.id.appBarLayout);
        recyclerView6=findViewById(R.id.recycle6);
        recyclerView5=findViewById(R.id.recycle5);
        recyclerView4=findViewById(R.id.recycle4);
        recyclerView2=findViewById(R.id.recycle2);
        recyclerView3=findViewById(R.id.recycle3);
        follow=findViewById(R.id.follow);
        play=findViewById(R.id.play);
        followers=findViewById(R.id.followed);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                artistActivityPresenter.onChangeAppbar(appBarLayout,verticalOffset);
            }
        });
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        artistActivityPresenter.onInit(getIntent().getBundleExtra(MyApplication.DATA),this,this);
        artistActivityPresenter.onMemberOf(getIntent().getBundleExtra(MyApplication.DATA),this);
        artistActivityPresenter.onMembers(getIntent().getBundleExtra(MyApplication.DATA),this);
        artistActivityPresenter.onAppearInSong(getIntent().getBundleExtra(MyApplication.DATA),this,this);
        follow.setOnClickListener(v->{
            artistActivityPresenter.checkLoved(this);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onInit(String name, String image, int follower) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        followers.setText(follower+" Người quan tâm");
        Glide.with(this).load(image).into(imageView);
        Glide.with(this).load(image).into(small);

    }

    @Override
    public void onSongs(RecycleAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public void onRelateArtists( ArtistAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        frame2.setVisibility(View.VISIBLE);

        recyclerView3.setLayoutManager(layoutManager);
        recyclerView3.setAdapter(recycleAdapter);
    }

    @Override
    public void onSmallImage(int action) {
        small.setVisibility(action);
    }

    @Override
    public void onAlbums(AlbumAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        frame.setVisibility(View.VISIBLE);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(recycleAdapter);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onAppearIn( RecycleAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        frame3.setVisibility(View.VISIBLE);
        recyclerView4.setLayoutManager(layoutManager);
        recyclerView4.setAdapter(recycleAdapter);
    }

    @Override
    public void onMemberOf(ArtistAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        frame4.setVisibility(View.VISIBLE);
        recyclerView5.setLayoutManager(layoutManager);
        recyclerView5.setAdapter(recycleAdapter);
    }

    @Override
    public void onMembers( ArtistAdapter recycleAdapter,LinearLayoutManager layoutManager) {
        frame5.setVisibility(View.VISIBLE);
        recyclerView6.setLayoutManager(layoutManager);
        recyclerView6.setAdapter(recycleAdapter);
    }

    @Override
    public void onFollowButton(String text) {
        follow.setText(text);
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return true;
    }

    @Override
    public void onClickListener(Song song) {
        artistActivityPresenter.showBottomSheet(song);
    }
}