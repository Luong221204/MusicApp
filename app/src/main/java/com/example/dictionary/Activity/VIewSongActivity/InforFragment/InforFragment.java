package com.example.dictionary.Activity.VIewSongActivity.InforFragment;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.VIewSongActivity.ViewSongActivity;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InforFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InforFragment extends Fragment implements InformFragmentInterface, ItemClickListener {
    ImageView image;
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    public RecycleAdapter recycleAdapter;
    InformFragmentPresenter informFragmentPresenter=new InformFragmentPresenter(this);
    TextView song_name,singers_name,album,type,year_launched;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ISBACK.equals(intent.getAction())) informFragmentPresenter.showInfo();
            else if(MyApplication.DATA.equals(intent.getAction())){
                informFragmentPresenter.showOnRecycleView();
            }else if(MyApplication.AGAIN.equals(intent.getAction())){
                informFragmentPresenter.showOnRecycleView();
            }else if(MyApplication.AGAIN2.equals(intent.getAction())){
                informFragmentPresenter.showOnRecycleView();
            }else if(MyApplication.AGAIN3.equals(intent.getAction())){
                informFragmentPresenter.onRecycleOffline();
            }else if(MyApplication.AGAIN4.equals(intent.getAction())){
                informFragmentPresenter.onRecycleOffline();

            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.ISBACK);
        intentFilter.addAction(MyApplication.DATA);
        intentFilter.addAction(MyApplication.AGAIN);
        intentFilter.addAction(MyApplication.AGAIN2);
        intentFilter.addAction(MyApplication.APPLICATION);
        intentFilter.addAction(MyApplication.OFFLINE);
        intentFilter.addAction(MyApplication.AGAIN3);
        intentFilter.addAction(MyApplication.AGAIN4);

        requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_infor, container, false);
        image=view.findViewById(R.id.image);
        relativeLayout=view.findViewById(R.id.album);
        song_name=view.findViewById(R.id.name);
        song_name.setSelected(true);
        singers_name=view.findViewById(R.id.singers);
        album=view.findViewById(R.id.album_name);
        type=view.findViewById(R.id.type_name);
        year_launched=view.findViewById(R.id.date);
        recyclerView=view.findViewById(R.id.recycle);
        informFragmentPresenter.showInfo();
        return view;
    }


    @Override
    public void showInformSong(String song) {
        song_name.setText(song);

    }

    @Override
    public void showInformSingers(String singers) {
        singers_name.setText(singers);
    }

    @Override
    public void showInformAlbum(String album_name) {
        if(album_name == null) {
          relativeLayout.setVisibility(View.GONE);
        }
        else album.setText(album_name);

    }

    @Override
    public void showInformImage(String image_url) {
        Glide.with(requireActivity()).load(image_url).into(image);

    }

    @Override
    public void showInformYear(String date) {
        year_launched.setText(date);

    }

    @Override
    public void showInformType(String type_name) {
        type.setText(type_name);

    }

    @Override
    public void showOnRecycleView(ArrayList<Song> songs) {
        RecycleAdapter recycleAdapter=new RecycleAdapter(songs, (Context) requireActivity(),1, (ViewSongActivity) requireActivity(),this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showOnRecycleOffline(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs) {
        DatabaseAdapter recycleAdapter=new DatabaseAdapter(this,(Context) requireActivity(),songs,1, (ViewSongActivity) requireActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(layoutManager);
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
        informFragmentPresenter.showBottomSheet(song);
    }
}