package com.example.dictionary.Activity.LibActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.Activity.SortFragment.Filter2Fragment;
import com.example.dictionary.Activity.FilterFragment.FilterBottomFragment;
import com.example.dictionary.R;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LibActivity extends AppCompatActivity implements LibActivityInterface, ItemClickListener {
    Toolbar toolbar;
    ImageView filterSort,filter;
    TextView quantity;
    RecyclerView recyclerView;
    LibActivityPresenter libActivityPresenter=new LibActivityPresenter(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Objects.equals(intent.getAction(), MyApplication.SORT)){
                try {
                    libActivityPresenter.onSort(getIntent(),intent,LibActivity.this);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SORT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lib);
        toolbar=findViewById(R.id.toolbar);
        quantity=findViewById(R.id.quantity);
        recyclerView=findViewById(R.id.recycle);
        filterSort=findViewById(R.id.filter2);
        filter=findViewById(R.id.filter);
        setSupportActionBar(toolbar);
        filterSort.setOnClickListener(v->{libActivityPresenter.onFilterSort(getIntent());});
        filter.setOnClickListener(v->{libActivityPresenter.onFilter(getIntent());});
        libActivityPresenter.onInit(getIntent(),this,this);
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
    public void onRecycleViewDown( DatabaseAdapter databaseAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(databaseAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onRecycleViewLove(RecycleAdapter recycleAdapter, LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onInit(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showFilterSort(Filter2Fragment fragment) {
        fragment.show(getSupportFragmentManager(),MyApplication.FRAGMENTCHOSEN);
    }

    @Override
    public void showFilter(FilterBottomFragment fragment) {
        fragment.show(getSupportFragmentManager(),MyApplication.FRAGMENTCHOSEN);
    }

    @Override
    public void onClickListener(com.example.dictionary.Activity.Model.Song song) {
        libActivityPresenter.showBottomSheet(song);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}