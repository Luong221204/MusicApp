package com.example.dictionary.Activity.Adapter.AboutSongAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Interface.Adapter.DatabaseInterface;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.DatabasePresenter;
import com.example.dictionary.Activity.RoomDataBase.Entity.Song;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.ViewHolder> {
    Context context;
    int mode;
    ItemClickListener itemClickListener;
    ArrayList<Song> songs;
    ViewSongActivity viewSongActivity;
    public DatabaseAdapter(ItemClickListener itemClickListener,Context context,ArrayList<Song> songs, int mode,ViewSongActivity viewSongActivity){
        this.context=context;
        this.songs=songs;
        this.mode=mode;
        this.itemClickListener=itemClickListener;
        this.viewSongActivity=viewSongActivity;
    }
    public void updateData(ArrayList<Song> songs){
        this.songs=songs;
    }
    @NonNull
    @Override
    public DatabaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.song_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageURI(Uri.parse(songs.get(position).getImage()));
        holder.singers.setText(songs.get(position).getSinger());
        holder.song.setText(songs.get(position).getName());
        holder.databasePresenter.onInit(mode);
        holder.add.setOnClickListener(v -> {
            holder.databasePresenter.sendBroadcast(context,this,songs.get(position));

        });
        holder.itemView.setOnClickListener(v->{
            holder.databasePresenter.sendBroadcast2(context,viewSongActivity,songs.get(position));
        });
        holder.more.setOnClickListener(v->{
            itemClickListener.onClickListener(DatabasePresenter.convert(songs.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements DatabaseInterface {
        TextView song,singers;
        ImageView imageView,more,add,like,on_list,cd;
        View view;
        DatabasePresenter databasePresenter=new DatabasePresenter(this);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            more=itemView.findViewById(R.id.more);
            cd=itemView.findViewById(R.id.cd);
            add=itemView.findViewById(R.id.add_to_play);
            like=itemView.findViewById(R.id.like);
            on_list=itemView.findViewById(R.id.on_list);
            song=itemView.findViewById(R.id.name);
            song.post(()->{if(song.getPaint().measureText(song.getText().toString())>song.getWidth())
            {
                song.setSelected(true);
            }
            });
            singers=itemView.findViewById(R.id.singers);
            view=itemView;
        }

        @Override
        public void onView(int addAction, int moreAction, int listAction) {
            add.setVisibility(addAction);
            more.setVisibility(moreAction);
            on_list.setVisibility(listAction);
        }
    }
}
