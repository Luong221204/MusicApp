package com.example.dictionary.Activity.AlbumAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Model.Album;
import com.example.dictionary.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Album> list;
    int mode;
    public AlbumAdapter(Context context, ArrayList<Album> list,int mode){
        this.context=context;
        this.list=list;
        this.mode=mode;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if(viewType == 0){
            View view=inflater.inflate(R.layout.album_layout,parent,false);
            return new ViewHolder(view,list);
        }
        else{
            View view=inflater.inflate(R.layout.end_layout,parent,false);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.topMargin = 130;
            view.setLayoutParams(params);
            return new ViewHolder2(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).albumAdapterPresenter.onInit(mode,position,list);
            ((ViewHolder)holder).itemView.setOnClickListener(v->{((ViewHolder) holder).albumAdapterPresenter.sendBroadcast(context,list,mode,position);});
        }else{
            ((ViewHolder2) holder).albumAdapterPresenter.onInit(mode,position,list);
            ((ViewHolder2)holder).itemView.setOnClickListener(v->{((ViewHolder2) holder).albumAdapterPresenter.sendBroadcast(context,list,mode,position);});
        }


    }

    @Override
    public int getItemCount() {
        if(mode == 1) return list.size()+1;
        else return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mode == 1){
            if(position == list.size()) return 1;
            else return 0;
        }
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AlbumAdapterInterface {
        ImageView imageView;
        TextView textView;
        AlbumAdapterPresenter albumAdapterPresenter=new AlbumAdapterPresenter(this);
        public ViewHolder(@NonNull View itemView,ArrayList<Album> albums) {
            super(itemView);
            imageView=itemView.findViewById(R.id.album);
            textView=itemView.findViewById(R.id.name);
        }

        @Override
        public void onInit(String name,String image) {
            textView.setText(name);
            Glide.with(itemView.getContext()).load(image).into(imageView);
            itemView.setAlpha(0);
            itemView.animate().alpha(1f).setDuration(600).start();
        }

        @Override
        public void onInitEnd(String name, int icon) {
            textView.setText(name);
            imageView.setImageResource(icon);
        }

        @Override
        public void onInitOff(String name, String image) {
            textView.setText(name);
            imageView.setImageURI(Uri.parse(image));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            itemView.setEnabled(false);
            itemView.setAlpha(0);
            itemView.animate().alpha(0.3f).setDuration(600).start();
        }
    }
    public static class ViewHolder2 extends RecyclerView.ViewHolder implements AlbumAdapterInterface{
        ImageView imageView;
        TextView textView;
        AlbumAdapterPresenter albumAdapterPresenter=new AlbumAdapterPresenter(this);
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.name);
        }

        @Override
        public void onInit(String name, String image) {
            textView.setText(name);
            Glide.with(itemView.getContext()).load(image).into(imageView);
        }

        @Override
        public void onInitEnd(String name, int icon) {
            textView.setText(name);
            imageView.setImageResource(icon);
        }

        @Override
        public void onInitOff(String name, String image) {

        }
    }
}

