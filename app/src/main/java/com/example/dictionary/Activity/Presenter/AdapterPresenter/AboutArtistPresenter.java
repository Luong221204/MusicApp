package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.View.Activity.ArtistActivity;

public class AboutArtistPresenter {
    public void startActivity(Context context, Artist artist){
        Intent intent=new Intent(context, ArtistActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.ARTIST,artist);
        intent.putExtra(MyApplication.DATA,bundle);
        context.startActivity(intent);
    }
}
