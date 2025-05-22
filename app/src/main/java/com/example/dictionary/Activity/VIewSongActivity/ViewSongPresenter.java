package com.example.dictionary.Activity.VIewSongActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.Adapter.NaviAdapter.SongViewAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.Finisher;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.R;

public class ViewSongPresenter {
    ViewSongActivityInterface viewSongActivityInterface;
    public ViewSongPresenter(ViewSongActivityInterface viewSongActivityInterface){
        this.viewSongActivityInterface=viewSongActivityInterface;
    }
    public void onInit(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            assert bundle != null;
            Song song=bundle.getSerializable(MyApplication.SONG, Song.class);
            assert song != null;
            viewSongActivityInterface.onSongName(song.getName());
        }

    }
    public void onCreateAc(FragmentManager fragmentManager){
        SongViewAdapter songViewAdapter=new SongViewAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        if(MyApplication.song.getName() != null){
            viewSongActivityInterface.onInit(songViewAdapter,MyApplication.song.getName());

        }
    }
    public void onTabLayout(int position){
        switch(position){
            case 0:{
                viewSongActivityInterface.onTabLayout(true, R.drawable.dot,R.drawable.dot_fade,MyApplication.song.getName());

                break;
            }
            case 1:{
                viewSongActivityInterface.onTabLayout(false,R.drawable.dot_fade,R.drawable.dot,"Thông tin");
                break;
            }
            default: viewSongActivityInterface.onTabLayout(true,R.drawable.dot,R.drawable.dot_fade,MyApplication.song.getName());

        }
    }
    public void onBackFinish(Context context, Finisher finisher, ItemClickListener itemClickListener){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.history_layout);
        Window window=dialog.getWindow();
        if(window==null) return;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams win=window.getAttributes();
        win.gravity= Gravity.CENTER;
        window.setAttributes(win);
        RecycleAdapter resultSearchAdapter=new RecycleAdapter(MyApplication.list, context,3,finisher,itemClickListener);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        viewSongActivityInterface.onFinish(dialog,resultSearchAdapter,layoutManager);
    }

}
