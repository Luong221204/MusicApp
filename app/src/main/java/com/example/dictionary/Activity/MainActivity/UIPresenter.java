package com.example.dictionary.Activity.MainActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.DownloadSong;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.MainActivity.AccountFragment.AccountFragment;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.Activity.MainActivity.ExploreFragment.ExploreFragment;
import com.example.dictionary.Activity.MainActivity.LibraryFragment.LibraryFragment;
import com.example.dictionary.Activity.SearchFragment.SearchFragment;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UIPresenter {
    private final MainActivityInterface uiInterface;
    public boolean isShowed=false;
    public UIPresenter(MainActivityInterface uiInterface){
        this.uiInterface=uiInterface;
    }
    public void smallSongView(Intent intent){
        Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
        if(bundle != null){
            int action= bundle.getInt(MyApplication.ACTION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Song song=bundle.getSerializable(MyApplication.SONG,Song.class);
            }
        }
    }
    public void bottomNavigationView(MainActivity mainActivity, MenuItem item){
        int hide1=0,hide2 = 0,show = 0;
        String title="";
        FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
        @SuppressLint("CommitTransaction") FragmentTransaction transaction=fragmentManager.beginTransaction();
        if(item.getItemId()== R.id.libra){
            hide1=1; hide2=2; show=0; title="Thư viện";
        }else if(item.getItemId()==R.id.home){
            hide1=0; hide2=2; show=1; title="Khám phá";
        }else if(item.getItemId()==R.id.account){
            hide1=0; hide2=1; show=2; title="Cá nhân";
        }
        uiInterface.bottomNavigationView(show,hide1,hide2,title,transaction);

    }
    public ArrayList<Fragment> getFragments(Bundle bundle,MainActivity mainActivity,int frameLayoutId){
        ArrayList<Fragment> fragments=new ArrayList<>();
        ExploreFragment homeFragment;
        AccountFragment accountFragment;
        LibraryFragment libraryFragment;
        if(bundle == null){
            homeFragment=new ExploreFragment();
            accountFragment=new AccountFragment();
            libraryFragment=new LibraryFragment();
            mainActivity.getSupportFragmentManager().beginTransaction().add(frameLayoutId,homeFragment,"EXPLORE")
                    .add(frameLayoutId,accountFragment,"ACCOUNT")
                    .add(frameLayoutId,libraryFragment,"LIBRA")
                    .hide(homeFragment).hide(accountFragment).commit();
        }else{
            homeFragment= (ExploreFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("EXPLORE");
            accountFragment= (AccountFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("ACCOUNT");
            libraryFragment= (LibraryFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("LIBRA");
        }
        fragments.add(libraryFragment);
        fragments.add(homeFragment);
        fragments.add(accountFragment);
        return fragments;

    }
    public void onReplicate(Bundle bundle,FragmentTransaction fragmentTransaction){
        if(bundle != null){
            uiInterface.showTitleToolbar(bundle.getString(MyApplication.FRAGMENTCHOSEN));
            if(bundle.getBoolean(MyApplication.ISSHOWED)){
                uiInterface.showSearchFragment(fragmentTransaction, View.GONE);
            }
        }
    }
    public void onInit(){
        uiInterface.onInit();
    }
    public void verifyPressToSearchFragment(int id, FragmentTransaction fragmentTransaction){
        if(id==R.id.search){
            showSearchFragment(fragmentTransaction,R.id.fr2);
        }
    }
    public void showSearchFragment(FragmentTransaction fragmentTransaction,int frameLayoutId){
        fragmentTransaction.replace(frameLayoutId,new SearchFragment()).addToBackStack(SearchFragment.class.getName());
        uiInterface.showSearchFragment(fragmentTransaction, View.GONE);
    }
    public void restoreSearchFragment(Bundle bundle,MainActivity mainActivity,int frameLayoutId){
        if(bundle != null){
            mainActivity.isShowed=bundle.getBoolean(MyApplication.ISSHOWED);
        }
        if(mainActivity.isShowed){
            FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
            @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(frameLayoutId,new SearchFragment()).addToBackStack(SearchFragment.class.getName());
            uiInterface.showSearchFragment(fragmentTransaction,View.GONE);
        }
    }
    public void saveState(Bundle outState, ActionBar actionBar){
        outState.putBoolean(MyApplication.ISSHOWED,isShowed);
        outState.putString(MyApplication.FRAGMENTCHOSEN, (String) Objects.requireNonNull(actionBar).getTitle());
    }
    public void inflateMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.toolbar,menu);
    }
    public void downloadForFirstLogin(Context context){
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs= (ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song>) MyDatabase.getInstance(context).userDAO().getAllSongs();
        ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs1=new ArrayList<>();
        for(com.example.dictionary.Activity.Model.Song song:MyApplication.d){
            for(int i=0;i<songs.size();i++){
                if(song.getId() == songs.get(i).getSong_id()) songs1.add(songs.get(i));
            }
        }
        for(com.example.dictionary.Activity.Model.Song song:MyApplication.d){
            if(checkSongNotExisted(songs1,song) != null) {{
                executorService.submit(()->{download(context,checkSongNotExisted(songs1,song));});
            }}
        }
    }
    private void download(Context context, com.example.dictionary.Activity.Model.Song song){
        onDownload(context,song);
    }
    private static void onDownload(Context context, com.example.dictionary.Activity.Model.Song song){
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
            request.setDestinationInExternalPublicDir( Environment.DIRECTORY_PICTURES, "download.jpg");
            DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId=downloadManager.enqueue(request);
        }else{
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
            intent.putExtra(MyApplication.TOKEN,1000);
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

    private static String getUriDownloaded(Context context, long downloadId){
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
    private com.example.dictionary.Activity.Model.Song checkSongNotExisted(ArrayList<com.example.dictionary.Activity.RoomDataBase.Entity.Song> songs, com.example.dictionary.Activity.Model.Song song){
        for(int i=0;i<songs.size();i++){
            if(song.getId() == songs.get(i).getSong_id()) return null;
        }
        return song;
    }
}
