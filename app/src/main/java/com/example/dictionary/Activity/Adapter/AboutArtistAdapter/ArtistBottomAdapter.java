package com.example.dictionary.Activity.Adapter.AboutArtistAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.AboutArtistPresenter;
import com.example.dictionary.Activity.View.Activity.ArtistActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArtistBottomAdapter extends RecyclerView.Adapter<ArtistBottomAdapter.ViewHolder> {
    Context context;
    ArrayList<Artist> artists;
    AboutArtistPresenter aboutArtistPresenter;
    public ArtistBottomAdapter(Context context,ArrayList<Artist> artists){
        this.context=context;
        this.artists=artists;
        aboutArtistPresenter=new AboutArtistPresenter();
    }
    @NonNull
    @Override
    public ArtistBottomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.bottom_artist_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArtistBottomAdapter.ViewHolder holder, int position) {
        holder.name.setText(artists.get(position).getName());
        holder.checkBox.setVisibility(View.GONE);
        Glide.with(context).load(artists.get(position).getImage()).into(holder.image);
        holder.itemView.setOnClickListener(v -> {
           aboutArtistPresenter.startActivity(context,artists.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name,followers;
        CheckBox checkBox;
        ImageView next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            followers=itemView.findViewById(R.id.sl);
            checkBox=itemView.findViewById(R.id.check);
            next=image.findViewById(R.id.next);
        }
    }
}
