package com.example.dictionary.Activity.View.NaviFragment.PagerFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Adapter.FormAdapter.OptionAdapter;
import com.example.dictionary.Activity.Interface.Adapter.CLickBottom;
import com.example.dictionary.Activity.Interface.View.BottomFragmentInterface;
import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Presenter.ViewPresenter.BottomFragmentPresenter;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomFragment extends BottomSheetDialogFragment  implements BottomFragmentInterface, CLickBottom {
    Song song1;
    public BottomFragmentPresenter bottomFragmentPresenter=new BottomFragmentPresenter(this);
    RecyclerView recyclerView;
    ImageView imageView;
    TextView name,singers;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.ISBACK.equals(intent.getAction())){
                bottomFragmentPresenter.onInitAgain(getContext());
            }else if(MyApplication.SUCCESSFULL.equals(intent.getAction())){
                bottomFragmentPresenter.reset(requireActivity());
            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onStart() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MyApplication.ISBACK);
        intentFilter.addAction(MyApplication.SUCCESSFULL);
        requireActivity().registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_NOT_EXPORTED);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bottom, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        imageView=view.findViewById(R.id.image);
        name=view.findViewById(R.id.name);
        singers=view.findViewById(R.id.singers);
        Bundle bundle=getArguments();
        bottomFragmentPresenter.onInit(bundle,requireActivity());
        bottomFragmentPresenter.onRecycleView(bundle,requireActivity());
        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onInit(String song_name, String singer, String image) {
        name.setText(song_name);
        singers.setText(singer);
        Glide.with(requireActivity()).load(image).into(imageView);
    }

    @Override
    public void onRecycleView(ArrayList<Options> options,Song song) {
        OptionAdapter optionAdapter=new OptionAdapter( requireActivity(), options,song,BottomFragment.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setAdapter(optionAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onInitOff(String song_name, String singer, String image) {
        name.setText(song_name);
        singers.setText(singer);
        imageView.setImageURI(Uri.parse(image));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClickBottom(BottomSheetDialogFragment bottomSheetDialogFragment,Song song) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(MyApplication.SONG,song);
        bottomSheetDialogFragment.setArguments(bundle);
        bottomSheetDialogFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.ACTION);
    }
}