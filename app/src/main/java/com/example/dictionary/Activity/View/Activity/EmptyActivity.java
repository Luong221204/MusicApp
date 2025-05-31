package com.example.dictionary.Activity.View.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionary.R;

public class EmptyActivity extends AppCompatActivity implements EmptyInterface {
    ImageView edit;
    AppCompatButton change;
    EditText name;
    ImageView close;
    EmptyPresenter emptyPresenter=new EmptyPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_empty);
        edit=findViewById(R.id.edit);
        change=findViewById(R.id.change);
        name=findViewById(R.id.name);
        close=findViewById(R.id.close);
        emptyPresenter.onInit(getIntent());
        change.setOnClickListener(v->{
            emptyPresenter.onChange(name.getText().toString());});
        close.setOnClickListener(v->finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onInit(String text) {
        name.setText(text);
    }

    @Override
    public void onToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}