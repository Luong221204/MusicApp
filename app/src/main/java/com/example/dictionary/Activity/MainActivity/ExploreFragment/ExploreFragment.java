package com.example.dictionary.Activity.MainActivity.ExploreFragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.Adapter.FormAdapter.RecentAdapter;
import com.example.dictionary.Activity.TypeAdapter.TypeAdapter;
import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements ItemClickListener, ExploreFragmentInterface {
RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3,recyclerView4;
TextView name;
ExploreFragmentPresenter exploreFragmentPresenter=new ExploreFragmentPresenter(this);
CircleImageView image;
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(MyApplication.SUCCESS.equals(intent.getAction())){
            Bundle bundle = intent.getBundleExtra(MyApplication.DATA);
            if(bundle != null){
                String uri=bundle.getString(MyApplication.URI);
                long downloadId=bundle.getLong(MyApplication.ID,-1);
                DownloadSong downloadSong= ViewFragmentPresenter.checkOver(downloadId,uri);
                if( downloadSong!= null){
                    Toast.makeText(requireActivity(),"Đã tải thành công bài "+downloadSong.getSong_name(),Toast.LENGTH_LONG).show();
                    ViewFragmentPresenter.saveDownloadSong(downloadSong,context);
                }
            }
        }
    }
};

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.SUCCESS);
        requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home2, container, false);
        recyclerView2=view.findViewById(R.id.recycle2);
        name=view.findViewById(R.id.name);
        image=view.findViewById(R.id.image);
        recyclerView4=view.findViewById(R.id.recycle4);
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView1=view.findViewById(R.id.recycle1);
        recyclerView3=view.findViewById(R.id.recycle3);
        exploreFragmentPresenter.showOnHintRecycle(getContext(),this);
        exploreFragmentPresenter.showOnIdol();
        exploreFragmentPresenter.showOnIdolAlbum(getContext());
        exploreFragmentPresenter.showOnThemes(getContext());
        exploreFragmentPresenter.showOnHistory(getContext());
        exploreFragmentPresenter.showOnAlbumHot(getContext());
        return view;
    }

    @Override
    public void onClickListener(Song song) {
        exploreFragmentPresenter.showBottomSheet(song);
    }


    @Override
    public void showOnIdol(String images, String singer_name) {
        Glide.with(requireActivity()).load(images).into(image);
        name.setText(singer_name.toUpperCase());
    }

    @Override
    public void showOnHintRecycle(LinearLayoutManager linearLayoutManager,RecentAdapter adapter) {

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void showOnIdolAlbumRecycle( AlbumAdapter albumAdapter,LinearLayoutManager layoutManager) {

        recyclerView2.setAdapter(albumAdapter);
        recyclerView2.setLayoutManager(layoutManager);
    }

    @Override
    public void showOnThemeRecycle(TypeAdapter typeAdapter,LinearLayoutManager layoutManager2) {

        recyclerView3.setLayoutManager(layoutManager2);
        recyclerView3.setAdapter(typeAdapter);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void showOnHistoryRecycle(LaterAdapter laterAdapter,LinearLayoutManager layoutManager1) {
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(laterAdapter);

    }

    @Override
    public void showOnHotAlbum(AlbumAdapter albumAdapter,LinearLayoutManager layoutManager) {
        recyclerView4.setAdapter(albumAdapter);
        recyclerView4.setLayoutManager(layoutManager);
    }
    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }
}