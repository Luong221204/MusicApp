package com.example.dictionary.Activity.Adapter.FormAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Lib;
import com.example.dictionary.Activity.Presenter.AdapterPresenter.LibFavouritePresenter;
import com.example.dictionary.Activity.View.Activity.FavouriteArtistActivity;
import com.example.dictionary.Activity.View.Activity.LibActivity;
import com.example.dictionary.Activity.View.Activity.MainActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

public class LibFavouriteAdapter extends RecyclerView.Adapter<LibFavouriteAdapter.ViewHolder> {
    MainActivity mainActivity;
    ArrayList<Lib> arrayList;
    public LibFavouriteAdapter(MainActivity mainActivity,ArrayList<Lib> arrayList){
        this.arrayList=arrayList;
        this.mainActivity=mainActivity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.source_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint({"CommitTransaction", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(arrayList.get(position).getIcon());
        holder.text.setText(arrayList.get(position).getName());
        holder.cnt.setText(arrayList.get(position).getCount()+" ");
        holder.itemView.setOnClickListener(v -> {
          holder.libFavouritePresenter.startActivity(mainActivity,arrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView text,cnt;
        LibFavouritePresenter libFavouritePresenter=new LibFavouritePresenter();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            text=itemView.findViewById(R.id.text);
            cnt=itemView.findViewById(R.id.count);
        }
    }
}
