package com.example.dictionary.Activity.Listener;

import android.view.View;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Presenter.ViewPresenter.PlayMusicPresenter;
import com.example.dictionary.Activity.View.Activity.MainActivity;

public class ClickViewMainListener implements View.OnClickListener{
    private final PlayMusicPresenter playMusicPresenter;
    private final MainActivity context;
    public ClickViewMainListener(MainActivity context, PlayMusicPresenter playMusicPresenter){
        this.context=context;
        this.playMusicPresenter=playMusicPresenter;
    }
    @Override
    public void onClick(View v) {
        if(v==context.status){
            if(MyApplication.isPlaying){
                this.playMusicPresenter.sendBroadcastOption(this.context,MyApplication.PAUSE);
            }else{
                this.playMusicPresenter.sendBroadcastOption(this.context,MyApplication.PLAY);
            }
        }else if(v==context.next){
            this.playMusicPresenter.sendBroadcastOption(this.context,MyApplication.NEXT);
        }else if(v==context.back){
            this.playMusicPresenter.sendBroadcastOption(this.context,MyApplication.BACK);
        }

    }
}
