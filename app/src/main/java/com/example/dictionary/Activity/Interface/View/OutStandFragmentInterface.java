package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;

import java.util.ArrayList;

public interface OutStandFragmentInterface {
    public void showBottomSheet(BottomFragment bottomFragment);
    public void onBackFinish();
    void onRecycle(ArrayList<Behalf> arrayList);
}
