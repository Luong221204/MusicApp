package com.example.dictionary.Activity.View.NaviFragment.PagerFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.AlbumAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.PlaylistFragmentInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Presenter.ViewPresenter.PlaylistPresenter;
import com.example.dictionary.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayListFragment extends Fragment implements PlaylistFragmentInterface {
    RecyclerView recyclerView;
    PlaylistPresenter playlistPresenter=new PlaylistPresenter(this);
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ACTION.equals(intent.getAction())){
                ArrayList<Album> albums=new ArrayList<>();
                String data=intent.getStringExtra(MyApplication.DATA);
                try {
                    JSONArray jsonArray=new JSONArray(data);
                    JSONObject jsonObject;
                    Gson gson=new Gson();
                    Album album;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        album=gson.fromJson(jsonObject.toString(),Album.class);
                        albums.add(album);
                    }
                    playlistPresenter.onInit(albums);
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
        intentFilter.addAction(MyApplication.ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_play, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        playlistPresenter.onInit(MyApplication.albums);

        return view;
    }

    @Override
    public void onInit(ArrayList<Album> albums) {
        AlbumAdapter albumAdapter=new AlbumAdapter(requireContext(),albums,0);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(requireContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albumAdapter);
    }
    @Override
    public void onStop() {
        super.onStop();

        requireActivity().unregisterReceiver(broadcastReceiver);
    }
}