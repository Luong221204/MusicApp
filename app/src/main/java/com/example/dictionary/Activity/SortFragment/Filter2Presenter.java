package com.example.dictionary.Activity.SortFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.dictionary.Activity.Application.MyApplication;

import java.util.Collections;
import java.util.Objects;

public class Filter2Presenter {
    Filter2Interface filter2Interface;

    public Filter2Presenter(Filter2Interface filter2Interface) {
        this.filter2Interface = filter2Interface;
    }
    public void onInit(Bundle bundle, Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(bundle != null && Objects.equals(bundle.getString(MyApplication.BUNDLE), MyApplication.DATA)){
            if(networkInfo != null && networkInfo.isConnected()){
                filter2Interface.onView(View.VISIBLE);
                return;
            }
            filter2Interface.onView(View.GONE);
        }else if(bundle != null && Objects.equals(bundle.getString(MyApplication.BUNDLE), MyApplication.OFFLINE)){
            filter2Interface.onView(View.VISIBLE);
        }
    }
    public void onSortSongName(Context context,String action){
        Collections.sort(MyApplication.FavouriteSongs);
        Intent intent=new Intent(MyApplication.SORT);
        intent.putExtra(MyApplication.ACTION,action);
        context.sendBroadcast(intent);

    }
}
