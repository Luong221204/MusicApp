package com.example.dictionary.Activity.View.Activity;

import android.content.Intent;
import android.util.Log;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmptyPresenter {
    EmptyInterface emptyInterface;

    public EmptyPresenter(EmptyInterface emptyInterface) {
        this.emptyInterface = emptyInterface;
    }
    public void onInit(Intent intent){
        String name=intent.getStringExtra(MyApplication.ACTION);
        emptyInterface.onInit(name);
    }
    public void onChange(String name){
        ApiService.apiService.changeFullName(MyApplication.user.getUserId(),name).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null){
                    emptyInterface.onToast(response.body().getMessage());
                    MyApplication.user.setFullName(name);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                emptyInterface.onToast("fail");

            }
        });
    };
}
