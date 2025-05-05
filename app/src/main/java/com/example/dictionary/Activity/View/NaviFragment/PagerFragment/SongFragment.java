package com.example.dictionary.Activity.View.NaviFragment.PagerFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Interface.View.SongFragmentInterface;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.SongFragmentPresenter;
import com.example.dictionary.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongFragment extends Fragment implements SongFragmentInterface, ItemClickListener {
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    SongFragmentPresenter songFragmentPresenter=new SongFragmentPresenter(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.SEARCH.equals(intent.getAction())){
                try {
                    songFragmentPresenter.onInit(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SEARCH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        songFragmentPresenter.onRecycle();
        return view;
    }

    @Override
    public void onRecycleView(ArrayList<Song> songs) {
        recycleAdapter=new RecycleAdapter(songs,requireActivity(),0,null,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onRecycleViewHistory(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs) {
        DatabaseAdapter databaseAdapter =new DatabaseAdapter(this,requireActivity(),songs,0,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(databaseAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClickListener(Song song) {
        songFragmentPresenter.showBottomSheet(song);
    }
}