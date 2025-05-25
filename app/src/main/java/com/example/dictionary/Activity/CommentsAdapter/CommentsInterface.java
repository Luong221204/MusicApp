package com.example.dictionary.Activity.CommentsAdapter;

import android.app.Dialog;

import androidx.recyclerview.widget.LinearLayoutManager;

public interface CommentsInterface {
    void onInit(String i, String n, String d, String c,String sl, int action,int love);
    void onMoreComments(CommentsAdapter commentsAdapter, LinearLayoutManager layoutManager);
    void onHideText();
    void isChild();
    void onLike(int source,String sl);
}
