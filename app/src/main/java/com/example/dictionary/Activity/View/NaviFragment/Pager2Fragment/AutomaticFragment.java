package com.example.dictionary.Activity.View.NaviFragment.Pager2Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.AutoFragmentInterface;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.AutomaticPresenter;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutomaticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutomaticFragment extends Fragment implements AutoFragmentInterface, ItemClickListener {
    RecyclerView recyclerView,recyclerView1;
    NestedScrollView nestedScrollView;
    TextView ds,td,text;
    AutomaticPresenter automaticPresenter=new AutomaticPresenter(this);;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.AGAIN.equals(intent.getAction())){
                automaticPresenter.onAutoList();
            }else if(MyApplication.AGAIN2.equals(intent.getAction())){
                automaticPresenter.onAutoList();
            }else if(MyApplication.AGAIN3.equals(intent.getAction())){
                automaticPresenter.onAutoListOff();
            }else if(MyApplication.AGAIN4.equals(intent.getAction())){
                automaticPresenter.onAutoListOff();

            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();

        intentFilter.addAction(MyApplication.AGAIN);
        intentFilter.addAction(MyApplication.AGAIN2);
        intentFilter.addAction(MyApplication.AGAIN3);
        intentFilter.addAction(MyApplication.AGAIN4);

        requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_automatic, container, false);

        automaticPresenter.onPlaylist(this.getViewLifecycleOwner());
        nestedScrollView=view.findViewById(R.id.nested);
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView1=view.findViewById(R.id.recycle1);
        td=view.findViewById(R.id.td);
        ds=view.findViewById(R.id.ds);
        text=view.findViewById(R.id.text);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
               automaticPresenter.onScroll(nestedScrollView,td);
            }
        });
        automaticPresenter.onInit();

        return view;
    }

    @Override
    public void onInit(String theme) {
        text.setText(theme);
    }

    @Override
    public void onPlaylist(ArrayList<Song> songs) {
        RecycleAdapter resultSearchAdapter=new RecycleAdapter(songs, (Context) requireActivity(),2, (ViewSongActivity) requireActivity(),null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultSearchAdapter);
    }

    @Override
    public void onAutoPlay(ArrayList<Song> songs) {
        RecycleAdapter resultSearchAdapter=new RecycleAdapter(songs, (Context) requireActivity(),1, (ViewSongActivity) requireActivity(),this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(resultSearchAdapter);
    }

    @Override
    public void onAutoPlayOff(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs) {
        DatabaseAdapter resultSearchAdapter=new DatabaseAdapter(this,(Context) requireActivity(),songs,1, (ViewSongActivity) requireActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(resultSearchAdapter);
    }

    @Override
    public void onPlaylistOff(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs) {
        DatabaseAdapter resultSearchAdapter=new DatabaseAdapter(this,(Context) requireActivity(),songs,2, (ViewSongActivity) requireActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultSearchAdapter);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClickListener(Song song) {
        automaticPresenter.showBottomSheet(song);
    }
}