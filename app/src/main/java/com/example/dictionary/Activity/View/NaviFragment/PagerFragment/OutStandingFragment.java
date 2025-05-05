package com.example.dictionary.Activity.View.NaviFragment.PagerFragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.Activity.Adapter.ContentAdapter;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Interface.View.OutStandFragmentInterface;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Presenter.ViewPresenter.OutstandingPresenter;
import com.example.dictionary.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class OutStandingFragment extends Fragment implements ItemClickListener, OutStandFragmentInterface {
    public RecyclerView recyclerView;
    public OutstandingPresenter outstandingPresenter=new OutstandingPresenter(this);
    Song song1;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ACTION.equals(intent.getAction())){
                outstandingPresenter.showOutStand(MyApplication.behalves);
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
    public void onRecycle(ArrayList<Behalf> arrayList) {

        ContentAdapter albumAdapter=new ContentAdapter(requireActivity(), arrayList,this);
        recyclerView.setAdapter(albumAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStop() {
        super.onStop();

        requireActivity().unregisterReceiver(broadcastReceiver);
    }
}