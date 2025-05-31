package com.example.dictionary.Activity.VIewSongActivity.ViewFragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dictionary.Activity.Application.MyApplication;
import com.example.dictionary.Activity.CommentFragment.CommentFragment;
import com.example.dictionary.Activity.Interface.Adapter.CLickBottom;
import com.example.dictionary.Activity.Interface.View.ItemClickListener;
import com.example.dictionary.Activity.Service.MyService;
import com.example.dictionary.Activity.VIewSongActivity.AutoFragment.AutomaticFragment;
import com.example.dictionary.Activity.Listener.SeekBarChangeListener;
import com.example.dictionary.Activity.Model.Song;
import com.example.dictionary.Activity.VIewSongActivity.ViewSongActivity;
import com.example.dictionary.Activity.BottomFragment.BottomFragment;
import com.example.dictionary.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends Fragment implements ViewFragmentInterface.View, ItemClickListener, CLickBottom {
    Runnable runnable,runnable2,runnable1;
    SeekBar seekBar;
    boolean isLiked=false;
    FrameLayout frameLayout;
    Scene scene1,scene2,currentScene;
    Transition transition;
    ViewGroup viewGroup;
    SeekBarChangeListener seekBarChangeListener;
    RelativeLayout relativeLayout;
    ImageView pause,imageView,list,like,back,next,add,download,comment,turn;
    TextView time,song,singers,duration;
    public ViewFragmentPresenter viewFragmentPresenter=new ViewFragmentPresenter(this);
    public BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MyApplication.FRAGMENTCHOSEN.equals(intent.getAction())) viewFragmentPresenter.receiveFromBroadcast(intent);
            else if(MyApplication.ISBACK.equals(intent.getAction())) viewFragmentPresenter.onNext();
            else if(MyApplication.COMPLETE.equals(intent.getAction())) viewFragmentPresenter.onCompleteMedia();
            else if(MyApplication.SUCCESS.equals(intent.getAction())) {
                viewFragmentPresenter.onIntent(intent,getContext());
            }else if(MyApplication.OFFLINE.equals(intent.getAction())){
                Bundle bundle=intent.getBundleExtra(MyApplication.BUNDLE);
                viewFragmentPresenter.onOffline(bundle);
            }else if(MyApplication.SUCCESSFULL.equals(intent.getAction())){
                viewFragmentPresenter.onReset();
            }
        }
    };

    @SuppressLint("InlinedApi")
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyApplication.FRAGMENTCHOSEN);
        intentFilter.addAction(MyApplication.ISBACK);
        intentFilter.addAction(MyApplication.ISBACKOFF);
        intentFilter.addAction(MyApplication.COMPLETE);
        intentFilter.addAction(MyApplication.SUCCESS);
        intentFilter.addAction(MyApplication.AGAIN3);
        intentFilter.addAction(MyApplication.OFFLINE);
        intentFilter.addAction(MyApplication.SUCCESSFULL);
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        requireActivity().registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        song=view.findViewById(R.id.song);
        frameLayout=view.findViewById(R.id.frame);
        AutomaticFragment automaticFragment=new AutomaticFragment();
        @SuppressLint("CommitTransaction") FragmentTransaction fragmentTransaction=requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame,automaticFragment,"auto");
        singers=view.findViewById(R.id.singers);
        viewGroup=view.findViewById(R.id.layout);
        relativeLayout=view.findViewById(R.id.relative);
        like=view.findViewById(R.id.like);
        comment=view.findViewById(R.id.comment);
        comment.setOnClickListener(v->{
            viewFragmentPresenter.openComments();
        });
        like.setOnClickListener(v->{
            viewFragmentPresenter.sendYourFavourite(getContext());
        });
        imageView=view.findViewById(R.id.image);
        Animation animation= AnimationUtils.loadAnimation(requireActivity(),R.anim.newz);
        like.setAnimation(animation);
        scene1=Scene.getSceneForLayout(viewGroup,R.layout.thumb_layout,requireActivity());
        scene2=Scene.getSceneForLayout(viewGroup,R.layout.thumb_layout2,requireActivity());
        scene1.enter();
        currentScene=scene1;
        transition= TransitionInflater.from(requireActivity()).inflateTransition(R.transition.example1);
        seekBarChangeListener=new SeekBarChangeListener(this);
        runnable1=new Runnable() {
            @Override
            public void run() {
                list=viewGroup.findViewById(R.id.list);
                next=viewGroup.findViewById(R.id.next);
                back=viewGroup.findViewById(R.id.back);
                seekBar=viewGroup.findViewById(R.id.seekbar);
                add=viewGroup.findViewById(R.id.add);
                download=viewGroup.findViewById(R.id.download);
                seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
                time=viewGroup.findViewById(R.id.time);
                duration=viewGroup.findViewById(R.id.duration);
                viewFragmentPresenter.setDuration();
                pause=viewGroup.findViewById(R.id.pause);
                viewFragmentPresenter.setStatusBar(pause);
                turn=viewGroup.findViewById(R.id.turn);
                viewFragmentPresenter.onSceneChange(seekBar,time);
                turn.setOnClickListener(v->{
                   viewFragmentPresenter.replay(requireActivity());
                });
                add.setOnClickListener(v->{
                    viewFragmentPresenter.addToPlaylistFragment(ViewFragment.this);
                });
                viewFragmentPresenter.onDownloadStatus(requireActivity(),download);
                download.setOnClickListener(v->{ViewFragmentPresenter.onDownload(getContext(),MyApplication.song);});
                next.setOnClickListener(v->{viewFragmentPresenter.sendBroadcast(requireActivity(),MyApplication.NEXT);});
                back.setOnClickListener(v->{viewFragmentPresenter.sendBroadcast(requireActivity(),MyApplication.BACK);});
                pause.setOnClickListener(v -> {
                    viewFragmentPresenter.onClickPauseOrPlay((ViewSongActivity) requireActivity(),null);
                });
                list.setOnClickListener(v->{
                    viewFragmentPresenter.Scene(1,scene2,fragmentTransaction);
                    runnable2.run();
                });
            }
        };
        runnable1.run();
        runnable2=new Runnable() {
            @Override
            public void run() {
                next=viewGroup.findViewById(R.id.next);
                back=viewGroup.findViewById(R.id.back);
                list=viewGroup.findViewById(R.id.list);
                seekBar=viewGroup.findViewById(R.id.seekbar);
                seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
                viewFragmentPresenter.onSceneChange(seekBar,null);
                pause=viewGroup.findViewById(R.id.pause);
                turn=viewGroup.findViewById(R.id.turn);
                viewFragmentPresenter.setStatusBar(pause);
                next.setOnClickListener(v->{viewFragmentPresenter.sendBroadcast(requireActivity(),MyApplication.NEXT);});
                back.setOnClickListener(v->{viewFragmentPresenter.sendBroadcast(requireActivity(),MyApplication.BACK);});
                pause.setOnClickListener(v -> {
                    viewFragmentPresenter.onClickPauseOrPlay((ViewSongActivity) requireActivity(),null);
                });
                turn.setOnClickListener(v->{
                    viewFragmentPresenter.replay(requireActivity());
                });
                list.setOnClickListener(v->{
                    viewFragmentPresenter.setStatusBar(pause);
                    viewFragmentPresenter.Scene(2,scene1,fragmentTransaction);
                    runnable1.run();
                });
            }
        };
        scene1.setEnterAction(runnable1);
        scene2.setEnterAction(runnable2);
        viewFragmentPresenter.init(Looper.getMainLooper());
        imageView.post(viewFragmentPresenter.runnable(imageView));


        return view;
    }

    @Override
    public void onSeekBar(int progress) {
        seekBar.setProgress(progress);
    }

    @Override
    public void onTimeline(String time_text) {
        time.setText(time_text);
    }

    @Override
    public void playOrPause(int source) {
        pause.setImageResource(source);
    }

    @Override
    public void showStatus(boolean isLiked) {

    }

    @Override
    public void showRotation(boolean isPaused) {
        if(isPaused) imageView.post(viewFragmentPresenter.runnable(imageView));//!isPaused
        else imageView.animate().cancel();
    }

    @Override
    public void onDuration(String time) {
        duration.setText(time);
    }

    @Override
    public void scenePause(int source) {
        pause.setImageResource(source);
    }

    @Override
    public void disPlayScene(Scene scene,int visibility1,int visibility2) {
        TransitionManager.go(scene,transition);
        frameLayout.setVisibility(visibility1);
        relativeLayout.setVisibility(visibility2);
        imageView.setVisibility(visibility2);
    }

    @Override
    public void statusPlayButton(ImageView pause,int source) {
        pause.setImageResource(source);
    }

    @Override
    public void onSeekBar2(int progress, SeekBar seekBar,TextView time,String timeline) {
        seekBar.setProgress(progress);
        if(time != null) time.setText(timeline);
    }

    @Override
    public void onInit(String singer_name,String song_name,String image) {
        song.setText(song_name);
        song.setSelected(true);
        singers.setText(singer_name);
        Glide.with(requireActivity()).load(image).into(imageView);
    }

    @Override
    public void onDownloadComplete() {
        Toast.makeText(getContext(),"Đã tải thành công",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepareDownload() {
        Toast.makeText(getContext(),"Đang tải",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOffline(String singer_name, String song_name, String image) {
        song.setText(song_name);
        song.setSelected(true);
        singers.setText(singer_name);
        imageView.setImageURI(Uri.parse(image));
    }

    @Override
    public void onDownloadStatus(ImageView imageView) {
        imageView.setAlpha(0.3f);
    }

    @Override
    public void showBottomSheet(BottomFragment bottomFragment) {
        bottomFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.SONG);
    }

    @Override
    public void onReset(float alpha) {
        download.setAlpha(alpha);
        download.setEnabled(false);
    }

    @Override
    public void onLove(int icon) {
        like.setImageResource(icon);

    }

    @Override
    public void onToast(String message) {
        Toast.makeText(requireActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCommentFragment(CommentFragment commentFragment) {
        commentFragment.show(requireActivity().getSupportFragmentManager(),"haha");
    }


    @Override
    public void onClickListener(Song song) {
        viewFragmentPresenter.showBottomSheet(song);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClickBottom(BottomSheetDialogFragment bottomSheetDialogFragment, Song song) {
        bottomSheetDialogFragment.show(requireActivity().getSupportFragmentManager(),MyApplication.ACTION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(receiver);

    }
}