package com.example.dictionary.Activity.Adapter.AboutSongAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.LaterPresenter;
import com.example.dictionary.Activity.View.Activity.RecentActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

public class LaterAdapter extends RecyclerView.Adapter<LaterAdapter.ViewHolder>{
    Context context;
    ArrayList<Later> list;
    public LaterAdapter(Context context, ArrayList<Later> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public LaterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.later_layout,parent,false);
        return new LaterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaterAdapter.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
        holder.imageView.setImageResource(list.get(position).getIcon());
        holder.itemView.setOnClickListener(v->{
            holder.laterPresenter.startActivity(position,context);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LaterPresenter laterPresenter=new LaterPresenter();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
