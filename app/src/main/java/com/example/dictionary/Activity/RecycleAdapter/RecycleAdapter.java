package com.example.dictionary.Activity.RecycleAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Interface.View.Finisher;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

import java.util.ArrayList;
//mode 0 là song bình thường
//mode 1 là là danh sách phát tự động
//mode 2 là đã chọn cho trình phát nhạc
//mode 3 là song trong history dialog
//mode 4 là thêm vào playlist

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private ArrayList<Song> list;
    private final Context context;
    private final int mode;
    private final ItemClickListener listener;
    private final Finisher finisher;
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(Song song){
        int index=-1;
        for(int i=0;i<this.list.size();i++){
            if(song.getId()==this.list.get(i).getId()){
                index=i;
                break;
            }
        }
        if (index != -1) {
            this.list.remove(index);
            notifyItemRemoved(index);
        }
    }
    public void reset(ArrayList<Song> songs){
        this.list=songs;
        notifyDataSetChanged();
    }
    public RecycleAdapter(ArrayList<Song> list, Context context,int mode,Finisher finisher,ItemClickListener itemClickListener){
        this.list=list;
        this.listener=itemClickListener;
        this.mode=mode;
        this.context=context;
        this.finisher=finisher;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.song_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclePresenter.onInitMode(mode,position);
        holder.recyclePresenter.onInit(list.get(position),context);
        holder.add.setOnClickListener(view -> {
            holder.recyclePresenter.sendBroadcast(context,this,list.get(position));
        });
        holder.more.setOnClickListener(view -> {
            listener.onClickListener(list.get(position));
        });
        holder.itemView.setOnClickListener(v->{
            RecyclePresenter.sendBroadcast2(context,finisher,list.get(position));
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecycleInterface {
        TextView song,singers;
        Animation animation;
        RecyclePresenter recyclePresenter=new RecyclePresenter(this);
        ImageView imageView,more,add,like,on_list,cd;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animation=AnimationUtils.loadAnimation(itemView.getContext(),R.anim.rotate);
            imageView=itemView.findViewById(R.id.image);
            more=itemView.findViewById(R.id.more);
            cd=itemView.findViewById(R.id.cd);
            cd.setAnimation(animation);
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
        public void onInitMode(int moreAction, int addAction, int listAction,int cdAction,float scale) {
            add.setVisibility(addAction);
            more.setVisibility(moreAction);
            on_list.setVisibility(listAction);
            cd.setVisibility(cdAction);
            itemView.setScaleY(scale);
            itemView.setScaleX(scale);
            itemView.setAlpha(0f);
            itemView.animate().alpha(1f).setDuration(600).start();
        }

        @Override
        public void onInit(String singer_name,String song_name,String image) {
            singers.setText(singer_name);
            song.setText(song_name);
            recyclePresenter.loadImage(itemView.getContext(),image);
        }

        @Override
        public void loadImage(String image) {
            Glide.with(itemView.getContext())
                    .load(image)
                    .into(imageView);
        }

        @Override
        public void onInitOff(String singer_name, String song_name, String image) {
            singers.setText(singer_name);
            song.setText(song_name);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(Uri.parse(image));
            itemView.setEnabled(false);
            more.setEnabled(false);
            itemView.setAlpha(0.5f);
        }

        @Override
        public void onInitOffButDownloaded(com.example.dictionary.Activity.RoomDataBase.Entity.Song downloadedsong) {
            itemView.setEnabled(true);
            itemView.setAlpha(1f);
            singers.setText(downloadedsong.getSinger());
            song.setText(downloadedsong.getName());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageURI(Uri.parse(downloadedsong.getImage()));
            itemView.setOnClickListener(v->{
                recyclePresenter.startMusic(downloadedsong, itemView.getContext());
            });
        }
    }
}
