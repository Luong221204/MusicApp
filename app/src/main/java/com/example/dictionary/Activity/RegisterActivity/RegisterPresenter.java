package com.example.dictionary.Activity.RegisterActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.R;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    RegisterInterface registerInterface;
    public RegisterPresenter(RegisterInterface registerInterface){
        this.registerInterface=registerInterface;
    }
    public void showDialog(Context context){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.favourit_dialog);
        Window window=dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams win=window.getAttributes();
        win.gravity= Gravity.CENTER;
        window.setAttributes(win);
        dialog.setCancelable(true);
        registerInterface.showDialog(dialog);
    }
    public void setMusicRoutines(Set<String> routines){
        if(routines.isEmpty()) return;
        StringBuilder stringBuffer=new StringBuilder();
        for(String routine:routines){
            stringBuffer.append(routine).append(" , ");
        }
        stringBuffer.delete(stringBuffer.lastIndexOf(","),stringBuffer.lastIndexOf(" "));
        registerInterface.showFavouriteTypes(String.valueOf(stringBuffer));
        //DataManager.getInstance().mySharedPreference.setRoutine(routines);
    }
    public void register(String name,String username,String password,String confirm,int gender,Set<String> strings,String phone){
        if(name.isEmpty()||username.isEmpty()||password.isEmpty()||confirm.isEmpty()||phone.isEmpty()){
            registerInterface.showLack();
            return;
        }
        if(!confirm.equals(password)){
            registerInterface.showError();
            return;
        }
        User user=new User();
        user.setFullName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoneNumber(phone);
        user.setGender(gender);
        ApiService.apiService.postRegister(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        registerInterface.register(response.body().getMessage());
                        if(response.body().getMessage().equals("Đăng kí thành công")){
                            Intent intent=new Intent();
                            intent.putExtra(MyApplication.USER,username);
                            intent.putExtra(MyApplication.DATA,password);
                            registerInterface.finishActivity(intent);
                        }

                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                registerInterface.showError();

            }
        });
    }
}
