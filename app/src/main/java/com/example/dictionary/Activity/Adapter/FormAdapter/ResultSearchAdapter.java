package com.example.dictionary.Activity.Adapter.FormAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.ResultAdapter.ResultPresenter;
import com.example.dictionary.Activity.SearchFragment.SearchFragmentInterface;
import com.example.dictionary.R;

import java.util.ArrayList;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ViewHolder> {
    ArrayList<Search> list;
    private final Context mainActivity;
    private final int indexed;
    private final SearchFragmentInterface searchFragmentInterface;
    public ResultSearchAdapter(ArrayList<Search> list, Context activity, int indexed, SearchFragmentInterface searchFragmentInterface){
        this.list=list;
        this.mainActivity=activity;
        this.indexed=indexed;
        this.searchFragmentInterface=searchFragmentInterface;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Search> update){
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
        holder.textView.setText(list.get(position).getSearch());
        holder.itemView.setOnClickListener(v->{
            holder.resultPresenter.onClick(mainActivity,position,indexed,list,searchFragmentInterface);

        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ResultPresenter resultPresenter=new ResultPresenter();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.name);
        }
    }
}
