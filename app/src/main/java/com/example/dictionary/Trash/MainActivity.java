package com.example.dictionary.Trash;

import static io.reactivex.rxjava3.internal.util.HalfSerializer.onNext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*recyclerView=findViewById(R.id.recycle);
        ArrayList<String> list=new ArrayList<>();
        songAdapter=new SongAdapter(list,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(songAdapter);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        HandlerThread handlerThread=new HandlerThread("myThread");
        handlerThread.start();
        Handler handler=new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    list.add((String) msg.obj);
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            songAdapter.setData(list);
                        }
                    });

                }
            }
        };
        HandlerThread handlerThread1= new HandlerThread("newThread");
        handlerThread1.start();
        Handler handler1=new Handler(handlerThread1.getLooper());
        handler1.post( new Runnable() {
            @Override
            public void run() {
                Message message1=Message.obtain();
                message1.what=1;
                message1.obj="kaka";
                handler.sendMessage(message1);
                Log.d("luong","kaka");
                handler1.postDelayed(this,2000);

            }
        });
        recyclerView.addItemDecoration(itemDecoration);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);//giả sử lấy dữ liệu từ server
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add("My Love");
                list.add("I do");
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        songAdapter.setData(list);
                    }
                });
            }
        }).start();*/



      /*  BehaviorSubject<List<String>> songSubject = BehaviorSubject.createDefault(list);
        songSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(stringList -> {
            songAdapter.setData((ArrayList<String>) stringList);

        });*/
       /* findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(handler);
            }
        });*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
  /*  @SuppressLint("CheckResult")
    public void addSong(BehaviorSubject<List<String>> mom){
        Observable.fromCallable(()->{
            EditText editText=findViewById(R.id.txtName);
            ArrayList<String> list= (ArrayList<String>) mom.getValue();
            list.add(String.valueOf(editText.getText()));
            return list;
        }).subscribeOn(Schedulers.io()).subscribe(mom::onNext);

    }
    public void add(Handler handler) {
        if (handler == null) {
            Log.e("DucLuong", "Handler chưa được khởi tạo!");
            return;
        }

        HandlerThread handlerThread = new HandlerThread("myThread1");
        handlerThread.start();

        Handler handler1 = new Handler(handlerThread.getLooper());
        Handler mainHandler = new Handler(Looper.getMainLooper()); // Handler chính để cập nhật UI

        handler1.post(() -> {
            // Lấy dữ liệu từ UI trong main thread
            handler1.post(() -> {
                EditText editText = findViewById(R.id.txtName);
                Message msg1 = Message.obtain();
                msg1.what = 1;
                msg1.obj = String.valueOf(editText.getText());
                Bundle bundle=new Bundle();


             //   Toast.makeText(MainActivity.this,"hihi"+msg1.obj,Toast.LENGTH_SHORT).show();

                handler.sendMessage(msg1);
            });
        });
    }

    public void onClickAddName(View view)
    {
        ContentValues values = new ContentValues();
        values.put(MyProvider.name, ((EditText) findViewById(R.id.txtName))
                .getText().toString());
        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);
        ((EditText) findViewById(R.id.txtName)).setText("");
        Toast.makeText(getBaseContext(), "New record inserted", Toast.LENGTH_LONG)
                .show();
    }*/
}