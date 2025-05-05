package com.example.dictionary.Activity.Adapter.FormAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Adapter.AboutSongAdapter.RecycleAdapter;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    ArrayList<ArrayList<Song>> list;
    Context context;
    ItemClickListener itemClickListener;
    public RecentAdapter(Context mainActivity,ArrayList<ArrayList<Song>>list,ItemClickListener itemClickListener){
        this.list=list;
        this.itemClickListener=itemClickListener;
        this.context=mainActivity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecycleAdapter contentAdapter=new RecycleAdapter(list.get(position),context,0,null,itemClickListener);
            LinearLayoutManager layoutManager=new LinearLayoutManager(context);
            holder.recyclerView.setAdapter(contentAdapter);
            holder.recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recycle);
        }
    }

}
