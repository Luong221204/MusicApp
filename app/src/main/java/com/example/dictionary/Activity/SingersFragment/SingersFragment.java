package com.example.dictionary.Activity.SingersFragment;

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
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingersFragment extends Fragment implements SingersFragmentInterface {
    SingersFragmentPresenter singersFragmentPresenter=new SingersFragmentPresenter(this);
    RecyclerView recyclerView;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ACTION.equals(intent.getAction())){
                singersFragmentPresenter.onInit(getContext(),getArguments());
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        singersFragmentPresenter.onInit(getContext(),getArguments());
        return view;
    }

    @Override
    public void onInit(ContentAdapter contentAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(broadcastReceiver);

    }
}