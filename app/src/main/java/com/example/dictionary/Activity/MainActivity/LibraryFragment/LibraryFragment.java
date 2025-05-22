package com.example.dictionary.Activity.MainActivity.LibraryFragment;

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

import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;
import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.LibFavouriteAdapter.LibFavouriteAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.example.dictionary.R;

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
                libraryPresenter.onFavouritePart(getContext());
            }else if(MyApplication.RECENTLY.equals(intent.getAction())){
                libraryPresenter.onHistory(requireContext());
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();

        libraryPresenter.reset(getContext());
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SUCCESSFULL);
        intentFilter.addAction(MyApplication.RECENTLY);
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
        libraryPresenter.onFavouritePart(getContext());
        libraryPresenter.onHistory(getContext());
        libraryPresenter.onPlaylist(getContext());
        return view;
    }


    @Override
    public void onFavouritePart(LibFavouriteAdapter libFavouriteAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(libFavouriteAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPlaylist(BottomsAdapter playlistAdapter,LinearLayoutManager linearLayoutManager) {
        recyclerView2.setAdapter(playlistAdapter);
        recyclerView2.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onHistory( LaterAdapter laterAdapter,LinearLayoutManager layoutManager1) {
        recyclerView1.setAdapter(laterAdapter);
        recyclerView1.setLayoutManager(layoutManager1);
    }

    @Override
    public void onStop() {
        super.onStop();
        libraryPresenter.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireContext().unregisterReceiver(broadcastReceiver);
    }
}