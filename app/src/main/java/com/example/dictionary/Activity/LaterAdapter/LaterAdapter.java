package com.example.dictionary.Activity.LaterAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.Finisher;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.DataAdapter.DatabasePresenter;
import com.example.dictionary.Activity.RecycleAdapter.RecyclePresenter;
import com.example.dictionary.Activity.RoomDataBase.Database.MyDatabase;
import com.example.dictionary.R;

import java.util.ArrayList;

public class LaterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    private final int TypeIcon=1;
    private final int SONG=2;
    ArrayList<Object> objects;
    public LaterAdapter(Context context, ArrayList<Later> list, ArrayList<Song> songOff){
        this.context=context;
        this.objects=new ArrayList<>();
        this.objects.addAll(list);
        if(songOff != null)  this.objects.addAll(songOff);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.later_layout,parent,false);
        if(viewType == TypeIcon) return new LaterAdapter.ViewHolder(view);
        else return new LaterAdapter.SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LaterAdapter.ViewHolder){
            ((ViewHolder) holder).laterPresenter.onInit((Later) objects.get(position));
            holder.itemView.setOnClickListener(v->{
                ArrayList<Song> arrayList= (ArrayList<Song>) MyDatabase.getInstance(context).userDAO().getRecentlySongs(MyApplication.user.getUserId());
                ((ViewHolder) holder).laterPresenter.startActivity(position,context);
            });
        }else if(holder instanceof LaterAdapter.SongViewHolder){
            ((SongViewHolder) holder).laterSongPresenter.onInit(objects.get(position),context);
            holder.itemView.setOnClickListener(v->{
                RecyclePresenter.sendBroadcast2(context, (Finisher) null, (Song) objects.get(position));
            });
        }
    }



    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(objects.get(position) instanceof Later) return TypeIcon;
        else return SONG;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements LaterIconInterface {
        ImageView imageView;
        TextView textView;
        public LaterPresenter laterPresenter=new LaterPresenter(this);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }
        @Override
        public void onInit(int icon, String name) {
            imageView.setImageResource(icon);
            textView.setText(name);
        }
    }
    public static class SongViewHolder extends RecyclerView.ViewHolder implements LaterSongInterface {
        ImageView imageView;
        TextView textView;
        LaterSongPresenter laterSongPresenter=new LaterSongPresenter(this);
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }

        @Override
        public void onInit(String source, String name) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(Uri.parse(source));
            itemView.setAlpha(0f);
            itemView.animate().alpha(1f).setDuration(600).start();
            textView.setText(name);
        }

        @Override
        public void onInitOff(String source, String name) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(Uri.parse(source));
            textView.setText(name);
            itemView.setAlpha(0.3f);
            itemView.setEnabled(false);
        }

        @Override
        public void onInitOffButDownloaded(com.example.dictionary.Activity.RoomDataBase.Entity.Song song) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(Uri.parse(song.getImage()));
            itemView.setAlpha(0f);
            itemView.animate().alpha(1f).setDuration(600).start();
            textView.setText(song.getName());
            itemView.setOnClickListener(v->{
                DatabasePresenter.sendBroadcast2(itemView.getContext(),null,song);;});
        }
    }
}
