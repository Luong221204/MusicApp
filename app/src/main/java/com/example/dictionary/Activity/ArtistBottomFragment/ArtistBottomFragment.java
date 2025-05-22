package com.example.dictionary.Activity.ArtistBottomFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dictionary.Activity.ArtistBottomAdapter.ArtistBottomAdapter;
import com.example.dictionary.Activity.Model.Artist;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistBottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistBottomFragment extends BottomSheetDialogFragment implements ArtistBottomFragmentInterface {
    ArtistBottomPresenter artistBottomPresenter=new ArtistBottomPresenter(this);
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_artist_bottom, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        artistBottomPresenter.onInit(getArguments(),getContext());
        return view;
    }
    @Override
    public void onInit( ArtistBottomAdapter optionAdapter,LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(optionAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}