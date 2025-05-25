package com.example.dictionary.Activity.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Lib;
import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.Activity.VIewSongActivity.ViewFragment.ViewFragmentPresenter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class MyApplication extends Application {
    public static  final String CHANNEL_ID="CHANNEL_ID";
    public static final String ACTION="ACTION";
    public static final String ARTIST="ARTIST";
    public static final String BUNDLE="BUNDLE";
    public static final String SEARCH="SEARCH";
    public static final String COMPLETE="COMPLETE";
    public static final String ID="ID";
    public static final String SONG="SONG";
    public static final String SUCCESS="SUCCESS";
    public static final String SUCCESSFULL="SUCCESSFULL";
    public static final String SMALLVIEW="SMALLVIEW";
    public static final String ISSHOWED="ISSHOWED";
    public static final String DATA="DATA";
    public static final String ISBACK="ISBACK";
    public static final String UPDATE="UPDATE";
    public static final String ISBACKOFF="ISBACKOFF";
    public static final String AGAIN="AGAIN";
    public static final String AGAIN3="AGAIN3";
    public static final String APPLICATION="APPLICATION";
    public static final String AGAIN4="AGAIN4";
    public static final String OFFLINE="OFFLINE";
    public static final String AGAIN2="AGAIN2";
    public static final String PICTURE="PICTURE";
    public static final String AUDIO="AUDIO";
    public static final String URI="URI";
    public static final String MYROUTINE="MYROUTINE";
    public static final String MySTORE="MYSTORE";
    public static final String RECENTLY="RECENTLY";

    public static final String USER="USER";
    public static final String FRAGMENTCHOSEN="FragmentChosen";
    public static final String FROMRECEIVE="FROMRECEIVE";
    public static final String TOKEN="TOKEN";
    public static final String LOG="LOG";
    public static final int PAUSE=0;
    public static final int PLAY=1;
    public static final int NEXT=2;
    public static final int BACK=3;
    public static final int DEFAULT=-1000;
    public static final String SONG_ASC = "SONG_ASC";
    public static final String NEWEST = "NEWEST";
    public static final String OLDEST = "OLDEST";
    public static final String SORT = "SORT";
    public static final String ARTIST_ASC = "ARTIST_ASC";

    public static ArrayList<Song> list, auto,d;
    public static ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> listOff, autoOff=new ArrayList<>();
    public static ArrayList<Artist> artists;
    public static ArrayList<Search> fakeData;
    public static ArrayList<Options> options;
    public static ArrayList<Playlist> playlists;
    public static Map<String,Integer> type_map;
    public static ArrayList<DownloadSong> downloadSongs;
    public static ArrayList<Song> FavouriteSongs;
    public static ArrayList<Artist> FavouriteArtists;
    public static ArrayList<Album> FavouriteAlbums;
    public static ArrayList<Behalf> behalves=new ArrayList<>();
    public static ArrayList<Album> albums=new ArrayList<>();
    public static ArrayList<Later> theme=new ArrayList<>();
    public static ArrayList<Later> history=new ArrayList<>();
    public static ArrayList<Lib> libs=new ArrayList<>();
    public static Lib Love=new Lib(R.drawable.heart_pink,"Yêu thích");
    public static Lib SongDownloaded=new Lib(R.drawable.down_load,"Đã tải");
    public static Lib Artists=new Lib(R.drawable.artist,"Nghệ sĩ");
    public static Stack<Song> stack;
    public static Stack<com.example.dictionary.Activity.RoomDataBase.Entity.Song> stackOff;
    public static HandlerThread handlerThread;
    public static Handler handler;
    public static Song song;
    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying=false;
    public static User user=new User();
    public static int cnt;

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.DATA.equals(intent.getAction())){
                String data=intent.getStringExtra(MyApplication.SONG);
                try {
                    MyApplication.auto=getSongs(data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else if(MyApplication.FROMRECEIVE.equals(intent.getAction())){
                String data=intent.getStringExtra(MyApplication.SONG);
                try {
                    MyApplication.auto=getSongs(data);
                    Intent intent2=new Intent(MyApplication.AGAIN2);
                    getApplicationContext().sendBroadcast(intent2);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else if(MyApplication.SUCCESS.equals(intent.getAction())) {
                Bundle bundle = intent.getBundleExtra(MyApplication.DATA);
                if(bundle != null){
                    String uri=bundle.getString(MyApplication.URI);
                    long downloadId=bundle.getLong(MyApplication.ID,-1);
                    DownloadSong downloadSong= ViewFragmentPresenter.checkOver(downloadId,uri);
                    if( downloadSong!= null){
                        ViewFragmentPresenter.saveDownloadSong(downloadSong,context);
                    }
                }
            }else if(MyApplication.SUCCESSFULL.equals(intent.getAction())) {
                Toast.makeText(context, "Đã tải thành công", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ArrayList<Song> getSongs(String data) throws JSONException {
        ArrayList<Song> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(data, new TypeToken<ArrayList<Song>>() {}.getType());
        return list;
    }
    private ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> getSongOff(String data) throws JSONException {
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(data, new TypeToken<ArrayList<Song>>() {}.getType());
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.DATA);
        intentFilter.addAction(MyApplication.OFFLINE);
        intentFilter.addAction(MyApplication.SUCCESS);
        intentFilter.addAction(MyApplication.SUCCESSFULL);

        intentFilter.addAction(MyApplication.FROMRECEIVE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }
        DataManager.init(getApplicationContext());
        MyLiveData.init();
        mediaPlayer=null;
        handlerThread=new HandlerThread("DucLuong");
        handlerThread.start();
        downloadSongs=new ArrayList<>();
        listOff=new ArrayList<>();
        options=new ArrayList<>();
        playlists=new ArrayList<>();
        list=new ArrayList<>();
        stack=new Stack<>();
        stackOff=new Stack<>();
        listOff= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(getApplicationContext()).userDAO().getAllSongs();
        auto=new ArrayList<>();
        fakeData=new ArrayList<>();
        artists = new ArrayList<>();
        type_map=new HashMap<>();
        type_map.put("pop",1);
        type_map.put("ballad",2);
        type_map.put("âu mý",3);
        type_map.put("rock",4);
        type_map.put("fonk",5);
        type_map.put("edm",6);
        type_map.put("việt",7);

        options.add(new Options(R.drawable.add,"Thêm vào Playlist"));
        options.add(new Options(R.drawable.download,"Tải xuống"));
        options.add(new Options(R.drawable.like,"Thêm vào Thư viện"));
        options.add(new Options(R.drawable.img,"Xem nghệ sĩ"));
        theme.add(new Later(R.drawable.vpop,"V-Pop"));
        theme.add(new Later(R.drawable.mi,"Âu-Mĩ"));
        theme.add(new Later(R.drawable.fonk,"fonk"));
        theme.add(new Later(R.drawable.ballad,"Ballad"));
        theme.add(new Later(R.drawable.edm,"EDM"));
        theme.add(new Later(R.drawable.rocj,"Rock"));
        theme.add(new Later(R.drawable.pop,"Pop"));
        theme.add(new Later(R.drawable.arrow,"Xem tất cả"));
        FavouriteSongs=new ArrayList<>();
        FavouriteArtists=new ArrayList<>();
        FavouriteAlbums=new ArrayList<>();
        d=new ArrayList<>();
        history.add(new Later(R.drawable.later,"Bài hát nghe gần đây"));
        libs.add(Love);
        libs.add(SongDownloaded);
        //SongDownloaded.setCount(MyDatabase.getInstance(getApplicationContext()).userDAO().getAllSongs().size());
        libs.add(Artists);
        if(!DataManager.getFirstInstalled()){
            Set<String> routines=new HashSet<>();
            routines.add("love");
            routines.add("pop");
            DataManager.getInstance().setRoutine(routines);
            DataManager.getInstance().setFirstInstalled();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"MY ZING",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null,null);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            if(notificationManager !=null) {
                notificationManager.createNotificationChannel(channel);
            }
        }



    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(broadcastReceiver);
    }
}
