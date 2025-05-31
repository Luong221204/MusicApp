package com.example.dictionary.Activity.MainActivity.ExploreFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.LaterAdapter.LaterAdapter;
import com.example.dictionary.Activity.Adapter.FormAdapter.RecentAdapter;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.Activity.TypeAdapter.TypeAdapter;
import com.example.dictionary.Activity.Application.DataManager;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragmentPresenter {
    ExploreFragmentInterface exploreFragmentInterface;
    public ExploreFragmentPresenter(ExploreFragmentInterface exploreFragmentInterface){
        this.exploreFragmentInterface=exploreFragmentInterface;
    }
    public void onInit(Context context){
        ApiService.apiService.ping().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                exploreFragmentInterface.onInternetConnect(View.VISIBLE,View.GONE,View.VISIBLE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                exploreFragmentInterface.onInternetConnect(View.GONE,View.VISIBLE,View.GONE);

            }
        });
    }
    public void showOnHintRecycle(Context context, ItemClickListener itemClickListener){
        ArrayList<String> stringSet= new ArrayList<>(DataManager.getInstance().getRoutines());
        int index= MyApplication.type_map.get(stringSet.get(1));
        ApiService.apiService.getSongsBasedOnType(index).enqueue(new Callback<ArrayList<Song>>() {
            @Override
            public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                ArrayList<Song> songs=response.body();
                if(songs != null){
                    ArrayList<ArrayList<Song>> lists=new ArrayList<>();
                    int cnt1=0;
                    for(int i=0;i<songs.size()/3+1;i++){
                        int cnt=0;
                        ArrayList<Song> list1=new ArrayList<>();
                        while( cnt1<songs.size() && cnt<3){
                            list1.add(songs.get(cnt1));
                            cnt++;
                            cnt1++;
                        }
                        lists.add(list1);
                    }
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    RecentAdapter adapter=new RecentAdapter( context, lists, itemClickListener);
                    exploreFragmentInterface.showOnHintRecycle(linearLayoutManager,adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

            }
        });
    }
    public void showOnIdol(){
        ApiService.apiService.getArtistIndexed(String.valueOf(2)).enqueue(new Callback<ArrayList<Artist>>() {
            @Override
            public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                exploreFragmentInterface.showOnIdol(response.body().get(0).getImage(),response.body().get(0).getName());
            }

            @Override
            public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

            }
        });
    }
    public void showOnIdolAlbum(Context context){
        ApiService.apiService.getAlbumOnArtistId(2).enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if(response.body() != null){
                    AlbumAdapter albumAdapter=new AlbumAdapter(context,response.body(),0);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    exploreFragmentInterface.showOnIdolAlbumRecycle(albumAdapter,layoutManager);
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
            }
        });
    }
    public void showBottomSheet(Song song){
        BottomFragment bottomFragment=new BottomFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomFragment.setArguments(bundle);
        exploreFragmentInterface.showBottomSheet(bottomFragment);
    }
    public void showOnThemes(Context context){
        ApiService.apiService.getTypeHot().enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                if(response.body() !=null){
                    TypeAdapter typeAdapter=new TypeAdapter(
                            context,response.body(),1);
                    LinearLayoutManager layoutManager2=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    exploreFragmentInterface.showOnThemeRecycle(typeAdapter,layoutManager2);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {

            }
        });
    }
    public void showOnHistory(Context context){
        ArrayList<Song> songs= (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getRecentlySongs(MyApplication.user.getUserId());
        LaterAdapter laterAdapter=new LaterAdapter(context,MyApplication.history,songs);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        exploreFragmentInterface.showOnHistoryRecycle(laterAdapter,layoutManager1);
    }
    public void showOnAlbumHot(Context context){
        ApiService.apiService.getAlbumsHot().enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if(response.body() != null){
                    AlbumAdapter albumAdapter=new AlbumAdapter(context,response.body(),1);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                    exploreFragmentInterface.showOnHotAlbum(albumAdapter,layoutManager);
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

            }
        });
    }
}
