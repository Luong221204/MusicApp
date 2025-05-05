package com.example.dictionary.Activity.View.NaviFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Interface.View.AccountInterface;
import com.example.dictionary.Activity.Presenter.ViewPresenter.AccountPresenter;
import com.example.dictionary.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements AccountInterface {
    CircleImageView circleImageView;
    TextView textView;
    AccountPresenter accountPresenter=new AccountPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        circleImageView=view.findViewById(R.id.image);
        textView=view.findViewById(R.id.name);
        accountPresenter.init();
        return view;
    }

    @Override
    public void init(int source, String name) {
        circleImageView.setImageResource(source);
        textView.setText(name);
    }
}