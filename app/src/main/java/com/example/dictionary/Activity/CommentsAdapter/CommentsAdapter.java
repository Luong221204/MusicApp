package com.example.dictionary.Activity.CommentsAdapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.CommentFragment.CommentInterface;
import com.example.dictionary.Activity.LoginActivity.LoginPresenter;
import com.example.dictionary.Activity.Model.Comment;
import com.example.dictionary.R;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    ArrayList<Comment> comments;
    Context context;
    boolean isChild;
    CommentInterface commentInterface;
    public CommentsAdapter(ArrayList<Comment> comments, Context context,boolean isChild,CommentInterface commentInterface) {
        this.comments = comments;
        this.context = context;
        this.isChild=isChild;
        this.commentInterface=commentInterface;
    }
    public void setData(ArrayList<Comment> comments){
        if(this.comments == null) {
            this.comments=new ArrayList<>(comments);
            notifyDataSetChanged();
            return;
        }
        this.comments.addAll(comments);
        Set<Comment>commentSet=new HashSet<>(this.comments);
        this.comments=new ArrayList<>(commentSet);
        notifyDataSetChanged();


    }
    public void upDate(Comment comment){
        if(this.comments == null) this.comments=new ArrayList<>();
        comments.add(0,comment);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commentPresenter.onInit(comments.get(position),isChild,holder.itemView.getContext(),commentInterface);
        holder.more.setOnClickListener(v->{
            holder.commentPresenter.onMoreComments(comments.get(position),context,commentInterface);
        });
        holder.response.setOnClickListener(v->{
            holder.commentPresenter.onResponse(commentInterface,comments.get(position));
        });
        holder.like.setOnClickListener(v->{
            holder.commentPresenter.onClickLike(comments.get(position),context);
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements CommentsInterface{
        CircleImageView image;
        TextView name,comment,response,more,date,sl;
        ImageView like;
        RelativeLayout relativeLayout,relativeLayout2,relativeLayout1;
        RecyclerView recyclerView;
        CommentPresenter commentPresenter=new CommentPresenter(this);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            comment = itemView.findViewById(R.id.comment);
            response = itemView.findViewById(R.id.response);
            more = itemView.findViewById(R.id.more);
            like = itemView.findViewById(R.id.like);
            date = itemView.findViewById(R.id.date);
            sl = itemView.findViewById(R.id.sl);
            recyclerView = itemView.findViewById(R.id.recycle);
            relativeLayout = itemView.findViewById(R.id.relative3);
            relativeLayout2 = itemView.findViewById(R.id.relative2);
            relativeLayout1 = itemView.findViewById(R.id.relative1);


        }

        @Override
        public void onInit(String i, String n, String d, String c,String s, int action,int love) {
            itemView.setAlpha(0);
            itemView.animate().alpha(1f).setDuration(300).start();
            Glide.with(itemView.getContext()).load(i).into(image);
            name.setText(n);
            date.setText(d);
            sl.setText(s);
            comment.setText(c);
            like.setImageResource(love);
            more.setVisibility(action);
        }

        @Override
        public void onMoreComments(CommentsAdapter commentsAdapter, LinearLayoutManager layoutManager) {
            recyclerView.setAdapter(commentsAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }

        @Override
        public void onHideText() {
            more.setAlpha(1f);
            more.animate().alpha(0).setDuration(300).start();
            more.setVisibility(View.GONE);
        }

        @Override
        public void isChild() {
            ViewGroup.LayoutParams v=image.getLayoutParams();
            v.height=60;
            v.width=60;
            image.setLayoutParams(v);
            RelativeLayout.LayoutParams h= (RelativeLayout.LayoutParams) relativeLayout1.getLayoutParams();
            h.topMargin=dpToPx(5,itemView.getContext());
            relativeLayout1.setLayoutParams(h);
            RelativeLayout.LayoutParams h1= (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams();
            h1.setMarginStart(dpToPx(50,itemView.getContext()));
            h1.topMargin=dpToPx(10, itemView.getContext());
            relativeLayout2.setLayoutParams(h1);
            RelativeLayout.LayoutParams h2= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
            h2.setMarginStart(dpToPx(50,itemView.getContext()));
            relativeLayout.setLayoutParams(h2);
        }

        @Override
        public void onLike(int source,String s) {
            like.setImageResource(source);
            sl.setText(s);
        }

        private int dpToPx(int dp, Context context) {
            return Math.round(dp * context.getResources().getDisplayMetrics().density);
        }
    }

}
