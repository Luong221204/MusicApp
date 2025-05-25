package com.example.dictionary.Activity.CommentFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.CommentsAdapter.CommentsAdapter;
import com.example.dictionary.Activity.Model.Comment;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.checkerframework.checker.units.qual.C;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends BottomSheetDialogFragment implements CommentInterface{

    RecyclerView recyclerView;
    EditText editText;
    TextView name;
    ImageView send,close;
    RelativeLayout relativeLayout;
    CommentPresenter commentPresenter=new CommentPresenter(this);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_comment, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        editText=view.findViewById(R.id.comment);
        send=view.findViewById(R.id.send);
        close=view.findViewById(R.id.close);
        name=view.findViewById(R.id.name);
        relativeLayout=view.findViewById(R.id.response);
        commentPresenter.onComments(requireContext());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                commentPresenter.onTextChanged(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        close.setOnClickListener(v->{
            commentPresenter.onCloseBoard();
        });
        send.setOnClickListener(v->{
            commentPresenter.sendComment(null,null,editText.getText().toString());
        });
        return view;
    }

    @Override
    public void onComments(CommentsAdapter adapter, LinearLayoutManager layoutManager) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onTextChanged(int dimension, int action) {
        ViewGroup.LayoutParams layoutParams=editText.getLayoutParams();
        layoutParams.width=dpToPx(dimension,editText.getContext());
        editText.setLayoutParams(layoutParams);
        send.setVisibility(action);
    }

    @Override
    public void onResponse(Comment comment,CommentsAdapter commentsAdapter,int action) {
        relativeLayout.setVisibility(action);
        name.setText(comment.getFullName());
        send.setOnClickListener(v->{
            commentPresenter.sendComment(comment,commentsAdapter,editText.getText().toString());
        });
    }

    @Override
    public void hideKeyboard() {
        editText.setText("");
        InputMethodManager im= (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseBoard(int action) {
        relativeLayout.setVisibility(action);
        send.setOnClickListener(v->{
            commentPresenter.sendComment(null,null,editText.getText().toString());
        });
    }

    @Override
    public void focusEditText() {
        editText.requestFocus();
        InputMethodManager im= (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }



    private int dpToPx(int dp, Context context) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

}