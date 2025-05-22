package com.example.dictionary.Activity.SortFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Filter2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Filter2Fragment extends BottomSheetDialogFragment implements Filter2Interface {
    private RelativeLayout newest,oldest,up,down;
    private ImageView tick1,tick2,tick3,tick4;
    Filter2Presenter filterBottomPresenter=new Filter2Presenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter2, container, false);
        newest = view.findViewById(R.id.newest);
        oldest = view.findViewById(R.id.oldest);
        up = view.findViewById(R.id.up);
        down = view.findViewById(R.id.down);
        tick1 = view.findViewById(R.id.tick1);
        tick2 = view.findViewById(R.id.tick2);
        tick3 = view.findViewById(R.id.tick3);
        tick4 = view.findViewById(R.id.tick4);
        filterBottomPresenter.onInit(getArguments(), requireContext());
        newest.setOnClickListener(v->{filterBottomPresenter.onSortSongName(requireContext(), MyApplication.NEWEST);});
        oldest.setOnClickListener(v->{filterBottomPresenter.onSortSongName(requireContext(), MyApplication.OLDEST);});
        up.setOnClickListener(v->{filterBottomPresenter.onSortSongName(requireContext(), MyApplication.SONG_ASC);});
        down.setOnClickListener(v->{filterBottomPresenter.onSortSongName(requireContext(), MyApplication.ARTIST_ASC);});

        return view;
    }

    @Override
    public void onView(int action) {
        down.setVisibility(action);
    }
}