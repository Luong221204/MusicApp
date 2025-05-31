package com.example.dictionary.Activity.MainActivity.AccountFragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.ApiService.RealPathUtil;
import com.example.dictionary.Activity.Application.DataManager;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.ChangeActivity.ChangeActivity;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.LoginActivity.LoginActivity;
import com.example.dictionary.Activity.View.Activity.EmptyActivity;
import com.example.dictionary.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountPresenter {
    AccountInterface accountInterface;
    int request_code=100;
    public AccountPresenter(AccountInterface accountInterface){
        this.accountInterface=accountInterface;
    }
    public void init(){
        if(MyApplication.user.getGender()==1){
            if(MyApplication.user.getAvatar() == null){
                accountInterface.init(R.drawable.img_7,MyApplication.user.getFullName());
            }else{
                accountInterface.init2(MyApplication.user.getAvatar(),MyApplication.user.getFullName());

            }
        }else{
            if(MyApplication.user.getAvatar() == null){
                accountInterface.init(R.drawable.img_8,MyApplication.user.getFullName());
            }else{
                accountInterface.init2(MyApplication.user.getAvatar(),MyApplication.user.getFullName());

            }
        }
    }
    public void sign_out(Context context){
        Dialog dialog= LoginPresenter.dialog(context);
        dialog.show();
        MyApplication.playlists.clear();
        MyApplication.playlists.add(new Playlist(R.drawable.plus,"Tạo Playlist"));
        DataManager.getInstance().setLoginState(false);
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        dialog.dismiss();

    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void requestForPermission( ){
        if(accountInterface.hasPermission(Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String[] permissions={Manifest.permission.READ_MEDIA_IMAGES};

            accountInterface.requestPermission(permissions,request_code);
        }
    }
    public void checkForPermission(int request,int[] grants){
        if(request == request_code &&grants.length>0 &&grants[0] == PackageManager.PERMISSION_GRANTED) openGallery();
    }
    private void openGallery() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        accountInterface.getLauncher().launch(Intent.createChooser(intent,"Select Picture"));
    }
    public void getData(Intent data,Context context){
        Dialog dialog=LoginPresenter.dialog(context);
        dialog.show();
        if (data != null) {
            Uri selectedImageUri = data.getData();
            RequestBody requestUserId= RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(MyApplication.user.getUserId()));
            File file=new File(RealPathUtil.getRealPath(context,selectedImageUri));
            RequestBody requestImage= RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part multipartBody=MultipartBody.Part.createFormData("avatar",file.getName(),requestImage);
            ApiService.apiService.installAvatar(requestUserId,multipartBody).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    dialog.dismiss();
                    accountInterface.resetAvatar(response.body().getAvatar());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }
    public void onStartChangeAc(Context context){
        Intent intent=new Intent(context,ChangeActivity.class);
        context.startActivity(intent);
    }
    public void onStartName(Context context){
        Intent intent=new Intent(context, EmptyActivity.class);
        intent.putExtra(MyApplication.ACTION,MyApplication.user.getFullName());
        context.startActivity(intent);
    }
}
