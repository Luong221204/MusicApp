package com.example.dictionary.Activity.CategoryActivity;

import android.view.inputmethod.EditorInfo;

import com.example.dictionary.Activity.ApiService.ApiService;
import com.example.dictionary.Activity.Model.Type;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {
    CategoryInterface categoryInterface;
    public CategoryPresenter(CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }
    public void onInit(){
        ApiService.apiService.getTypes().enqueue(new Callback<ArrayList<Type>>() {
            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                categoryInterface.onInit(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {
            }
        });
    }
    public boolean onSearch(int action, String text){
        if (action == EditorInfo.IME_ACTION_SEARCH) {
            if(text.isEmpty()){
                onInit();
            }
            else{
                ApiService.apiService.getSearchedType(text).enqueue(new Callback<ArrayList<Type>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                        categoryInterface.onInit(response.body());
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Type>> call, Throwable t) {
                    }
                });
            }
            categoryInterface.hideKeyBoard();
            return true;
        }
        return false;
    }
}
