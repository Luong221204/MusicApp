package com.example.dictionary.Activity.Interface.View;

import android.view.View;

import com.example.dictionary.Activity.Model.Type;

import java.util.ArrayList;

public interface CategoryInterface {
    void onInit(ArrayList<Type> types);
    void hideKeyBoard();
}
