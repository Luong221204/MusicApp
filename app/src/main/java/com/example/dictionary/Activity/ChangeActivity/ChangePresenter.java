package com.example.dictionary.Activity.ChangeActivity;

import android.text.InputType;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.User;
import com.example.dictionary.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePresenter {
    ChangeInterface changeInterface;

    public ChangePresenter(ChangeInterface changeInterface) {
        this.changeInterface = changeInterface;
    }
    public void onClickOldEye(int inputType){
        if(inputType== (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)){
            changeInterface.onOldEyeChange(InputType.TYPE_CLASS_TEXT, R.drawable.close_eye);
        }else{
            changeInterface.onOldEyeChange((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD), R.drawable.eye);
        }
    }
    public void onClickNewEye(int inputType){
        if(inputType== (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)){
            changeInterface.onNewEyeChange(InputType.TYPE_CLASS_TEXT, R.drawable.close_eye);
        }else{
            changeInterface.onNewEyeChange((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD), R.drawable.eye);
        }
    }
    public void solveChangePassword(String old,String newP){
        if(old.equals(newP)){
            changeInterface.onToast("Mật khẩu mới không được giống mật khẩu cũ");
            return;
        }
        if(old.equals(MyApplication.user.getPassword())){
            ApiService.apiService.changePassword(MyApplication.user.getUserId(),newP).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful() && response.body() != null){
                        changeInterface.onToast(response.body().getMessage());
                        MyApplication.user.setPassword(newP);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    changeInterface.onToast("Không thanh công");

                }
            });
        }else{
            changeInterface.onToast("Mật khẩu cũ không chính xác");
        }
    }

}
