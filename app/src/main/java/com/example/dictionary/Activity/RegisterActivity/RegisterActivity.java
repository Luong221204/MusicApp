package com.example.dictionary.Activity.RegisterActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.Adapter.FavouriteAdapter.FavouriteAdapter;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.R;

import java.util.HashSet;
import java.util.Set;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface {
    RelativeLayout relativeLayout;
    EditText full_name,username,password,confirm,phone;
    RadioButton male,female;
    Button button;
    Set<String> routines=new HashSet<>();
    TextView favourite;
    RegisterPresenter registerPresenter=new RegisterPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        relativeLayout=findViewById(R.id.frame1);
        full_name=findViewById(R.id.full_name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        confirm=findViewById(R.id.again);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        button=findViewById(R.id.register);
        favourite=findViewById(R.id.favourite);
        phone=findViewById(R.id.phone);
        relativeLayout.setOnClickListener(v->{registerPresenter.showDialog(RegisterActivity.this);});
        button.setOnClickListener(v->{
            if(male.isChecked()) registerPresenter.register(full_name.getText().toString(),
                username.getText().toString(),password.getText().toString()
                ,confirm.getText().toString(),1,null,phone.getText().toString());
            else registerPresenter.register(full_name.getText().toString(),
                    username.getText().toString(),password.getText().toString()
                    ,confirm.getText().toString(),0,null,phone.getText().toString());
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showDialog(Dialog dialog) {
        RecyclerView recyclerView=dialog.findViewById(R.id.recycle);
        Button button =dialog.findViewById(R.id.ok);
        FavouriteAdapter favouriteAdapter=new FavouriteAdapter(MyApplication.theme,dialog.getContext(),routines);
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(dialog.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(favouriteAdapter);
        button.setOnClickListener(v->{registerPresenter.setMusicRoutines(routines);
            dialog.dismiss();});
        dialog.show();
    }

    @Override
    public void register(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLack() {
        Toast.makeText(this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExisted() {
        Toast.makeText(this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showFavouriteTypes(String s) {
        favourite.setText(s);
    }

    @Override
    public void finishActivity(Intent intent) {
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}