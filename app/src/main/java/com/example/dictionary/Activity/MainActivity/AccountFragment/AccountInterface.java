package com.example.dictionary.Activity.MainActivity.AccountFragment;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

public interface AccountInterface {
    void init(int source,String name);
    int hasPermission(String permission);
    void requestPermission(String [] permissions,int request_code);
    ActivityResultLauncher<Intent> getLauncher();
    void resetAvatar(String uri);
    void init2(String source,String name);}
