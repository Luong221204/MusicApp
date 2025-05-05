package com.example.dictionary.Activity.Interface.View;

import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.BottomFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public interface ThemeInterface {
    void onInit(String name, ArrayList<Song> songs);
    void showBottomSheet(BottomFragment bottomFragment);
}
