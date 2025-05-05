package com.example.dictionary.Activity.Adapter.FormAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

import java.util.ArrayList;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ViewHolder> {
    ArrayList<String> list;
    private final Context mainActivity;
    public ResultSearchAdapter(ArrayList<String> list, Context activity){
        this.list=list;
        this.mainActivity=activity;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<String> update){
        this.list=update;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mainActivity);
        View view=layoutInflater.inflate(R.layout.result_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.name);
        }
    }
}
