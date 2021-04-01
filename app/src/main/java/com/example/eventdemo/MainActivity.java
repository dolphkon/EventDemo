package com.example.eventdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView tv_hello_world;
    private Button btn_test;
    private Button btn_handler;
    private Button btn_message;
    private Button btn_messageQueue;
    private Button btn_looper;
    private Button btn_threadLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_hello_world = findViewById(R.id.tv_hello_world);
        btn_test = findViewById(R.id.btn_test);
        btn_handler = findViewById(R.id.btn_handler);
        btn_message = findViewById(R.id.btn_test);
        btn_messageQueue = findViewById(R.id.btn_messageQueue);
        btn_looper = findViewById(R.id.btn_looper);
        btn_threadLocal = findViewById(R.id.btn_threadLocal);
        btn_handler.setOnClickListener(this);

        /**  解决子线程访问UI导致崩溃，有如下方案：
         *   1.在创建视图的线程进行视图UI的更新，即主线程创建线程，主线程更新UI
         *   2.在ViewRootImpl创建之前完成子线程UI的更新，即onCreate()中进行子线程更新UI
         *   3.子线程切换到主线程进行UI更新，比如handler，view.post()方法
         * */
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "new thread()---点击的时候创建子线程并更新UI");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_hello_world.setText("点击的时候再更新UI");
                                Log.d(TAG, "run: 当前所在线程为" + Thread.currentThread().getName());
                            }
                        });
                    }
                }).start();
            }
        });

        Log.d(TAG, "onCreate: ");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "new thread()---onResume: 此时ViewRootImpl已经创建完成");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.btn_handler:
                startActivity(new Intent(this, HandlerActivity.class));
                break;
        }
    }
}
