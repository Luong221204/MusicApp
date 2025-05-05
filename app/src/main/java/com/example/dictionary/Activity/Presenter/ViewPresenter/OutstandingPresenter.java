package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.os.Bundle;

import com.example.dictionary.Activity.Interface.View.OutStandFragmentInterface;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

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
    public void showOutStand(ArrayList<Behalf> behalves){
        outStandInterface.onRecycle(behalves);
    }
    public void showHistory(){}
}
