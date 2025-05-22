package com.example.dictionary.Activity.ContentAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.AlbumAdapter.AlbumAdapter;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Listener.ItemContentClickListener;
import com.example.dictionary.Activity.Model.Behalf;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Behalf> list;
    ItemContentClickListener itemContentClickListener;
    ItemClickListener itemClickListener;
    public ContentAdapter(Context outStandingFragment, ArrayList<Behalf>list,ItemClickListener itemClickListener){
        this.list=list;
        this.context=outStandingFragment;
        this.itemClickListener=itemClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if(viewType==0){
            View view=inflater.inflate(R.layout.song_layout2,parent,false);
            return new NormalHolder(view);
        }else{
            View view=inflater.inflate(R.layout.out_standing_artist,parent,false);
            return new LargeHolder(view,context);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(holder instanceof LargeHolder){
            ((LargeHolder) holder).largeHolderPresenter.onInit(list.get(position),context);
            ((LargeHolder) holder).relativeLayout1.setOnClickListener(v->{((LargeHolder) holder).largeHolderPresenter.onTouch(list.get(position),context);});
        }else{
            ((NormalHolder) holder).normalHolderPresenter.onInit(list.get(position));
            ((NormalHolder) holder).itemView.setOnClickListener(v->
                    ((NormalHolder) holder).normalHolderPresenter.onTouch(list.get(position), context,null)
            );
            ((NormalHolder) holder).more.setOnClickListener(v-> {
                itemClickListener.onClickListener((Song) list.get(position));
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LargeHolder extends RecyclerView.ViewHolder implements com.example.dictionary.Activity.ContentAdapter.LargeHolder {
        RecyclerView recyclerView;
        CircleImageView imageView;
        LargeHolderPresenter largeHolderPresenter=new LargeHolderPresenter(this);
        TextView name,for_u,alias;
        RelativeLayout relativeLayout,relativeLayout1;
        public LargeHolder(@NonNull View itemView,Context context) {
            super(itemView);
            imageView=itemView.findViewById(R.id.artist);
            name=itemView.findViewById(R.id.name);
            for_u=itemView.findViewById(R.id.for_u);
            alias=itemView.findViewById(R.id.alias);
            relativeLayout=itemView.findViewById(R.id.option);
            relativeLayout1=itemView.findViewById(R.id.relative1);
            recyclerView=itemView.findViewById(R.id.album);
        }

        @Override
        public void onInit(String image, String song_name, String for_u_text, int action,String entitle) {
            Glide.with(itemView.getContext()).load(image).into(imageView);
            name.setText(song_name);
            for_u.setText(for_u_text);
            relativeLayout.setVisibility(action);
            alias.setText(entitle);
            itemView.setAlpha(0f);
            itemView.animate().alpha(1f).setDuration(600).start();
        }

        @Override
        public void onRecycleView(AlbumAdapter albumAdapter,LinearLayoutManager layoutManager) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(albumAdapter);
        }

        @Override
        public void onInitOff(String image, String song_name, String for_u_text, int action,String entitle) {
            imageView.setImageURI(Uri.parse(image));
            name.setText(song_name);
            for_u.setText(for_u_text);
            relativeLayout.setVisibility(action);
            alias.setText(entitle);
            itemView.setAlpha(0f);
            itemView.animate().alpha(0.3f).setDuration(600).start();
            itemView.setEnabled(false);
        }
    }
    public static class NormalHolder extends  RecyclerView.ViewHolder implements com.example.dictionary.Activity.ContentAdapter.NormalHolder, ItemClickListener {
        TextView name,singers;
        ImageView imageView,more;
        NormalHolderPresenter normalHolderPresenter=new NormalHolderPresenter(this);
        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            more=itemView.findViewById(R.id.more);
            name=itemView.findViewById(R.id.name);
            singers=itemView.findViewById(R.id.singers);
            imageView=itemView.findViewById(R.id.image);
        }
        @Override
        public void onInit(String image, String name_text, String singer) {
            name.setText(name_text);
            singers.setText(singer);
            Glide.with(itemView.getContext()).load(image).into(imageView);
        }

        @Override
        public void onClickListener(Song song) {

        }
    }
}
