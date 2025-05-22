package com.example.dictionary.Activity.TypeAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Model.Type;
import com.example.dictionary.Activity.CategoryActivity.CategoryActivity;
import com.example.dictionary.Activity.ThemeActivity.ThemeActivity;
import com.example.dictionary.R;

import java.util.ArrayList;

public class TypePresenter {
    TypeInterface typeInterface;

    public TypePresenter(TypeInterface typeInterface) {
        this.typeInterface = typeInterface;
    }

    public void onInit(int mode , int position, ArrayList<Type> types){
        if(mode == 0){
            typeInterface.onInit(types.get(position).getTitle(),types.get(position).getImage());
        }else{
            if(position == types.size()) typeInterface.onInitEnd("Xem tất cả", R.drawable.arrow);
            else{
                typeInterface.onInit(types.get(position).getTitle(),types.get(position).getImage());
            }
        }
    }
    public void sendBroadcast(Context context, ArrayList<Type> types, int mode, int position){
        if(mode == 0){
            Intent intent1=new Intent(context, ThemeActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable(MyApplication.ACTION, types.get(position));
            intent1.putExtra(MyApplication.BUNDLE,bundle);
            context.startActivity(intent1);
        }else{
            if(position == types.size()){
                Intent intent1=new Intent(context, CategoryActivity.class);
                context.startActivity(intent1);

            }else{
                Intent intent1=new Intent(context, ThemeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(MyApplication.ACTION,types.get(position));
                intent1.putExtra(MyApplication.BUNDLE,bundle);
                context.startActivity(intent1);
            }

        }
    }
}
