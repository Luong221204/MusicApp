package com.example.dictionary.Activity.CommentFragment;

import android.content.Context;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.CommentsAdapter.CommentsAdapter;
import com.example.dictionary.Activity.Model.Comment;

public interface CommentInterface {
    void onComments(CommentsAdapter adapter, LinearLayoutManager layoutManager);
    void onTextChanged(int dimension,int action);
    void onResponse(Comment comment,CommentsAdapter commentsAdapter,int action);
    void hideKeyboard();
    void onError(String error);
    void onCloseBoard(int action);
    void focusEditText();
    void onAlreadySentComment();
}
