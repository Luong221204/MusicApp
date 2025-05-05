package com.example.dictionary.Activity.Service;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Broadcast.MyReciever;
import com.example.dictionary.Activity.View.Activity.MainActivity;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    private Song song;
    MediaSessionCompat mediaSessionCompat;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int action =intent.getIntExtra(MyApplication.ACTION,1000);
        handleAction(action);
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if(bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                song=bundle.getSerializable(MyApplication.SONG,Song.class);
                if(song != null){
                    MyApplication.song=song;
                    if(MyApplication.mediaPlayer != null) MyApplication.mediaPlayer.release();
                    MyApplication.mediaPlayer=MediaPlayer.create(getApplicationContext(), Uri.parse(song.getUrl()));
                    MyApplication.mediaPlayer.start();
                    MyApplication.isPlaying=true;
                    MyApplication.mediaPlayer.setOnCompletionListener(mp -> {
                        Intent intent2=new Intent(getApplicationContext(), MyReciever.class);
                        Bundle bundle2=new Bundle();
                        bundle2.putInt(MyApplication.ACTION,MyApplication.NEXT);
                        intent2.putExtra(MyApplication.ACTION,bundle2);
                        getApplicationContext().sendBroadcast(intent2);
                    });
                    sendNotification();
                }
            }

        }
        return START_NOT_STICKY;
    }
    private void handleAction(int action){
        if(action == MyApplication.PAUSE){
            if(MyApplication.mediaPlayer != null&& MyApplication.mediaPlayer.isPlaying()){
                MyApplication.mediaPlayer.pause();
                MyApplication.isPlaying=false;
                sendNotification();

            }
        }else if(action == MyApplication.PLAY){
            if(MyApplication.mediaPlayer != null){
                MyApplication.mediaPlayer.start();
                MyApplication.isPlaying=true;
                sendNotification();

            }
        }
    }
    @SuppressLint("ForegroundServiceType")
    public void sendNotification(){
        mediaSessionCompat=new MediaSessionCompat(this,"tag");
        NotificationCompat.Builder notification=new NotificationCompat.Builder(getApplicationContext(),
                MyApplication.CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(sendToActivity())
                .setContentText(song.getArtist_name())
                .setContentTitle(song.getName())
                .setCustomBigContentView(getRemoteViews())
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()).setShowCancelButton(true));
                ;
        if (MyApplication.isPlaying){
             notification.addAction(R.drawable.baseline_skip_previous_24,"back",pendingIntentBack())
                    .addAction(R.drawable.baseline_pause_24,"back",pendingIntentPause())
                     .addAction(R.drawable.baseline_skip_next_24,"next",pendingIntentNext());
        }else{
            notification.addAction(R.drawable.baseline_skip_previous_24,"back",pendingIntentBack())
                    .addAction(R.drawable.baseline_play_arrow_24,"back",pendingIntentPlay())
                    .addAction(R.drawable.baseline_skip_next_24,"next",pendingIntentNext());
            ;
        }
        startForeground(1,notification.build());

    }
    private PendingIntent sendToActivity(){
        Intent intentActivity=new Intent(this, MainActivity.class);
        intentActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int action=MyApplication.isPlaying?MyApplication.PLAY:MyApplication.PAUSE;
        Bundle bundle=new Bundle();
        bundle.putInt(MyApplication.ACTION,action);
        bundle.putSerializable(MyApplication.SONG,song);
        intentActivity.putExtra(MyApplication.BUNDLE,bundle);
        return PendingIntent.getActivity(getApplicationContext()
                ,0
                ,intentActivity
                ,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
    private PendingIntent pendingIntentBack(){
        Intent back=new Intent(this, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bundle.putInt(MyApplication.ACTION,MyApplication.BACK);
        back.putExtra(MyApplication.ACTION,bundle);
        return PendingIntent.getBroadcast(getApplicationContext(),11000,back,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
    private PendingIntent pendingIntentNext(){
        Intent next=new Intent(this, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bundle.putInt(MyApplication.ACTION,MyApplication.NEXT);
        next.putExtra(MyApplication.ACTION,bundle);
        return PendingIntent.getBroadcast(getApplicationContext(),20,next,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
    private PendingIntent pendingIntentPause(){
        Intent pause=new Intent(this, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putInt(MyApplication.ACTION,MyApplication.PAUSE);
        bundle.putSerializable(MyApplication.SONG,song);
        pause.putExtra(MyApplication.ACTION,bundle);
        return PendingIntent.getBroadcast(getApplicationContext(),1000,pause,
                PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE
        );
    }
    private PendingIntent pendingIntentPlay(){
        Intent play=new Intent(this, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putInt(MyApplication.ACTION,MyApplication.PLAY);
        bundle.putSerializable(MyApplication.SONG,song);
        play.putExtra(MyApplication.ACTION,bundle);
        return PendingIntent.getBroadcast(getApplicationContext(),10,play,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
    private RemoteViews getRemoteViews(){
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.song);
        remoteViews.setTextViewText(R.id.song,song.getName());
        remoteViews.setImageViewResource(R.id.next,R.drawable.baseline_skip_next_24);
        remoteViews.setImageViewResource(R.id.previous,R.drawable.baseline_skip_previous_24);
        if(MyApplication.isPlaying) {
            remoteViews.setImageViewResource(R.id.status, R.drawable.baseline_pause_24);
            remoteViews.setOnClickPendingIntent(R.id.status,pendingIntentPause());
        }
        else{
            remoteViews.setImageViewResource(R.id.status, R.drawable.baseline_play_arrow_24);
            remoteViews.setOnClickPendingIntent(R.id.status,pendingIntentPlay());
        }
        remoteViews.setOnClickPendingIntent(R.id.previous,pendingIntentBack());
        remoteViews.setOnClickPendingIntent(R.id.next,pendingIntentNext());
        return remoteViews;
    }
}
