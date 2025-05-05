package com.example.dictionary.Activity.Adapter.FormAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Interface.Adapter.FavouriteInterface;
import com.example.dictionary.Activity.Model.Later;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.Set;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    ArrayList<Later> types;
    Context context;
    Set<String> strings;
    public FavouriteAdapter(ArrayList<Later> types, Context context, Set<String> strings){
        this.types=types;
        this.context=context;
        this.strings=strings;
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_favourite,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.checkBox.setChecked(holder.favouriteAdapter.init(types.get(position).getName(),strings));
        holder.textView.setText(types.get(position).getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.favouriteAdapter.setFavouriteTypes(isChecked,strings,types.get(position).getName());
            };
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements FavouriteInterface {
        TextView textView;
        CheckBox checkBox;
        com.example.dictionary.Activity.Presenter.AdapterPresenter.FavouriteAdapter favouriteAdapter=new com.example.dictionary.Activity.Presenter.AdapterPresenter.FavouriteAdapter(this);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.type);
            checkBox=itemView.findViewById(R.id.state);

        }
    }
}
