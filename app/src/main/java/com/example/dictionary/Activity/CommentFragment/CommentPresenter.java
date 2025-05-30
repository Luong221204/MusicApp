package com.example.dictionary.Activity.CommentFragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.CommentsAdapter.CommentsAdapter;
import com.example.dictionary.Activity.Model.Comment;
import com.example.dictionary.Activity.Test;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentPresenter {
    CommentInterface commentInterface;
    CommentsAdapter commentsAdapter;
    LinearLayoutManager layoutManager;
    int song_id=-1;
    public CommentPresenter(CommentInterface commentInterface) {
        this.commentInterface = commentInterface;
        song_id=MyApplication.song.getId();
    }
    public void onComments(Context context){
        ApiService.apiService.getCommentForSong(song_id,0,MyApplication.user.getUserId()).enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if(response.body() != null){
                    commentsAdapter=new CommentsAdapter(response.body(), context,false,commentInterface);
                    layoutManager=new LinearLayoutManager(context);
                    if(response.body().size()>0){
                        commentInterface.onComments(commentsAdapter,layoutManager);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
            }
        });
    }
    public void onTextChanged(CharSequence charSequence){
        if(charSequence.length()>0) {
            commentInterface.onTextChanged(330,View.VISIBLE);
        }
        else{
            commentInterface.onTextChanged(370,View.GONE);
        }
    }
    public void sendComment(Comment parent,CommentsAdapter commentsAdapterFrom,String text){
        if(parent == null && commentsAdapterFrom ==null){
            Comment comment=new Comment();
            comment.setParent_id(0);
            comment.setUserId(MyApplication.user.getUserId());
            comment.setComment(text);
            comment.setSong_id(song_id);
            commentInterface.onComments(commentsAdapter,layoutManager);
            ApiService.apiService.postComment(comment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if(response.isSuccessful() && response.body() != null){
                        commentsAdapter.upDate(response.body());
                    }else if(response.isSuccessful() && response.body().getMessage() != null){
                        commentInterface.onError(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    commentInterface.onError("Không có mạng");

                }
            });
        }else if(parent != null && commentsAdapterFrom !=null) {
            Comment comment=new Comment();
            comment.setParent_id(parent.getId());
            comment.setUserId(MyApplication.user.getUserId());
            comment.setComment(text);
            comment.setSong_id(song_id);
            ApiService.apiService.postComment(comment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if(response.isSuccessful() && response.body() != null){
                        commentsAdapterFrom.upDate(response.body());
                    }else if(response.isSuccessful() && response.body().getMessage() != null){
                        commentInterface.onError(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    commentInterface.onError("Không có mạng");

                }
            });
        }
        commentInterface.hideKeyboard();
    }
    public void onResponseToSb(Comment comment){

    }
    public void onCloseBoard(){
        commentInterface.onCloseBoard(View.GONE);
    }

}
