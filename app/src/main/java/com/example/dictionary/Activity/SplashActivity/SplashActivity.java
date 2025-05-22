package com.example.dictionary.Activity.SplashActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionary.R;

import io.reactivex.rxjava3.disposables.Disposable;

public class SplashActivity extends AppCompatActivity implements SplashInterface {

    SplashPresenter splashPresenter=new SplashPresenter(this);
    Disposable disposable;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        splashPresenter.loginOrNot(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onFinish() {
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}