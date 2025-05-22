package com.example.dictionary.Activity.LoginActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dictionary.R;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    Button login,send;
    EditText username,password;
    TextView forgot,register,error;
    LoginPresenter loginPresenter = new LoginPresenter(this);
    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    loginPresenter.handleResult(result.getData());
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        forgot=findViewById(R.id.forgot);
        register=findViewById(R.id.register);
        login.setOnClickListener(v->{
            loginPresenter.login(username.getText().toString(),password.getText().toString(),LoginActivity.this);
        });
        forgot.setOnClickListener(v->{
            loginPresenter.forgotPass(LoginActivity.this,username.getText().toString());
        });
        register.setOnClickListener(v->{
            loginPresenter.register(resultLauncher,this);
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.textChange2();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void showMissing(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void showErrorUsername(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorPassword(int status) {
        Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        forgot.setVisibility(status);
    }

    @Override
    public void showErrorPassword2(int status) {
        forgot.setVisibility(status);

    }

    @Override
    public void showResult(String name, String pass) {
        username.setText(name);
        password.setText(pass);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void showProcess(Dialog dialog) {
        dialog.show();
    }

    @Override
    public void cancelProcess(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void showDialog(Dialog dialog, String phoneNumber) {
        TextView phone=dialog.findViewById(R.id.phone);
        send=dialog.findViewById(R.id.verify);
        EditText editText=dialog.findViewById(R.id.text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPresenter.textChange();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        error=dialog.findViewById(R.id.warn);
        phone.setText(loginPresenter.dealPhoneNumber(phoneNumber));
        send.setOnClickListener(v->{
            loginPresenter.verifyCode(username.getText().toString(),editText.getText().toString(),dialog.getContext(),dialog);
        });
        dialog.show();
    }

    @Override
    public void showDialogError(int status) {
        error.setVisibility(status);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) send.getLayoutParams();
        params.topMargin = 30;
        send.setLayoutParams(params);
    }

    @Override
    public void cancelDialog(Dialog dialog) {
        dialog.dismiss();
    }

}