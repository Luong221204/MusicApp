package com.example.dictionary.Activity.MainActivity.AccountFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    RelativeLayout sign_out;
    AccountPresenter accountPresenter=new AccountPresenter(this);
    ActivityResultLauncher<Intent> launcher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if(result.getResultCode()== Activity.RESULT_OK){
                    accountPresenter.getData(result.getData(),getContext());

                }
            });

    @Override
    public void onStart() {
        super.onStart();

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        circleImageView=view.findViewById(R.id.image);
        textView=view.findViewById(R.id.name);
        sign_out=view.findViewById(R.id.sign_out);
        sign_out.setOnClickListener(v->{
            accountPresenter.sign_out(getContext());
        });
        circleImageView.setOnClickListener(v->{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                accountPresenter.requestForPermission();
            }
        });
        accountPresenter.init();
        return view;
    }

    @Override
    public void init2(String source, String name) {
        Glide.with(requireContext()).load(source).into(circleImageView);
        textView.setText(name);
    }

    @Override
    public void init(int source, String name) {
        textView.setText(name);
        circleImageView.setImageResource(source);

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public int hasPermission(String permission) {

        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void requestPermission(String[] permissions,int request_code) {
        requestPermissions(permissions,request_code);
    }

    @Override
    public ActivityResultLauncher<Intent> getLauncher() {
        return launcher;
    }

    @Override
    public void resetAvatar(String uri) {
        Glide.with(requireContext()).load(uri).into(circleImageView);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        accountPresenter.checkForPermission(requestCode,grantResults);
    }
}