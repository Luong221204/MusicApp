package com.example.dictionary.Activity.View.NaviFragment.PagerFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.dictionary.Activity.Adapter.BottomSheet.BottomsAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToPlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToPlayListFragment extends BottomSheetDialogFragment {
    RecyclerView recyclerView;
    EditText editText;
    RelativeLayout relativeLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_to_play_list, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        relativeLayout=view.findViewById(R.id.bottom);
        assert getTag() != null;
        if(getTag().equals(MyApplication.SONG)) relativeLayout.setVisibility(View.GONE);
        BottomsAdapter playlistAdapter=new BottomsAdapter(requireActivity(),R.layout.add_playlist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setAdapter(playlistAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        editText=view.findViewById(R.id.search_text);

        return view;
    }

}