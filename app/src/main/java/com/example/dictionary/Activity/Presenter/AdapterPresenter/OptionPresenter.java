package com.example.dictionary.Activity.Presenter.AdapterPresenter;

import android.content.Context;
import android.widget.Toast;

import com.example.dictionary.Activity.Application.MyLiveData;
import com.example.dictionary.Activity.Interface.Adapter.OptionInterface;
import com.example.dictionary.Activity.Model.Options;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.Presenter.ViewPresenter.ViewFragmentPresenter;
import com.example.dictionary.Activity.View.NaviFragment.Pager2Fragment.ArtistBottomFragment;
import com.example.dictionary.Activity.View.NaviFragment.PagerFragment.AddToPlayListFragment;

public class OptionPresenter {
    OptionInterface optionInterface;

    public OptionPresenter(OptionInterface optionInterface) {
        this.optionInterface = optionInterface;
    }
    public void onInit(Options options){
        if(options.getFlagDisable()){
            optionInterface.onInit(0.2F,false,options.getName(),options.getIcon());
        }else{
            optionInterface.onInit(1F,true,options.getName(),options.getIcon());

        }
    }
    public void onOptions(Options options, Context context, Song song){
        if(options.getName().equals("Thêm vào Playlist")){
            AddToPlayListFragment add=new AddToPlayListFragment();
            optionInterface.onAddToPlayListFragment(add);
        }else if(options.getName().equals("Xem nghệ sĩ")){
            ArtistBottomFragment add=new ArtistBottomFragment();
            optionInterface.onArtistBottomFragment(add,song);
        }else if(options.getName().equals("Tải xuống")){
            optionInterface.onInit(0.2F,false,options.getName(),options.getIcon());
            ViewFragmentPresenter.onDownload(context,song);
        }else if(options.getName().equals("Thêm vào danh sách phát")){
            MyLiveData.getInstance().addToSongsList(song);
            Toast.makeText(context,"Đã thêm vào danh sách phát",Toast.LENGTH_LONG).show();
        }
    }
}
