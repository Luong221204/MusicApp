package com.example.dictionary.Activity.SearchFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dictionary.Activity.Adapter.FormAdapter.ResultSearchAdapter;
import com.example.dictionary.Activity.Model.Search;
import com.example.dictionary.Activity.SearchViewPagerAdapter.SearchViewPagerAdapter;
import com.example.dictionary.Activity.Listener.ClickViewFragmentListener;
import com.example.dictionary.Activity.Listener.EditorActionListener;
import com.example.dictionary.Activity.Listener.TextWatcherListener;
import com.example.dictionary.Activity.MainActivity.MainActivity;
import com.example.dictionary.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchFragmentInterface {
    public EditText editText;
    public RelativeLayout relativeLayout,relativeLayout3;
    public ImageView searchView,delete,back;
    public RecyclerView recyclerView;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    public int action=0;
    Disposable disposable;
    public SearchFragmentPresenter searchFragmentPresenter=new SearchFragmentPresenter(this);
    TextWatcherListener textWatcherListener=new TextWatcherListener(this);

    @Override
    public void onStart() {
        super.onStart();
        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_background));
        window.setNavigationBarColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_background));
    }

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
        delete=view.findViewById(R.id.delete);
        back=view.findViewById(R.id.back);
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager2=view.findViewById(R.id.view_pager);
        relativeLayout=view.findViewById(R.id.relative1);
        back.setOnClickListener(clickViewFragmentListener);
        delete.setOnClickListener(clickViewFragmentListener);
        editText=view.findViewById(R.id.search_text);
        searchFragmentPresenter.onInit(getActivity());
        editText.setOnClickListener(clickViewFragmentListener);
        editText.setOnEditorActionListener(editorActionListener);
        disposable=searchFragmentPresenter.getObservable(editText);
        return view;
    }

    @Override
    public void stateSearchView() {
        editText.setText("");
        editText.addTextChangedListener(textWatcherListener);
    }
    @Override
    public void backToActivity() {
        ((MainActivity) requireActivity()).frameLayout.setVisibility(View.GONE);
        ((MainActivity) requireActivity()).relativeLayout.setVisibility(View.VISIBLE);
        ((MainActivity) requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
        ((MainActivity) requireActivity()).uiPresenter.onInit();
    }

    @Override
    public void showOnResultRecycleView(ResultSearchAdapter resultSearchAdapter, LinearLayoutManager layoutManager, ArrayList<Search> list, int action) {
        recyclerView.setVisibility(action);
        recyclerView.setAdapter(resultSearchAdapter);
        recyclerView.setLayoutManager(layoutManager);
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
    public void showTabLayout(TabLayout.Tab tab, String title) {
        tab.setText(title);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getViewLifecycleOwner();
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return requireActivity();
    }

    @Override
    public View getSearchView() {
        return this.editText;
    }

    @Override
    public void onInit(SearchViewPagerAdapter searchViewPagerAdapter) {
        viewPager2.setAdapter(searchViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                searchFragmentPresenter.showTabLayout(tab,position);
            }
        }).attach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }


}