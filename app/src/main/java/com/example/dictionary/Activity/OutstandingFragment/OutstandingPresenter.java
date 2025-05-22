package com.example.dictionary.Activity.OutstandingFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public class OutstandingPresenter {
    OutStandFragmentInterface outStandInterface;
    public OutstandingPresenter(OutStandFragmentInterface outStandInterface){
        this.outStandInterface=outStandInterface;
    }

    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        this.outStandInterface.showBottomSheet(bottomFragment);
    }
    public void showOutStand(Context context, ItemClickListener itemClickListener){
        ContentAdapter albumAdapter=new ContentAdapter(context, MyApplication.behalves,itemClickListener);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        outStandInterface.onRecycle(albumAdapter,layoutManager);
    }
    public void onReset(Context context, ItemClickListener itemClickListener){
        for(Behalf behalf:MyApplication.behalves){
            if(behalf instanceof Artist){
                behalf.setType(2);
                showOutStand(context,itemClickListener);
                return;
            }
        }
    }
}
