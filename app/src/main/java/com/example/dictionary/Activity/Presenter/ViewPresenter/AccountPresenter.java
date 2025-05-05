package com.example.dictionary.Activity.Presenter.ViewPresenter;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.AccountInterface;
import com.example.dictionary.R;

public class AccountPresenter {
    AccountInterface accountInterface;
    public AccountPresenter(AccountInterface accountInterface){
        this.accountInterface=accountInterface;
    }
    public void init(){
        if(MyApplication.user.getGender()==1){
            accountInterface.init(R.drawable.img_7,MyApplication.user.getFullName());
        }else{
            accountInterface.init(R.drawable.img_8,MyApplication.user.getFullName());

        }
    }
}
