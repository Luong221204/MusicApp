package com.example.dictionary.Activity.Adapter.AboutSongAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.dictionary.Activity.Interface.Adapter.RecycleInterface;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.RecyclePresenter;
import com.example.dictionary.Activity.View.Activity.ViewSongActivity;
import com.example.dictionary.R;

import java.util.ArrayList;
//mode 0 là song bình thường
//mode 1 là là danh sách phát tự động
//mode 2 là đã chọn cho trình phát nhạc
//mode 3 là song trong history dialog

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private ArrayList<Song> list;
    private final Context context;
    private final int mode;
    private final ItemClickListener listener;
    private final ViewSongActivity viewSongActivity;
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<Song> songs){
        this.list=songs;
        notifyDataSetChanged();
    }
    public RecycleAdapter(ArrayList<Song> list, Context activity,int mode,ViewSongActivity viewSongActivity,ItemClickListener itemClickListener){
        this.list=list;
        this.listener=itemClickListener;
        this.mode=mode;
        this.context=activity;
        this.viewSongActivity=viewSongActivity;
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
        holder.recyclePresenter.onInit(list.get(position));
        holder.add.setOnClickListener(view -> {
           holder.recyclePresenter.sendBroadcast(context,this,list.get(position));
        });
        holder.more.setOnClickListener(view -> {
            listener.onClickListener(list.get(position));
        });
        holder.itemView.setOnClickListener(v->{
           holder.recyclePresenter.sendBroadcast2(context,viewSongActivity,list.get(position));
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
        }

        @Override
        public void onInit(String singer_name,String song_name,String image) {
            singers.setText(singer_name);
            song.setText(song_name);
            Context context = itemView.getContext();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isDestroyed() && !activity.isFinishing()) {
                    Glide.with(context).load(image).into(imageView);
                }
            } else {
                Glide.with(context).load(image).into(imageView);
            }
        }
    }
}
