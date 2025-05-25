package com.example.dictionary.Activity.LoginActivity;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.DataManager;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.example.dictionary.Activity.RegisterActivity.RegisterActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    LoginInterface loginInterface;
    Dialog dialog;
    String phoneNumber;
    public LoginPresenter(LoginInterface loginInterface){
        this.loginInterface=loginInterface;
    }
    public void login(String username,String password,Context context){
        Dialog dialog=dialog(context);
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        if(username.isEmpty()||password.isEmpty()){
            loginInterface.showMissing("Bạn cần nhập đầy đủ thông tin");
            return;
        }
        loginInterface.showProcess(dialog);
        ApiService.apiService.postLogin(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getMessage().equals("Không tồn tại tài khoản")){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    loginInterface.cancelProcess(dialog);
                    loginInterface.showErrorUsername(response.body().getMessage());
                    return;
                }
                if(response.body().getMessage().equals("Mật khẩu không đúng")) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    loginInterface.cancelProcess(dialog);
                    loginInterface.showErrorPassword(View.VISIBLE);
                    return;
                }try {

                    ApiService.apiService.getUser(username).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            MyApplication.user=response.body();
                            ApiService.apiService.getPlaylists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Playlist>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Playlist>> call, Response<ArrayList<Playlist>> response) {
                                    MyApplication.playlists.clear();
                                    MyApplication.playlists.add(new Playlist(R.drawable.plus,"Tạo Playlist"));
                                    MyApplication.playlists.addAll(response.body());
                                    if(response.body() != null){
                                        for(Playlist playlist:response.body()){
                                            if(playlist.getImage().isEmpty()) playlist.setIcon(R.drawable.playlists);
                                        }
                                    }
                                    ApiService.apiService.getFavouriteSongs(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Song>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                                            MyApplication.FavouriteSongs.clear();
                                            MyApplication.FavouriteSongs=response.body();
                                            MyApplication.Love.setCount(MyApplication.FavouriteSongs.size());
                                            ApiService.apiService.getFavouriteArtists(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Artist>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<Artist>> call, Response<ArrayList<Artist>> response) {
                                                    MyApplication.FavouriteArtists.clear();
                                                    MyApplication.FavouriteArtists=response.body();
                                                    MyApplication.Artists.setCount(MyApplication.FavouriteArtists.size());
                                                    ApiService.apiService.getFavouriteAlbums(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Album>>() {
                                                        @Override
                                                        public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                                                            MyApplication.FavouriteAlbums.clear();
                                                            MyApplication.FavouriteAlbums=response.body();
                                                            ApiService.apiService.getDownloaded(MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Song>>() {
                                                                @Override
                                                                public void onResponse(Call<ArrayList<Song>> call, Response<ArrayList<Song>> response) {
                                                                    MyApplication.d=response.body();
                                                                    MyApplication.SongDownloaded.setCount(response.body().size());
                                                                    loginInterface.cancelProcess(dialog);
                                                                    Intent intent=new Intent(context, MainActivity.class);
                                                                    context.startActivity(intent);
                                                                    loginInterface.finishActivity();
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                                                                }
                                                            });

                                                        }

                                                        @Override
                                                        public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<Artist>> call, Throwable t) {

                                                }
                                            });

                                        }
                                        @Override
                                        public void onFailure(Call<ArrayList<Song>> call, Throwable t) {

                                        }
                                    });

                                }
                                @Override
                                public void onFailure(Call<ArrayList<Playlist>> call, Throwable t) {
                                }
                            });
                            DataManager.getInstance().setLoginState(true);
                            DataManager.getInstance().saveUser(response.body());
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
    public void forgotPass(Context context,String username){
        Dialog dialog1=dialog(context);
        loginInterface.showProcess(dialog1);
        ApiService.apiService.getNumberOnUsername(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loginInterface.cancelProcess(dialog1);
                dialog=dialog2(context);
                assert response.body() != null;
                loginInterface.showDialog(dialog,response.body().getPhoneNumber());
                phoneNumber=response.body().getPhoneNumber();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }
    public String dealPhoneNumber(String phone){
        return phone.replace(phone.substring(phone.length()-3,phone.length()),"***");
    }
    public static Dialog dialog(Context context){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.progress_dialog);
        Window window=dialog.getWindow();
        if(window == null){
            return null;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams win=window.getAttributes();
        win.gravity= Gravity.CENTER;
        window.setAttributes(win);
        dialog.setCancelable(false);
        return dialog;
    }
    private Dialog dialog2(Context context){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.verify_dialog);
        Window window=dialog.getWindow();
        if(window == null){
            return null;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams win=window.getAttributes();
        win.gravity= Gravity.CENTER;
        window.setAttributes(win);
        return dialog;
    }
    public void retrievePassword(Context context,String username){
        ApiService.apiService.getPassword(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Notification notification=new Notification.Builder(context, MyApplication.CHANNEL_ID)
                            .setContentTitle("Mật khẩu mới là :")
                            .setContentText(response.body().getPassword())
                            .setSmallIcon(R.drawable.img)
                            .build();
                    if(notificationManager != null) notificationManager.notify(2,notification);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    public void textChange(){
        loginInterface.showDialogError(View.GONE);
    }
    public void textChange2(){
        loginInterface.showErrorPassword2(View.GONE);
    }
    public void verifyCode(String username,String code,Context context,Dialog dialog){
        Dialog dialog1=dialog(dialog.getContext());
        dialog1.show();
        if(code.equals(phoneNumber.substring(phoneNumber.length()-3))){
            retrievePassword(context,username);
            dialog1.dismiss();
            loginInterface.cancelDialog(dialog);
        }else{
            dialog1.dismiss();
            loginInterface.showDialogError(View.VISIBLE);
        }
    }
    public void register(ActivityResultLauncher<Intent> launcher,Context context){
        Intent intent=new Intent(context, RegisterActivity.class);
        launcher.launch(intent);
    }
    public void handleResult(Intent data){
        assert data != null;
        String username=data.getStringExtra(MyApplication.USER);
        String password=data.getStringExtra(MyApplication.DATA);
        loginInterface.showResult(username,password);
    }
}
