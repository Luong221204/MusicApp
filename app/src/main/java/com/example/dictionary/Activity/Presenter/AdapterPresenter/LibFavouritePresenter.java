package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Lib;
import com.example.dictionary.Activity.FavouriteArtistActivity.FavouriteArtistActivity;
import com.example.dictionary.Activity.LibActivity.LibActivity;

public class LibFavouritePresenter {
    public void startActivity(Context context, Lib lib){
        if(lib.getName().equals("Nghệ sĩ")){
            Intent intent1=new Intent(context, FavouriteArtistActivity.class);
            intent1.putExtra(MyApplication.ACTION,lib.getName());
            context.startActivity(intent1);
            return;
        }
        Intent intent1=new Intent(context, LibActivity.class);
        intent1.putExtra(MyApplication.ACTION,lib.getName());
        context.startActivity(intent1);
    }
}
