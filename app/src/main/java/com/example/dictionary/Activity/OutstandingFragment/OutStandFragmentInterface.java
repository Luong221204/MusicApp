package com.example.dictionary.Activity.OutstandingFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ContentAdapter.ContentAdapter;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface OutStandFragmentInterface {
    public void showBottomSheet(BottomFragment bottomFragment);
    public void onBackFinish();
    void onRecycle(ContentAdapter albumAdapter, LinearLayoutManager layoutManager);
}
