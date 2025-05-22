package com.example.dictionary.Activity.BottomAdapter;

import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dictionary.Activity.Model.Playlist;

public interface PlaylistInterface {
    void onInitForDefault(Playlist playlist, ViewGroup.LayoutParams layoutParams , int isVisible);
    void showDialog(Dialog dialog);
    void onInitForReal(Playlist playlist);
    void onInitForReal2(Playlist playlist);
    void showMessage(String message);
    void showStatus(String status);
    TextView textView();
}
