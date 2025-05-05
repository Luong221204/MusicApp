package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.content.Intent;

import com.example.dictionary.Activity.View.Activity.RecentActivity;

public class LaterPresenter {
    public void startActivity(int position, Context context){
        if(position!=0) return;
        Intent intent1=new Intent(context, RecentActivity.class);
        context.startActivity(intent1);
    }
}
