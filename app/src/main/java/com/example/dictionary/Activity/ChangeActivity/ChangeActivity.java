package com.example.dictionary.Activity.ChangeActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
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

import org.checkerframework.checker.units.qual.C;

public class ChangeActivity extends AppCompatActivity implements ChangeInterface {
    ImageView eye1,eye2,close;
    EditText old,newP;
    AppCompatButton change;
    ChangePresenter changePresenter=new ChangePresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change);
        eye1=findViewById(R.id.eye1);
        eye2=findViewById(R.id.eye2);
        old=findViewById(R.id.old);
        close=findViewById(R.id.close);
        newP=findViewById(R.id.newP);
        change=findViewById(R.id.change);
        old.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newP.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        eye1.setOnClickListener(v->{changePresenter.onClickOldEye(old.getInputType());});
        eye2.setOnClickListener(v->{changePresenter.onClickNewEye(newP.getInputType());});
        change.setOnClickListener(v->changePresenter.solveChangePassword(old.getText().toString(),newP.getText().toString()));
        close.setOnClickListener(v->finish());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onOldEyeChange(int input, int eye) {
        old.setInputType(input);
        eye1.setImageResource(eye);
    }

    @Override
    public void onNewEyeChange(int input, int eye) {
        newP.setInputType(input);
        eye2.setImageResource(eye);
    }

    @Override
    public void onToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}