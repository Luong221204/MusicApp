package com.example.dictionary.Activity.RegisterActivity;

import android.app.Dialog;
import android.content.Intent;

public interface RegisterInterface {
    void showDialog(Dialog dialog);
    void register(String s);
    void showError();
    void showLack();
    void showExisted();
    void showFavouriteTypes(String s);
    void finishActivity(Intent intent);
}
