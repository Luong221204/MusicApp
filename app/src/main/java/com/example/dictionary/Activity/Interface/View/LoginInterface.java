package com.example.dictionary.Activity.Interface.View;

import android.app.Dialog;

public interface LoginInterface {
    void showMissing(String m);
    void showErrorUsername(String e);
    void showErrorPassword(int status);
    void showErrorPassword2(int status);
    void showResult(String username,String password);
    void finishActivity();
    void showProcess(Dialog dialog);
    void cancelProcess(Dialog dialog);
    void showDialog(Dialog dialog, String phoneNumber);
    void showDialogError(int status);
    void cancelDialog(Dialog dialog);
}
