package com.example.eventdemo;

import android.app.slice.Slice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import javax.net.ssl.SNIHostName;

/**********************************
 * Project：EventDemo
 * PackageName： com.example.eventdemo
 * ClassName： HandlerActivity
 * Author： dolphkon
 * Date：2021/3/30 16:48
 * Description： TODO
 ************************************/
public class HandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private   User user;

    private Button btn_handler_json;
    private Button btn_memory_leak;
    private TextView tv_hello_world;


private   Handler handler=new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 0:
                user= (User) msg.obj;
                Toast.makeText(HandlerActivity.this, "handler传递实体类信息为:"+user.toString(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                // 第一种方式:通过bundle传
             Bundle bundle= msg.getData();
          String id =bundle.getString("id");
          String name=   bundle.getString("name");
          String age=   bundle.getString("age");
                Toast.makeText(HandlerActivity.this, "handler传递bundle数据：id:"+id+"\n"+"name:"+name+"\n"+"age:"+age, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //第二种方式：通过JsonObject传
                JSONObject jsonObject= (JSONObject) msg.obj;
                try {
                    String mId=   jsonObject.getString("id");
                    String mName=jsonObject.getString("name");
                    String mAge=jsonObject.getString("age");
                    Toast.makeText(HandlerActivity.this, "handler传递json数据：id:"+mId+"\n"+"name:"+mName+"\n"+"age:"+mAge, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        Button btn_handler_bean = findViewById(R.id.btn_handler_bean);
        btn_handler_json = findViewById(R.id.btn_handler_json);
        btn_memory_leak = findViewById(R.id.btn_memory_leak);
        tv_hello_world = findViewById(R.id.tv_hello_world);
        btn_handler_bean.setOnClickListener(this);
        btn_handler_json.setOnClickListener(this);
        btn_memory_leak.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_handler_bean:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        User user = new User();
                        user.setAge("20");
                        user.setId("1");
                        user.setName("张三");
                        message.what = 0;
                        message.obj = user;
                        handler.sendMessage(message);
                    }
                }).start();

                break;

            case R.id.btn_handler_json:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        // 第一种方式:通过bundle传（通用的）
                        Bundle bundle = new Bundle();
                        bundle.putString("id", "1");
                        bundle.putString("name", "2");
                        bundle.putString("age", "20");
                        message.setData(bundle);
                        handler.sendMessage(message);


//                   第二种方式：通过object传
                        Message msg=Message.obtain();
                        msg.what=2;
                        JSONObject object=new JSONObject();
                        try {
                            Thread.sleep(3000);
                            object.put("id",1);
                            object.put("name","张三");
                            object.put("age","20");
                        }  catch (Exception e) {
                            e.printStackTrace();
                        }
                        msg.obj=object;
                        handler.sendMessage(msg);
                    }
                }).start();
                break;

            case R.id.btn_memory_leak:
                doInBackground();


                break;
        }
    }


    private void doInBackground() {
        new Thread(){
            @Override
            public void run() {
                //2.在工作线程中完成具体的事情
                try {
                    sleep(6000);
                } catch (Exception e) {

                }
                //3.事情完成后，通过mHandler线程间消息传递给主线程的Handler进行更新操作
                handler.sendEmptyMessage(3);
            }
        }.start();
    }
}
