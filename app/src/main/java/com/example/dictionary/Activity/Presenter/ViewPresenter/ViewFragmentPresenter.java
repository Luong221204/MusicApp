package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Broadcast.MyReciever;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ViewFragmentInterface;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFragmentPresenter {
    int t = 0, temp = 0;
    ImageView list,pause;
    SeekBar seekBar;
    ArrayList<Artist> artists;
    int delay=1000;
    boolean isPlaying=false;
    StringBuffer singers_name;
    private Runnable runnable;
    private boolean isRunnableRunning = false;
    boolean isCalled=false,isTriggered=false;
    String time_text = "";
    ViewFragmentInterface viewFragmentInterface;
    Handler handler;
    public ViewFragmentPresenter(ViewFragmentInterface viewFragmentInterface) {
        this.viewFragmentInterface = viewFragmentInterface;

    }

    public void onSeekBar(Message msg) {
        MyApplication.isPlaying= (boolean) msg.obj;
        int source = MyApplication.isPlaying ? R.drawable.baseline_pause_24 : R.drawable.baseline_play_arrow_24;//isPaused
        viewFragmentInterface.playOrPause(source);
        viewFragmentInterface.showRotation(MyApplication.isPlaying);//isPaused
        if (MyApplication.mediaPlayer == null) return;

        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (!MyApplication.isPlaying) {
                        isRunnableRunning = false;
                        return;
                    }

                    int duration = MyApplication.mediaPlayer.getDuration();
                    double rate = (double) 100 / duration;

                    int minutes = t / 60;
                    int seconds = t % 60;
                    time_text = minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
                    viewFragmentInterface.onTimeline(time_text);
                    double progress = MyApplication.mediaPlayer.getCurrentPosition() * rate;
                    temp= (int) progress;
                    viewFragmentInterface.onSeekBar((int) progress);

                    t++;
                    handler.postDelayed(this, delay);
                }
            };
        }

        if (MyApplication.isPlaying && !isRunnableRunning) {
            isRunnableRunning = true;
            handler.post(runnable);
        }

    }

    public void onClickPauseOrPlay(ViewSongActivity viewSongActivity, Handler handler) {
        if (MyApplication.isPlaying) {
            Intent intent = new Intent(viewSongActivity, MyService.class);
            intent.putExtra(MyApplication.ACTION, MyApplication.PAUSE);
            viewSongActivity.startService(intent);
        } else {
            Intent intent = new Intent(viewSongActivity, MyService.class);
            intent.putExtra(MyApplication.ACTION, MyApplication.PLAY);
            viewSongActivity.startService(intent);
        }

        Message message = this.handler.obtainMessage();
        message.obj = !MyApplication.isPlaying;
        this.handler.sendMessage(message);
    }

    public void onCompleteMedia() {
        MyApplication.mediaPlayer.start();
        t = 0;
        Message msg = this.handler.obtainMessage();
        msg.obj = true;
        this.handler.sendMessage(msg);
    }

    public void init(Looper looper) {
        if(handler == null){
            handler=new Handler(looper){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    onSeekBar(msg);
                }
            };
        }
        Message msg = this.handler.obtainMessage();
        msg.obj = true;
        this.handler.sendMessage(msg);
        int source = !MyApplication.isPlaying ? R.drawable.baseline_play_arrow_24 : R.drawable.baseline_pause_24;
        viewFragmentInterface.playOrPause(source);
        viewFragmentInterface.showRotation(!MyApplication.isPlaying);//isPaused
        int minutes = MyApplication.mediaPlayer.getDuration() / 60000;
        int seconds = (MyApplication.mediaPlayer.getDuration() % 60000) / 1000;
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", minutes, seconds);
        Log.d("DUCLUONG","kaka"+MyApplication.song.getArtistId());

        if(MyApplication.song.getArtistId()==-1){
            viewFragmentInterface.onOffline(MyApplication.song.getArtist_name(),MyApplication.song.getName(),MyApplication.song.getImage());
        }
        else{
            ApiService.apiService.getArtistNameForIndicatedSong(MyApplication.song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                    singers_name=new StringBuffer();
                    artists=response.body();
                    assert artists != null;
                    for(Artist artist:artists){
                        singers_name.append(artist.getName()).append(" , ");
                    }
                    singers_name.delete(singers_name.lastIndexOf(","),singers_name.lastIndexOf(" "));
                    viewFragmentInterface.onInit(String.valueOf(singers_name),MyApplication.song.getName(),MyApplication.song.getImage());
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Artist>> call, @NonNull Throwable t) {

                }
            });
        }
        viewFragmentInterface.onDuration(formattedTime);
    }
    public void setDuration(){
        int minutes = MyApplication.mediaPlayer.getDuration() / 60000;
        int seconds = (MyApplication.mediaPlayer.getDuration() % 60000) / 1000;
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", minutes, seconds);
        viewFragmentInterface.onDuration(formattedTime);
    }
    public void receiveFromBroadcast(Intent intent) {
        Bundle bundle = intent.getBundleExtra(MyApplication.BUNDLE);
        if (bundle != null) {
            int action = bundle.getInt(MyApplication.ACTION);
            if (action == MyApplication.PLAY){
                viewFragmentInterface.playOrPause(R.drawable.baseline_pause_24);
                Message message=handler.obtainMessage();
                message.obj=true;
                this.handler.sendMessage(message);
            }
            else if(action== MyApplication.PAUSE){
                viewFragmentInterface.playOrPause(R.drawable.baseline_play_arrow_24);
                Message message=handler.obtainMessage();
                message.obj=false;
                this.handler.sendMessage(message);
            }
            //viewFragmentInterface.showRotation(MyApplication.isPlaying);

        }
    }
    public Runnable runnable(ImageView imageView){
        double times= (double) MyApplication.mediaPlayer.getDuration() /10;
        int rotation= (int) (360*times);
        return new Runnable() {
            @Override
            public void run() {
                imageView.animate().rotation(rotation).withEndAction(this).setDuration(MyApplication.mediaPlayer.getDuration()* 10000L).setInterpolator(new LinearInterpolator()).start();
            }
        };
    }
    public void scenePauseOrPlay(){
        if(MyApplication.isPlaying) viewFragmentInterface.scenePause(R.drawable.baseline_pause_24);
        else viewFragmentInterface.scenePause(R.drawable.baseline_play_arrow_24);
    }
    public void setStatusBar(ImageView pause){
        if(MyApplication.isPlaying) viewFragmentInterface.statusPlayButton(pause,R.drawable.baseline_pause_24);
        else  viewFragmentInterface.statusPlayButton(pause,R.drawable.baseline_play_arrow_24);
    }
    public void Scene(int index,Scene scene, FragmentTransaction fragmentTransaction){
        if(index==1)viewFragmentInterface.disPlayScene(scene,View.VISIBLE,View.GONE);
        else viewFragmentInterface.disPlayScene(scene,View.GONE,View.VISIBLE);
        if(!isCalled){
            fragmentTransaction.commit();
            isCalled=true;
        }

    }
    public void onSeekBarChange(int progress){
        double percent= (double) progress /100;
        double currentMusicSeek=percent*MyApplication.mediaPlayer.getDuration();
        t= (int) currentMusicSeek/1000;
        MyApplication.mediaPlayer.seekTo((int) currentMusicSeek);

    }
    public void onSceneChange(SeekBar seekBar, TextView time){
        int minutes = t / 60;
        int seconds = t % 60;
        time_text = minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
        viewFragmentInterface.onSeekBar2(temp,seekBar,time,time_text);

    }
    public void onNext(){
        t=0;
        Message message=Message.obtain();
        message.obj=MyApplication.isPlaying;
        onSeekBar(message);
        int source = !MyApplication.isPlaying ? R.drawable.baseline_play_arrow_24 : R.drawable.baseline_pause_24;
        viewFragmentInterface.playOrPause(source);
        viewFragmentInterface.showRotation(MyApplication.isPlaying);
        int minutes = MyApplication.mediaPlayer.getDuration() / 60000;
        int seconds = (MyApplication.mediaPlayer.getDuration() % 60000) / 1000;
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", minutes, seconds);
        if(MyApplication.song.getAlbum_id() == -1){
            viewFragmentInterface.onOffline(MyApplication.song.getArtist_name(),MyApplication.song.getName(),MyApplication.song.getImage());
        }
        else{
            ApiService.apiService.getArtistNameForIndicatedSong(MyApplication.song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                    singers_name=new StringBuffer();
                    artists=response.body();
                    assert artists != null;
                    for(Artist artist:artists){
                        singers_name.append(artist.getName()).append(" , ");
                    }
                    singers_name.delete(singers_name.lastIndexOf(","),singers_name.lastIndexOf(" "));
                    viewFragmentInterface.onInit(String.valueOf(singers_name),MyApplication.song.getName(),MyApplication.song.getImage());
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<Artist>> call, @NonNull Throwable t) {

                }
            });
        }

        viewFragmentInterface.onDuration(formattedTime);
    }
    public void sendBroadcast(Context context,int action){
        Intent intent=new Intent(context, MyReciever.class);
        Bundle bundle=new Bundle();
        bundle.putInt(MyApplication.ACTION,action);
        intent.putExtra(MyApplication.ACTION,bundle);
        context.sendBroadcast(intent);
    }
    public static void onDownload(Context context,Song song){
        StringBuffer artists_name=new StringBuffer();
        DownloadSong downloadSong=new DownloadSong();
        downloadSong.setImage_id(onDownloading(context,MyApplication.PICTURE,song.getImage()));
        downloadSong.setUri_id(onDownloading(context,MyApplication.AUDIO,song.getUrl()));
        downloadSong.setSong_name(song.getName());
        downloadSong.setSong_id(song.getId());
        downloadSong.setYear_launched(song.getDay_launched());
        ApiService.apiService.getAlbumOnSongId(song.getId()).enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if(response.body() != null&& !response.body().isEmpty()) downloadSong.setAlbum(response.body().get(0).getName());
                else  downloadSong.setAlbum("");
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

            }
        });
        ApiService.apiService.getArtistNameForIndicatedSong(song.getId()).enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                assert response.body() != null;
                for(Artist artist:response.body()){
                    artists_name.append(artist.getName()).append(" , ");
                }
                artists_name.delete(artists_name.lastIndexOf(","),artists_name.lastIndexOf(" "));
                downloadSong.setArtists_name(String.valueOf(artists_name));
            }
            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

            }
        });
        ApiService.apiService.getTypeOnSongId(song.getId()).enqueue(new Callback<ArrayList<Type>>() {
            final StringBuffer type_name=new StringBuffer();
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                assert response.body() != null;
                for(Type type:response.body()){
                    type_name.append(type.getTitle()).append(" , ");

                }
                type_name.delete(type_name.lastIndexOf(","),type_name.lastIndexOf(" "));
                downloadSong.setType_name(String.valueOf(type_name));
            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {

            }
        });
        MyApplication.downloadSongs.add(downloadSong);

    }
    private static long onDownloading(Context context, String type, String uri){
        long downloadId=-1;
        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(uri));
        if(type.equals(MyApplication.PICTURE)){
            Toast.makeText(context,"Đang tải",Toast.LENGTH_SHORT).show();
            request.setDestinationInExternalPublicDir( Environment.DIRECTORY_PICTURES, "download.jpg");
            DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId=downloadManager.enqueue(request);

        }else{
            request.setDescription("Đang tải nhạc xuống")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "file.mp3");
            DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId= downloadManager.enqueue(request);
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        long finalDownloadId = downloadId;
        executor.execute(() -> {
            while (check(context, finalDownloadId)==-1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            Intent intent = new Intent(MyApplication.SUCCESS);
            Bundle bundle=new Bundle();
            bundle.putLong(MyApplication.ID,check(context,finalDownloadId));
            bundle.putString(MyApplication.URI,getUriDownloaded(context,finalDownloadId));
            intent.putExtra(MyApplication.DATA,bundle);
            context.sendBroadcast(intent);
            executor.shutdown();
        });
        return downloadId;
    }
    private static long check(Context context, long downloadId){
        boolean isSuccess=false;
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                isSuccess=true;
            }
        }
        cursor.close();
        return isSuccess?downloadId:-1;
    }
    public static DownloadSong checkOver(long downloadId,String uri){
        int position=-1;
        for(int i=0;i<MyApplication.downloadSongs.size();i++){
            if(downloadId == MyApplication.downloadSongs.get(i).getImage_id()){
                MyApplication.downloadSongs.get(i).setImage(uri);
            }else if(downloadId ==  MyApplication.downloadSongs.get(i).getUri_id()){
                MyApplication.downloadSongs.get(i).setUri(uri);
            }
            if(checkFullElements( MyApplication.downloadSongs.get(i))){
                position=i;
                break;
            }
        }
        if(position >-1) return MyApplication.downloadSongs.remove(position);
        return null;

    }
    public static boolean checkFullElements(DownloadSong downloadSong){
        return !downloadSong.getImage().isEmpty() && !downloadSong.getUri().isEmpty();
    }
    public static String getUriDownloaded(Context context, long downloadId){
        String uri="";
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query=new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor= downloadManager.query(query);
        if(cursor != null && cursor.moveToFirst()){
            int uriColumn=cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            uri=cursor.getString(uriColumn);
        }
        return uri;
    }
    public static void saveDownloadSong(DownloadSong downloadSong,Context context){
        com.example.dictionary.Activity.RoomDataBase.Entity.Song song
                =new com.example.dictionary.Activity.RoomDataBase.Entity.Song
                (downloadSong.getSong_name(),downloadSong.getArtists_name(),downloadSong.getImage(),
                        downloadSong.getAlbum(),downloadSong.getUri(),downloadSong.getSong_id(),downloadSong.getYear_launched(),downloadSong.getType_name());
        MyDatabase.getInstance(context).userDAO().insertSong(song);
        Intent intent = new Intent(MyApplication.SUCCESSFULL);
        context.sendBroadcast(intent);
    }
    public void onOffline(Bundle bundle){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            com.example.dictionary.Activity.RoomDataBase.Entity.Song song=bundle.getSerializable(MyApplication.SONG,com.example.dictionary.Activity.RoomDataBase.Entity.Song.class);
            assert song != null;
            viewFragmentInterface.onOffline(song.getSinger(),song.getName(),song.getImage());
        }

    }
    public void onDownloadStatus(Context context,ImageView imageView){
        if(checkDownloaded(context)){
            viewFragmentInterface.onDownloadStatus(imageView);
            imageView.setEnabled(false);
        }
    }
    public boolean checkDownloaded(Context context){
        MyApplication.listOff= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getAllSongs();
        for(int i=0;i<MyApplication.listOff.size();i++){
            if(MyApplication.listOff.get(i).getSong_id()==MyApplication.song.getId()){
                return true;
            }
        }
        return false;
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        viewFragmentInterface.showBottomSheet(bottomFragment);
    }
    public void onReset(){
        viewFragmentInterface.onReset(0.3F);
    }
}
