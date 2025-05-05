package com.example.dictionary.Activity.Adapter.AboutArtistAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.AboutArtistPresenter;
import com.example.dictionary.Activity.View.Activity.ArtistActivity;
import com.example.dictionary.R;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    Context context;
    ArrayList<Artist> list;
    AboutArtistPresenter aboutArtistPresenter;
    public ArtistAdapter(Context context, ArrayList<Artist> list){
        this.context=context;
        this.list=list;
        aboutArtistPresenter=new AboutArtistPresenter();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.artist_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(v->{
            aboutArtistPresenter.startActivity(context,list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.name);
        }
    }
}
