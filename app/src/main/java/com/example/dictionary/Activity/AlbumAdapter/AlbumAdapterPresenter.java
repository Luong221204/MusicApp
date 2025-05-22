package com.example.dictionary.Activity.AlbumAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.AlbumActivity.AlbumActivity;
import com.example.dictionary.Activity.AllAbumActivity.AllAlbumsActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

public class AlbumAdapterPresenter {
    AlbumAdapterInterface albumAdapterInterface;

    public AlbumAdapterPresenter(AlbumAdapterInterface albumAdapterInterface) {
        this.albumAdapterInterface = albumAdapterInterface;
    }
    public void onInit(int mode , int position, ArrayList<Album> albums){
        if(mode == 0){
            albumAdapterInterface.onInit(albums.get(position).getName(),albums.get(position).getImage());
        }else{
            if(position == albums.size()) albumAdapterInterface.onInitEnd("Xem tất cả", R.drawable.arrow);
            else{
                albumAdapterInterface.onInit(albums.get(position).getName(),albums.get(position).getImage());
            }
        }
    }
    public void sendBroadcast(Context context,  ArrayList<Album> albums,int mode,int position){
        if(mode == 0){
            Intent intent1=new Intent(context, AlbumActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable(MyApplication.ACTION,albums.get(position));
            intent1.putExtra(MyApplication.BUNDLE,bundle);
            context.startActivity(intent1);
        }else{
            if(position == albums.size()){
                Intent intent1=new Intent(context, AllAlbumsActivity.class);
                context.startActivity(intent1);

            }else{
                Intent intent1=new Intent(context, AlbumActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(MyApplication.ACTION,albums.get(position));
                intent1.putExtra(MyApplication.BUNDLE,bundle);
                context.startActivity(intent1);
            }

        }
    }
}
