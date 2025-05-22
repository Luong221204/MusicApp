package com.example.dictionary.Activity.OptionAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Interface.Adapter.CLickBottom;
import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.ArtistBottomFragment.ArtistBottomFragment;
import com.example.dictionary.Activity.AddToPlistFragment.AddToPlayListFragment;
import com.example.dictionary.R;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    FragmentActivity context;
    ArrayList<Options> list;
    CLickBottom itemClickListener;
    Song song;
    public OptionAdapter(Activity context, ArrayList<Options> list, Song song,CLickBottom itemClickListener) {
        this.context = (FragmentActivity) context;
        this.list = list;
        this.song=song;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public OptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.option_layout,parent,false);
        return new ViewHolder(view,context,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.optionPresenter.onInit(list.get(position));
        holder.itemView.setOnClickListener(v->{
            holder.optionPresenter.onOptions(list.get(position),context,song);
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements OptionInterface {
        ImageView imageView;
        TextView textView;
        Activity activity;
        CLickBottom cLickBottom;
        OptionPresenter optionPresenter=new OptionPresenter(this);
        public ViewHolder(@NonNull View itemView,Activity mainActivity,CLickBottom cLickBottom) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.option);
            activity=mainActivity;
            this.cLickBottom=cLickBottom;
        }

        @Override
        public void onInit(float alpha, boolean enable,String name,int icon) {
            itemView.setAlpha(alpha);
            itemView.setEnabled(enable);
            textView.setText(name);
            imageView.setImageResource(icon);
        }

        @Override
        public void onAddToPlayListFragment(AddToPlayListFragment addToPlayListFragment) {
            cLickBottom.onClickBottom(addToPlayListFragment,null);
        }

        @Override
        public void onArtistBottomFragment(ArtistBottomFragment artistBottomFragment,Song song) {
            cLickBottom.onClickBottom(artistBottomFragment,song);
        }
    }
}
