package com.example.dictionary.Activity.Listener;

import android.widget.SeekBar;

import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragment;

public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    ViewFragment fragmentActivity;
    public SeekBarChangeListener(ViewFragment fragmentActivity){
        this.fragmentActivity=fragmentActivity;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser)        this.fragmentActivity.viewFragmentPresenter.onSeekBarChange(progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
