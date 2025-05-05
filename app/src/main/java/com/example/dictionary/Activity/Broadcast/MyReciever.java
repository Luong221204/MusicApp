package com.example.dictionary.Activity.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Application.MyLiveData;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.DatabasePresenter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.Model.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReciever extends BroadcastReceiver {
    Song song;
    ArrayList<Type> types;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getBundleExtra(MyApplication.ACTION);
        if(bundle != null){
            int action=bundle.getInt(MyApplication.ACTION,10000);
            if(action == MyApplication.PAUSE ||action== MyApplication.PLAY){
                Intent intent2=new Intent(MyApplication.FRAGMENTCHOSEN);
                Bundle bundle1=new Bundle();
                bundle1.putInt(MyApplication.ACTION,action);
                intent2.putExtra(MyApplication.BUNDLE,bundle1);
                context.sendBroadcast(intent2);
                Intent intent1=new Intent(context, MyService.class);
                intent1.putExtra(MyApplication.ACTION,action);
                context.startService(intent1);
            }else if( action == MyApplication.NEXT||action==MyApplication.BACK){
                int index=0;
                if(MyApplication.song.getAlbum_id()==-1){
                    if(action == MyApplication.NEXT){
                        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs= MyLiveData.getInstance().getListSongsOff().getValue();
                        if(songs != null&&songs.isEmpty()&&!MyApplication.autoOff.isEmpty()){
                            song= DatabasePresenter.convert(MyApplication.autoOff.get(0));
                        }else if(songs != null && !songs.isEmpty()){
                            song=DatabasePresenter.convert(songs.get(0));
                            songs.remove(songs.get(0));
                        }
                    }else {
                        if(MyApplication.stackOff.isEmpty()) song=MyApplication.song;
                        else{
                            song =DatabasePresenter.convert(MyApplication.stackOff.pop());
                        }
                    }
                    MyApplication.autoOff=DatabasePresenter.matchToList((ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getAllSongs(),song.getId());
                    Intent intent2=new Intent(MyApplication.AGAIN4);
                    context.sendBroadcast(intent2);
                }
                else{
                    if(action == MyApplication.NEXT){
                        ArrayList<Song> songs= MyLiveData.getInstance().getListSongs().getValue();
                        if(songs != null&&songs.isEmpty()&&!MyApplication.auto.isEmpty()){
                            song=MyApplication.auto.get(0);
                        }else if(songs != null && !songs.isEmpty()){
                            song=songs.get(0);
                            songs.remove(song);
                        }
                        MyApplication.list.add(0,song);
                    }else {
                        if(MyApplication.stack.isEmpty()) song=MyApplication.song;
                        else{
                            song =MyApplication.stack.pop();
                        }
                    }
                    ApiService.apiService.getTypeOnSongId(song.getId()).enqueue(new Callback<ArrayList<Type>>() {
                        @Override
                        public void onResponse(@NonNull Call<ArrayList<Type>> call, @NonNull Response<ArrayList<Type>> response) {
                            types=response.body();
                            assert types != null;
                            for(Type type1:types){
                                ApiService.apiService.getSongsBasedOnType(type1.getId()).enqueue(new Callback<ArrayList<Song>>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ArrayList<Song>> call, @NonNull Response<ArrayList<Song>> response) {
                                        MyApplication.auto=response.body();
                                        Song song_temp=null;
                                        if( MyApplication.auto != null &&!MyApplication.auto.isEmpty() ){
                                            for(Song song:MyApplication.auto){
                                                if(song.getId()==MyApplication.song.getId())      song_temp=song  ;
                                            }
                                            MyApplication.auto.remove(song_temp);
                                        }
                                        Gson gson=new Gson();
                                        JsonArray json=gson.toJsonTree(MyApplication.auto).getAsJsonArray();
                                        String data=json.toString();
                                        Intent intent2=new Intent(MyApplication.FROMRECEIVE);
                                        intent2.putExtra(MyApplication.SONG,data);
                                        context.sendBroadcast(intent2);
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ArrayList<Song>> call, @NonNull Throwable t) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<ArrayList<Type>> call, @NonNull Throwable t) {

                        }
                    });

                }
                Intent intent1=new Intent(context,MyService.class);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable(MyApplication.SONG,song);
                intent1.putExtra(MyApplication.BUNDLE,bundle1);
                context.startService(intent1);
                Intent sendToUIBroadcast=new Intent(MyApplication.ISBACK);
                Bundle bundle2=new Bundle();
                bundle2.putSerializable(MyApplication.SONG,song);
                sendToUIBroadcast.putExtra(MyApplication.BUNDLE,bundle2);
                context.sendBroadcast(sendToUIBroadcast);


            }

        }

    }
}
