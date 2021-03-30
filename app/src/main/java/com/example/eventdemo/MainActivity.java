package com.example.eventdemo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv_hello_world;
    private Button btn_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_hello_world = findViewById(R.id.tv_hello_world);
        btn_test = findViewById(R.id.btn_test);
        new Thread(new Runnable() {
            @Override
            public void run() {
                tv_hello_world.setText("直接更新更新UI");
            }
        }).start();

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
                              Log.d(TAG, "run: 当前所在线程为"+Thread.currentThread().getName());
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
}
