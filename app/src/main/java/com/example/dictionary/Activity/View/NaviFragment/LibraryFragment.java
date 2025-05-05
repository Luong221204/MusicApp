package com.example.dictionary.Activity.View.NaviFragment;

import android.annotation.SuppressLint;
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

import com.example.dictionary.Activity.Adapter.BottomSheet.BottomsAdapter;
import com.example.dictionary.Activity.Adapter.AboutSongAdapter.LaterAdapter;
import com.example.dictionary.Activity.Adapter.FormAdapter.LibFavouriteAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.LibFragmentInterface;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Lib;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Presenter.ViewPresenter.LibraryPresenter;
import com.example.dictionary.Activity.View.Activity.MainActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment implements LibFragmentInterface {
    MainActivity mainActivity;
    RecyclerView recyclerView,recyclerView2,recyclerView1;
    LibraryPresenter libraryPresenter=new LibraryPresenter(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.SUCCESSFULL.equals(intent.getAction())){
                MyApplication.SongDownloaded.addMore(1);
                libraryPresenter.onFavouritePart();
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SUCCESSFULL);
        requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_new, container, false);
        mainActivity= (MainActivity) requireActivity();
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView2=view.findViewById(R.id.recycle2);
        recyclerView1=view.findViewById(R.id.recycle1);
        libraryPresenter.onFavouritePart();
        libraryPresenter.onHistory();
        libraryPresenter.onPlaylist();
        return view;
    }


    @Override
    public void onFavouritePart(ArrayList<Lib> libs) {
        LibFavouriteAdapter libFavouriteAdapter=new LibFavouriteAdapter((MainActivity) requireActivity(),libs);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(libFavouriteAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPlaylist(ArrayList<Playlist> playlists) {
        BottomsAdapter playlistAdapter=new BottomsAdapter(requireActivity(),R.layout.add_playlist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireActivity());
        recyclerView2.setAdapter(playlistAdapter);
        recyclerView2.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onHistory(ArrayList<Later> histories) {
        LaterAdapter laterAdapter=new LaterAdapter(requireActivity(),histories);
        recyclerView1.setAdapter(laterAdapter);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager1);
    }
}