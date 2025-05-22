package com.example.dictionary.Activity.VIewSongActivity.ViewFragment;

import android.transition.Scene;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dictionary.Activity.BottomFragment.BottomFragment;

public interface ViewFragmentInterface {
    void onSeekBar(int progress);
    void onTimeline(String time);
    void playOrPause(int source);
    void showStatus(boolean isLiked);
    void showRotation(boolean isPaused);
    void onDuration(String time);
    void scenePause(int source);
    void disPlayScene(Scene scene,int visibility1,int visibility2);
    void statusPlayButton(ImageView pause,int source);
    void onSeekBar2(int progress, SeekBar seekBar, TextView time,String timeline);
    void onInit(String singer_name,String song_name,String image);
    void onDownloadComplete();
    void onPrepareDownload();
    void onOffline(String singer_name,String song_name,String image);
    void onDownloadStatus(ImageView imageView);
    void showBottomSheet(BottomFragment bottomFragment);
    void onReset(float alpha);
    void onLove(int icon);
    void onToast(String message);


}
