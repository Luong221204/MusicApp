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

import com.example.dictionary.Activity.Adapter.ContentAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.SingersFragmentInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Presenter.ViewPresenter.SingersFragmentPresenter;
import com.example.dictionary.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                singersFragmentPresenter.onInit();
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
        singersFragmentPresenter.onInit();
        return view;
    }

    @Override
    public void onInit(ArrayList<Behalf> artists) {
        ContentAdapter contentAdapter=new ContentAdapter(getActivity(),artists,null);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}