package com.example.dictionary.Activity.BottomAdapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Playlist;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int source,action;
    Context context;
    ArrayList<Playlist> playlists;
    Song song;
    public BottomsAdapter(Context context, int source, int action, Song song){
        this.context=context;
        this.source=source;
        this.action=action;
        playlists=MyApplication.playlists;
        this.song=song;
    }
    public void setData(ArrayList<Playlist> list){
        this.playlists=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(source,parent,false);
        if(source== R.layout.add_playlist){
            return new PlayListViewHolder(view,song,this);
        }else if(source==R.layout.bottom_artist_layout) return new ArtistViewHolder(view);
        else return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlayListViewHolder) holder).playlistPresenter.onInit(R.layout.add_playlist,position,playlists.get(position),action);
    }

    @Override
    public int getItemCount() {
        if(source== R.layout.add_playlist){
            return playlists.size();
        }else if(source==R.layout.bottom_artist_layout) return MyApplication.options.size();
        else return MyApplication.options.size();
    }

    public static class PlayListViewHolder extends RecyclerView.ViewHolder implements PlaylistInterface {
        public ImageView imageView;
        public TextView textView,user;
        public Song song;
        public  BottomsAdapter bottomsAdapter;
        public PlaylistPresenter playlistPresenter=new PlaylistPresenter(this);
        public PlayListViewHolder(@NonNull View itemView,Song song,BottomsAdapter bottomsAdapter) {
            super(itemView);
            user=itemView.findViewById(R.id.sl);
            this.bottomsAdapter=bottomsAdapter;
            imageView=itemView.findViewById(R.id.icon);
            textView=itemView.findViewById(R.id.name);
            this.song=song;
        }
        @Override
        public void onInitForDefault(Playlist playlist, ViewGroup.LayoutParams layoutParams,int i) {
            user.setVisibility(i);
            textView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(playlist.getIcon());
            textView.setText(playlist.getName());
            itemView.setOnClickListener(v->{
                playlistPresenter.showDialog(itemView.getContext());
            });
        }

        @Override
        public void showDialog(Dialog dialog) {
            EditText editText=dialog.findViewById(R.id.edit);
            Button buttonX=dialog.findViewById(R.id.exit);
            Button buttonA=dialog.findViewById(R.id.accept);
            buttonA.setOnClickListener(v -> {
                playlistPresenter.addPlaylist(editText.getText().toString(),dialog,bottomsAdapter);
            });
            buttonX.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        }

        @Override
        public void onInitForReal(Playlist playlist) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(itemView.getContext()).load(playlist.getImage()).into(imageView);
            textView.setText(playlist.getName());
            user.setText(MyApplication.user.getUsername());
            itemView.setOnClickListener(v->{playlistPresenter.onClickPlaylist(itemView.getContext(),playlist);
            });
        }

        @Override
        public void onInitForReal2(Playlist playlist) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(itemView.getContext()).load(playlist.getImage()).into(imageView);
            textView.setText(playlist.getName());
            user.setText(MyApplication.user.getUsername());
            itemView.setOnClickListener(v->{
                playlistPresenter.addToPlaylist(playlist,song);
            });
        }

        @Override
        public void showMessage(String message) {
            Toast.makeText(itemView.getContext(),message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showStatus(String status) {
            Toast.makeText(itemView.getContext(),status,Toast.LENGTH_SHORT).show();

        }

        @Override
        public TextView textView() {
            return textView;
        }
    }
    public static class ArtistViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView imageView;
        public TextView name,quantity;
        public CheckBox checkBox;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.sl);
            checkBox=itemView.findViewById(R.id.check);
        }
    }
    public static class TypeViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView name,quantity;
        public CheckBox checkBox;
        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.sl);
            checkBox=itemView.findViewById(R.id.check);
        }
    }
}
