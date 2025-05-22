package com.example.dictionary.Activity.VIewSongActivity.ViewFragment;

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
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Broadcast.MyReciever;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.BottomFragment.BottomFragmentPresenter;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.RoomDataBase.Entity.User_songs;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.VIewSongActivity.ViewSongActivity;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFragmentPresenter {
    int t = 0, temp = 0;
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
    public void onIntent(Intent intent,Context context){
        Bundle bundle = intent.getBundleExtra(MyApplication.DATA);
        if(bundle != null){
            String uri=bundle.getString(MyApplication.URI);
            long downloadId=bundle.getLong(MyApplication.ID,-1);
            DownloadSong downloadSong=ViewFragmentPresenter.checkOver(downloadId,uri);
            if( downloadSong!= null){
                ViewFragmentPresenter.saveDownloadSong(downloadSong,context);
            }
        }
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
    public void sendYourFavourite(Context context){
        ApiService.apiService.loveOrNot(MyApplication.user.getUserId(),MyApplication.song.getId()).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                MyApplication.FavouriteSongs.clear();
                ApiService.apiService.getFavouriteSongs(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Song>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                        MyApplication.FavouriteSongs=response.body();
                        viewFragmentInterface.onLove(MyApplication.song.isLoved()?R.drawable.like:R.drawable.heart);
                        Song song=MyDatabase.getInstance(context).userDAO()
                                .checkSongRecentlyId(MyApplication.user.getUserId(),MyApplication.song.getId());
                        if(song != null)MyDatabase.getInstance(context).userDAO().updateStatus(!MyApplication.song.isLoved(),MyApplication.user.getUserId(),MyApplication.song.getId());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                    }
                });
            }


            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });
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
                    viewFragmentInterface.onLove( MyApplication.song.isLoved()?R.drawable.heart:R.drawable.like);
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
                    viewFragmentInterface.onLove( MyApplication.song.isLoved()?R.drawable.heart:R.drawable.like);

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
        if(BottomFragmentPresenter.checkDownloaded2(song,context) != null){
            postToServerDownloadSong(song,context);
            saveSong_User(song.getId(),MyApplication.user.getUserId(),context);
            sendBroadCast(context);
            return;
        }
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
    private static long getDownloadId(Context context, String type, String uri){
        long downloadId=-1;
        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(uri));
        if(type.equals(MyApplication.PICTURE)){
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
        return downloadId;
    }

    private static long onDownloading(Context context, String type, String uri){
        long downloadId=getDownloadId(context,type,uri);
        continueDownload(downloadId,context);
        return downloadId;
    }
    private static void continueDownload(long finalDownloadId,Context context){
        ExecutorService executor = Executors.newSingleThreadExecutor();
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
    }
    public static void onDownloadBackground(Context context,String type,String uri){
        long downloadId=getDownloadId(context,type,uri);
        continueDownloadInBackGround(downloadId,context);
        Intent intent=new Intent(MyApplication.RECENTLY);
        context.sendBroadcast(intent);
    }
    private static void continueDownloadInBackGround(long finalDownloadId,Context context){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (check(context, finalDownloadId)==-1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            String uri=getUriDownloaded(context,finalDownloadId);
            Song song=new Song(-1,-1);
            song.setName(MyApplication.song.getName());
            song.setId(MyApplication.song.getId());
            song.setImage(uri);
            song.setTime(System.currentTimeMillis());
            song.setUserId(MyApplication.user.getUserId());
            song.setLoved(MyApplication.song.isLoved());
            MyDatabase.getInstance(context).userDAO().insertRecentlySong(song);
            executor.shutdown();
        });
    }
    public static void onDownloadArtist(Context context,String type,Artist artist){
        long downloadId=getDownloadId(context,type,artist.getImage());
        continueDownloadArtist(downloadId,context,artist);
    }
    private static void continueDownloadArtist(long finalDownloadId,Context context,Artist artist){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (check(context, finalDownloadId)==-1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            String uri=getUriDownloaded(context,finalDownloadId);
            Artist downartist=new Artist(-1,-1);
            downartist.setName(artist.getName());
            downartist.setId(artist.getId());
            downartist.setImage(uri);
            downartist.setUserId(MyApplication.user.getUserId());
            downartist.setLoved(artist.isLoved());
            downartist.setType(1);
            MyDatabase.getInstance(context).userDAO().insertArtist(downartist);
            executor.shutdown();
        });
    }
    public static void onDownloadAlbum(Context context,String type,Album album){
        long downloadId=getDownloadId(context,type,album.getImage());
        continueDownloadAlbum(downloadId,context,album);
    }
    private static void continueDownloadAlbum(long finalDownloadId,Context context,Album album){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (check(context, finalDownloadId)==-1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            String uri=getUriDownloaded(context,finalDownloadId);
            Album downAlbum=new Album();
            downAlbum.setName(album.getName());
            downAlbum.setId(album.getId());
            downAlbum.setImage(uri);
            downAlbum.setUserId(MyApplication.user.getUserId());
            downAlbum.setLoved(album.isLoved());
            MyDatabase.getInstance(context).userDAO().insertAlbum(downAlbum);
            executor.shutdown();
        });
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
    private static boolean checkFullElements(DownloadSong downloadSong){
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
    private static void sendBroadCast(Context context){
        Intent intent = new Intent(MyApplication.SUCCESSFULL);
        context.sendBroadcast(intent);
    }
    public static void saveDownLoadedSong(DownloadSong downloadSong,Context context){
        com.example.dictionary.Activity.RoomDataBase.Entity.Song song
                =new com.example.dictionary.Activity.RoomDataBase.Entity.Song
                (downloadSong.getSong_name(),downloadSong.getArtists_name(),downloadSong.getImage(),
                        downloadSong.getAlbum(),downloadSong.getUri(),downloadSong.getSong_id(),downloadSong.getYear_launched(),downloadSong.getType_name());
        song.setTime(System.currentTimeMillis());
        MyDatabase.getInstance(context).userDAO().insertSong(song);
        saveSong_User(downloadSong.getSong_id(),MyApplication.user.getUserId(),context);
    }
    public static void saveSong_User(int song_id,int userId,Context context){
        User_songs userSongs=new User_songs();
        userSongs.setUserId(userId);
        userSongs.setSong_id(song_id);
        MyDatabase.getInstance(context).userDAO().insertUser_Song(userSongs);
    }
    private static void postToServerDownloadSong(Song song,Context context){
        ApiService.apiService.postDownload(MyApplication.user.getUserId(),song.getId()).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if(response.isSuccessful() && response.body()!= null&& !response.body().getMessage().isEmpty()){
                    //onDownload(context,song);
                }
            }
            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });
    }
    public static void saveDownloadSong(DownloadSong downloadSong,Context context){
        ApiService.apiService.postDownload(MyApplication.user.getUserId(),downloadSong.getSong_id()).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if(response.isSuccessful() && response.body() != null){
                    saveDownLoadedSong(downloadSong,context);
                    sendBroadCast(context);
                }
            }
            @Override
            public void onFailure(Call<Song> call, Throwable t) {

            }
        });

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
    private boolean checkDownloaded(Context context){
        MyApplication.listOff= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getSongsOnUserId(MyApplication.user.getUserId());
        for(int i=0;i<MyApplication.listOff.size();i++){
            if(MyApplication.listOff.get(i).getSong_id()==MyApplication.song.getId() ){
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
