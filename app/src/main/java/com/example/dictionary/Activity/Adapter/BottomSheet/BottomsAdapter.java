package com.example.dictionary.Activity.Adapter.BottomSheet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.View.Activity.PlaylistActivity;
import com.example.dictionary.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int source;
    Context context;
    public BottomsAdapter(Context context,int source){
        this.context=context;
        this.source=source;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(source,parent,false);
        if(source== R.layout.add_playlist){
            return new PlayListViewHolder(view);
        }else if(source==R.layout.bottom_artist_layout) return new ArtistViewHolder(view);
        else return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(source== R.layout.add_playlist){
            ((PlayListViewHolder) holder).imageView.setImageResource(MyApplication.playlists.get(position).getIcon());
            ((PlayListViewHolder) holder).textView.setText(MyApplication.playlists.get(position).getName());
            if(position==0){
                ((PlayListViewHolder) holder).itemView.setOnClickListener(v -> {showDialog();});
                return;
            }
            else{
                Intent intent1=new Intent(context, PlaylistActivity.class);
                intent1.putExtra(MyApplication.SONG,MyApplication.playlists.get(position).getName());
                context.startActivity(intent1);
            }
        }



    }

    @Override
    public int getItemCount() {
        if(source== R.layout.add_playlist){
            return MyApplication.playlists.size();
        }else if(source==R.layout.bottom_artist_layout) return MyApplication.options.size();
        else return MyApplication.options.size();
    }

    public static class PlayListViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.icon);
            textView=itemView.findViewById(R.id.name);
        }
    }
    public static class ArtistViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView imageView;
        public TextView name,quantity;
        public CheckBox checkBox;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.sl);
            checkBox=itemView.findViewById(R.id.check);
        }
    }
    public static class TypeViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView name,quantity;
        public CheckBox checkBox;
        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            quantity=itemView.findViewById(R.id.sl);
            checkBox=itemView.findViewById(R.id.check);
        }
    }
    public void showDialog(){
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        Window window=dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams win=window.getAttributes();//WinDowManager là của toàn bộ ứng dụng
        win.gravity= Gravity.CENTER;

        window.setAttributes(win);
        dialog.setCancelable(true);
        EditText editText=dialog.findViewById(R.id.edit);
        Button buttonX=dialog.findViewById(R.id.exit);
        Button buttonA=dialog.findViewById(R.id.accept);
        buttonA.setOnClickListener(v -> {
            Toast.makeText(context,"đã tạo",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        buttonX.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();

    }
}
