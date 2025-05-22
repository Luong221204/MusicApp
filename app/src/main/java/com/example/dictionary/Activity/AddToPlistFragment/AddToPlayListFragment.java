package com.example.dictionary.Activity.AddToPlistFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dictionary.Activity.BottomAdapter.BottomsAdapter;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToPlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToPlayListFragment extends BottomSheetDialogFragment implements AddToPlayListFragmentInterface {
    RecyclerView recyclerView;
    EditText editText;
    RelativeLayout relativeLayout;
    AddToPlayListFragmentPresenter addToPlayListFragmentPresenter=new AddToPlayListFragmentPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_to_play_list, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        relativeLayout=view.findViewById(R.id.bottom);
        editText=view.findViewById(R.id.search_text);
        assert getTag() != null;
        addToPlayListFragmentPresenter.onInit(getTag(),getContext(),getArguments());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addToPlayListFragmentPresenter.onTrack(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addToPlayListFragmentPresenter.onSearch(actionId,editText.getText().toString());
                return true;
            }
        });
        return view;
    }

    @Override
    public void onSearch() {

    }

    @Override
    public void onInit(int action) {
        relativeLayout.setVisibility(action);
    }

    @Override
    public void onRecycle(BottomsAdapter playlistAdapter, LinearLayoutManager linearLayoutManager) {
        recyclerView.setAdapter(playlistAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager im= (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

}