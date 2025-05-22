package com.example.dictionary.Activity.LibActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.DataAdapter.DatabaseAdapter;
import com.example.dictionary.Activity.RecycleAdapter.RecycleAdapter;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.Activity.SortFragment.Filter2Fragment;
import com.example.dictionary.Activity.FilterFragment.FilterBottomFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Response;

public class LibActivityPresenter {
    DatabaseAdapter databaseAdapter;
    RecycleAdapter recycleAdapter;

    LibActivityInterface libActivityInterface;
    public LibActivityPresenter(LibActivityInterface libActivityInterface){
        this.libActivityInterface=libActivityInterface;
    }
    public void onInit(Intent intent, Context context, ItemClickListener itemClickListener){
        String title=intent.getStringExtra(MyApplication.ACTION);
        if(title != null &&title.equals("Đã tải")){
            List<Song> songs=MyDatabase.getInstance(context).userDAO().getSongsOnUserId(MyApplication.user.getUserId());
            libActivityInterface.onQuantity(songs.size());
            databaseAdapter=new DatabaseAdapter(itemClickListener,context, (ArrayList<Song>) songs,0,null);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            libActivityInterface.onRecycleViewDown(databaseAdapter,layoutManager);
            libActivityInterface.onInit(title);

        }else if(title != null &&title.equals("Yêu thích")){
            libActivityInterface.onQuantity(MyApplication.FavouriteSongs.size());
            recycleAdapter=new RecycleAdapter(MyApplication.FavouriteSongs,context,0,null,itemClickListener);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            libActivityInterface.onRecycleViewLove(recycleAdapter,layoutManager);
            libActivityInterface.onInit(title);

        }
    }
    public void showBottomSheet(com.example.dictionary.Activity.Model.Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        libActivityInterface.showBottomSheet(bottomFragment);
    }
    public void onFilterSort(Intent intent){
        String title=intent.getStringExtra(MyApplication.ACTION);
        Filter2Fragment fragment=new Filter2Fragment();
        if(title != null &&title.equals("Đã tải")){
            Bundle bundle=new Bundle();
            bundle.putString(MyApplication.BUNDLE,MyApplication.OFFLINE);
            fragment.setArguments(bundle);

        }else if(title != null &&title.equals("Yêu thích")){
            Bundle bundle=new Bundle();
            bundle.putString(MyApplication.BUNDLE,MyApplication.DATA);
            fragment.setArguments(bundle);
        }
        libActivityInterface.showFilterSort(fragment);
    }
    public void onFilter(Intent intent){
        String title=intent.getStringExtra(MyApplication.ACTION);
        FilterBottomFragment fragment=new FilterBottomFragment();
        if(title != null &&title.equals("Đã tải")){
            Bundle bundle=new Bundle();
            bundle.putString(MyApplication.BUNDLE,MyApplication.OFFLINE);
            fragment.setArguments(bundle);

        }else if(title != null &&title.equals("Yêu thích")){
            Bundle bundle=new Bundle();
            bundle.putString(MyApplication.BUNDLE,MyApplication.DATA);
            fragment.setArguments(bundle);
        }
        libActivityInterface.showFilter(fragment);
    }
    public void onSort(Intent intent,Intent sortIntent,Context context) throws ExecutionException, InterruptedException {
        String title=intent.getStringExtra(MyApplication.ACTION);
        if(title != null &&title.equals("Đã tải")){
            if(databaseAdapter != null) databaseAdapter.updateData(getSortDownloadSongs(Objects.requireNonNull(sortIntent.getStringExtra(MyApplication.ACTION)),context));
        }else if(title != null &&title.equals("Yêu thích")){
            if(recycleAdapter != null) {
                recycleAdapter.reset(getSortFavouriteSongs(Objects.requireNonNull(sortIntent.getStringExtra(MyApplication.ACTION)),context));
            }
        }
    }
    private ArrayList<Song> getSortDownloadSongs(String action,Context context){
        if(action.equals(MyApplication.SONG_ASC)){
            return (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getASCNameDownloadedSongs(MyApplication.user.getUserId());
        }else if(action.equals(MyApplication.NEWEST)){
            return (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getNewestDownloadedSongs(MyApplication.user.getUserId());
        }else if(action.equals(MyApplication.OLDEST)){
            return (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getOldestDownloadedSongs(MyApplication.user.getUserId());
        }else{
            return (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getArtistASCDownloadedSongs(MyApplication.user.getUserId());
        }
    }

    private ArrayList<com.example.dictionary.Activity.Model.Song> getSortFavouriteSongs(String action, Context context) throws InterruptedException, ExecutionException {
        if(action.equals(MyApplication.SONG_ASC)){
            Collections.sort(MyApplication.FavouriteSongs);
            return MyApplication.FavouriteSongs;
        }else if(action.equals(MyApplication.NEWEST)){
            Collections.sort(MyApplication.FavouriteSongs, new Comparator<com.example.dictionary.Activity.Model.Song>() {
                @Override
                public int compare(com.example.dictionary.Activity.Model.Song o1, com.example.dictionary.Activity.Model.Song o2) {
                    if(o1.getTime()>o2.getTime()) return 1;
                    else if(o1.getTime()<o2.getTime()) return -1;
                    return 0;
                }
            });
            return MyApplication.FavouriteSongs;
        }else if(action.equals(MyApplication.OLDEST)){
            Collections.sort(MyApplication.FavouriteSongs, new Comparator<com.example.dictionary.Activity.Model.Song>() {
                @Override
                public int compare(com.example.dictionary.Activity.Model.Song o1, com.example.dictionary.Activity.Model.Song o2) {
                    if(o1.getTime()<o2.getTime()) return 1;
                    else if(o1.getTime()>o2.getTime()) return -1;
                    return 0;
                }
            });
            return MyApplication.FavouriteSongs;
        }else{
            ExecutorService executorService= Executors.newFixedThreadPool(MyApplication.FavouriteSongs.size());
            List<Callable<ArrayList<Artist>>> callables=new ArrayList<>();
            for(com.example.dictionary.Activity.Model.Song song:MyApplication.FavouriteSongs){
                callables.add(()->{
                    Response<ArrayList<Artist>> response = ApiService.apiService.getArtistNameForIndicatedSong(song.getId()).execute();
                    return response.body();
                });
            }
            int i=0;
            List<Future<ArrayList<Artist>>> futures = executorService.invokeAll(callables);
            for (Future<ArrayList<Artist>> future : futures) {
                StringBuffer stringBuffer=new StringBuffer();
                ArrayList<Artist> artists = future.get();
                for(Artist artist:artists){
                    stringBuffer.append(artist.getName()).append(" ");
                }
                MyApplication.FavouriteSongs.get(i).setArtist_name(String.valueOf(stringBuffer));
                i++;
            }


            return MyApplication.FavouriteSongs;
        }
    }
}

