package com.example.dictionary.Activity.OutstandingFragment;

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

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

public class OutStandingFragment extends Fragment implements ItemClickListener, OutStandFragmentInterface {
    public RecyclerView recyclerView;
    public OutstandingPresenter outstandingPresenter=new OutstandingPresenter(this);
    Song song1;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ACTION.equals(intent.getAction())){
                outstandingPresenter.showOutStand(getContext(),getItemClick());
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        outstandingPresenter=new OutstandingPresenter(this);
        View view=inflater.inflate(R.layout.fragment_out_standing, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        outstandingPresenter.showOutStand(getContext(),this);
        return view;
    }

    @Override
    public void onClickListener(Song song) {
        this.outstandingPresenter.showBottomSheet(song);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(requireActivity().getSupportFragmentManager(), MyApplication.SONG);
    }
    @Override
    public void onBackFinish() {

    }

    @Override
    public void onRecycle(ContentAdapter albumAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStop() {

        super.onStop();

        requireActivity().unregisterReceiver(broadcastReceiver);
    }
    private ItemClickListener getItemClick(){
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        outstandingPresenter.onReset(getContext(),this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}