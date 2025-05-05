package com.example.dictionary.Activity.Presenter.ViewPresenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.BottomFragmentInterface;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomFragmentPresenter {
    StringBuffer stringBuffer;
    Song song1=null;
    Bundle bundle=null;
    BottomFragmentInterface bottomFragmentInterface;
    public BottomFragmentPresenter(BottomFragmentInterface bottomFragmentInterface){
        this.bottomFragmentInterface=bottomFragmentInterface;
    }
    public void onInit(Bundle bundle, Context context){
        this.bundle=bundle;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            song1=bundle.getSerializable(MyApplication.SONG,Song.class);
        }
        showInformation(song1,context);
    }
    public void onRecycleView(Bundle bundle,Context context){
        Song song=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            song=bundle.getSerializable(MyApplication.SONG,Song.class);
        }
        onCheck(song,context);

    }
    public com.example.dictionary.Activity.RoomDataBase.Entity.Song checkDownloaded(Song song,Context context){
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> list=(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getAllSongs();
        for(int i=0;i<list.size();i++){
            if(song.getId()==list.get(i).getSong_id()) return list.get(i);
        }
        return null;
    }
    public void reset(Context context){
        if(bundle != null){
            onRecycleView(bundle,context);
        }
    }
    private void showInformation(Song song1,Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo != null && checkDownloaded(song1,context) != null && !networkInfo.isConnected() ){
            bottomFragmentInterface.onInitOff(checkDownloaded(song1,context).getName(), checkDownloaded(song1,context).getSinger(),checkDownloaded(song1,context).getImage());
        }
        else{
            ApiService.apiService.getArtistNameForIndicatedSong(song1.getId()).enqueue(new Callback<ArrayList<Artist>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Artist>> call, @NonNull Response<ArrayList<Artist>> response) {
                    ArrayList<Artist> artists=response.body();
                    stringBuffer=new StringBuffer();
                    MyApplication.artists=new ArrayList<>();
                    for(Artist artist:artists){
                        MyApplication.artists.add(artist);
                        stringBuffer.append(artist.getName()).append(" , ");
                    }
                    stringBuffer.delete(stringBuffer.lastIndexOf(","),stringBuffer.lastIndexOf(" "));
                    bottomFragmentInterface.onInit(song1.getName(), String.valueOf(stringBuffer),song1.getImage());
                }
                @Override
                public void onFailure(@NonNull Call<ArrayList<Artist>> call, Throwable t) {
                }
            });
        }
    }
    private void onCheck(Song song,Context context){
        ArrayList<Options> options = new ArrayList<>();
        options.add(new Options(R.drawable.add,"Thêm vào Playlist"));
        options.add(new Options(R.drawable.download,"Tải xuống"));
        options.add(new Options(R.drawable.like,"Thêm vào Thư viện"));
        options.add(new Options(R.drawable.img,"Xem nghệ sĩ"));
        options.add(new Options(R.drawable.img_5,"Thêm vào danh sách phát"));
        if(checkDownloaded(song,context) != null){
            for(int i=0;i< options.size();i++){
                if(options.get(i).getName().equals("Tải xuống")){
                    options.get(i).setFlagDisable(true);
                    options.get(i).setName("Đã tải xuống");
                    break;
                }
            }
        }
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("DucLuong", "Đã kết nối Wi-Fi");
        } else {
            for(int i=0;i< options.size();i++){
                if(options.get(i).getName().equals("Xem nghệ sĩ")){
                    options.get(i).setFlagDisable(true);
                    break;
                }
            }
        }
        bottomFragmentInterface.onRecycleView(options,song);
    }
    public void onInitAgain(Context context){
        showInformation(MyApplication.song,context);
        onCheck(MyApplication.song,context);
    }
}
