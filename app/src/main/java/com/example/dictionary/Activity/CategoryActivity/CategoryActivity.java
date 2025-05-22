package com.example.dictionary.Activity.CategoryActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.Activity.TypeAdapter.TypeAdapter;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryInterface {
    ImageView back;
    EditText search;
    RecyclerView recyclerView;
    CategoryPresenter categoryPresenter=new CategoryPresenter(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        back=findViewById(R.id.back);
        search=findViewById(R.id.search);
        recyclerView=findViewById(R.id.recycle);
        categoryPresenter.onInit();
        back.setOnClickListener(v->{finish();});

        search.setOnEditorActionListener((v,actionId,event)->{return categoryPresenter.onSearch(actionId,search.getText().toString());});
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onInit(ArrayList<Type> types) {
        TypeAdapter typeAdapter=new TypeAdapter(this,types,0);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(typeAdapter);
    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }
}