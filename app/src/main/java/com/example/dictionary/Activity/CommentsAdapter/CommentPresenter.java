package com.example.dictionary.Activity.CommentsAdapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.CommentFragment.CommentInterface;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.Model.Comment;
import com.example.dictionary.Activity.Test;
import com.example.dictionary.R;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentPresenter {
    CommentsInterface commentsInterface;
    CommentsAdapter commentsAdapter;
    LinearLayoutManager layoutManager;
    public CommentPresenter(CommentsInterface commentsInterface) {
        this.commentsInterface = commentsInterface;
    }
    public void onInit(Comment comment,boolean isChild,Context context,CommentInterface commentInterface) {
        if(isChild){
            commentsInterface.isChild();
        }
        ArrayList<Comment> comments=new ArrayList<>();
        commentsAdapter=new CommentsAdapter(comments, context,true,commentInterface);
        layoutManager=new LinearLayoutManager(context);
        commentsInterface.onMoreComments(commentsAdapter,layoutManager);
        ApiService.apiService.getCommentForSong(comment.getSong_id(),comment.getId(),MyApplication.user.getUserId())
                .enqueue(new Callback<ArrayList<Comment>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                        String time= comment.getTime().substring(0, comment.getTime().lastIndexOf("T"));
                        commentsInterface.onInit(comment.getAvatar(),comment.getFullName(),
                                time,comment.getComment(),setSl(comment.getLoved(),-1),
                                comment.getChildren()>0? View.VISIBLE:View.GONE
                        ,comment.getIsloved() >-1? R.drawable.heart:R.drawable.like);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

                    }
                });

    }
    public void onMoreComments(Comment comment,Context context,CommentInterface commentInterface){
        ApiService.apiService.getCommentForSong(comment.getSong_id(), comment.getId(),MyApplication.user.getUserId())
                .enqueue(new Callback<ArrayList<Comment>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                        commentsAdapter.setData(response.body());
                        commentsInterface.onHideText();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

                    }
                });
    }
    public void onResponse(CommentInterface commentInterface,Comment comment){
        commentInterface.focusEditText();
        commentInterface.onResponse(comment,commentsAdapter,View.VISIBLE);
    }
    private String setSl(float sl,int a){
        String[] prefix={"K","M","T"};
        if(sl /1000 >1) return setSl(sl/1000,a+1);
        if(a == -1) return (int)sl+"";
        if(sl/100>0) return sl+prefix[a];
        else return (int) sl+prefix[a];
    }
    public void onClickLike(Comment comment,Context context){
        String s=setSl(comment.getIsloved()>-1?comment.getChildren()-1:comment.getLoved()+1,-1);
        comment.setChildren(comment.getIsloved()>-1?comment.getChildren()-1:comment.getLoved()+1);
        commentsInterface.onLike(comment.getIsloved()>-1?R.drawable.like:R.drawable.heart,s);
        ApiService.apiService.postLikeComment(MyApplication.user.getUserId(),comment.getId(),comment.getIsloved()>-1?0:1).enqueue(
                new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        comment.setIsloved(comment.getIsloved()>-1?-1:0);

                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Toast.makeText(context,"mất kết nối mạng",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
