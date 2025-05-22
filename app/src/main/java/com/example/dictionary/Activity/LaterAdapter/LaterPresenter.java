package com.example.dictionary.Activity.LaterAdapter;

import android.content.Context;
import android.content.Intent;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.RecentActivity.RecentActivity;

public class LaterPresenter {
    LaterIconInterface laterIconInterface;

    public LaterPresenter(LaterIconInterface laterIconInterface) {
        this.laterIconInterface = laterIconInterface;
    }

    public void startActivity(int position, Context context){
        if(position!=0) return;
        Intent intent1=new Intent(context, RecentActivity.class);
        intent1.putExtra(MyApplication.OFFLINE,MyApplication.OFFLINE);
        context.startActivity(intent1);
    }
    public void onInit(Later later){
        laterIconInterface.onInit(later.getIcon(),later.getName());
    }
}
