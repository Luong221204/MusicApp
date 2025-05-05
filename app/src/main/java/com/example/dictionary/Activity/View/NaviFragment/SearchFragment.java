package com.example.dictionary.Activity.View.NaviFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dictionary.Activity.Adapter.FormAdapter.ResultSearchAdapter;
import com.example.dictionary.Activity.Adapter.NaviAdapter.SearchViewPagerAdapter;
import com.example.dictionary.Activity.Interface.View.SearchFragmentInterface;
import com.example.dictionary.Activity.Listener.ClickViewFragmentListener;
import com.example.dictionary.Activity.Listener.EditorActionListener;
import com.example.dictionary.Activity.Listener.TextWatcherListener;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.Presenter.ViewPresenter.SearchFragmentPresenter;
import com.example.dictionary.Activity.View.Activity.MainActivity;
import com.example.dictionary.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchFragmentInterface {
    public EditText editText;
    public ArrayList<String> list=new ArrayList<>();
    MainActivity mainActivity;
    public RelativeLayout relativeLayout,relativeLayout3;
    public ImageView searchView,delete,back;
    public RecyclerView recyclerView,historyView;
    ResultSearchAdapter resultSearchAdapter;
    ViewPager2 viewPager2;
    public int action=0;
    TabLayout tabLayout;
    SearchViewPagerAdapter searchViewPagerAdapter;
    public SearchFragmentPresenter searchFragmentPresenter=new SearchFragmentPresenter(this);
    TextWatcherListener textWatcherListener=new TextWatcherListener(this);
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        ClickViewFragmentListener clickViewFragmentListener=new ClickViewFragmentListener(this);
        EditorActionListener editorActionListener=new EditorActionListener(this);
        searchView=view.findViewById(R.id.im);
        relativeLayout3=view.findViewById(R.id.relative3);
        recyclerView=view.findViewById(R.id.search_recycle);
        resultSearchAdapter=new ResultSearchAdapter(MyApplication.fakeData, (MainActivity) requireActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(requireActivity());
        /*recyclerView.setAdapter(resultSearchAdapter);
        recyclerView.setLayoutManager(layoutManager);*/
        historyView=view.findViewById(R.id.history);
        historyView.setAdapter(resultSearchAdapter);
        historyView.setLayoutManager(layoutManager);
        delete=view.findViewById(R.id.delete);
        back=view.findViewById(R.id.back);
        mainActivity= (MainActivity) getActivity();
        back.setOnClickListener(clickViewFragmentListener);
        delete.setOnClickListener(clickViewFragmentListener);
        editText=view.findViewById(R.id.search_text);
        editText.addTextChangedListener(textWatcherListener);
        viewPager2=view.findViewById(R.id.view_pager);
        relativeLayout=view.findViewById(R.id.relative1);
        searchViewPagerAdapter=new SearchViewPagerAdapter(requireActivity(),1);
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager2.setAdapter(searchViewPagerAdapter);
        editText.setOnClickListener(clickViewFragmentListener);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                searchFragmentPresenter.showTabLayout(tab,position);
            }
        }).attach();
        editText.setOnEditorActionListener(editorActionListener);
        return view;
    }

    @Override
    public void stateSearchView() {
        editText.setText("");
        editText.addTextChangedListener(textWatcherListener);
    }
    @Override
    public void backToActivity() {
        assert mainActivity != null;
        mainActivity.frameLayout.setVisibility(View.GONE);
        mainActivity.relativeLayout.setVisibility(View.VISIBLE);
        mainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        mainActivity.isShowed=false;
    }

    @Override
    public void showOnResultRecycleView(ArrayList<String> list) {
        resultSearchAdapter.setData(list);
    }

    @Override
    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    @Override
    public void hideSearch() {
        relativeLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearch() {
        relativeLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showTabLayout(TabLayout.Tab tab, int position) {
        switch(position){
            case 0: tab.setText("Nổi bật");break;
            case 1: tab.setText("PlayList");break;
            case 2: tab.setText("Nghệ sĩ");break;
            case 3: tab.setText("Bài hát");break;
            default:tab.setText("Nổi bật");
        }
    }

}