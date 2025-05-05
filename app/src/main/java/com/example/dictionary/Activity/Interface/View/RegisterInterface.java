package com.example.dictionary.Activity.Interface.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import java.util.Set;

public interface RegisterInterface {
    void showDialog(Dialog dialog);
    void register(String s);
    void showError();
    void showLack();
    void showExisted();
    void showFavouriteTypes(String s);
    void finishActivity(Intent intent);
}
