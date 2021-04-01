package com.example.eventdemo;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**********************************
 * Project：EventDemo
 * PackageName： com.example.eventdemo
 * ClassName： BaseHandler
 * Author： dolphkon
 * Date：2021/4/1 13:56
 * Description： 基于WeakReference封装的BaseHandler
 ************************************/
public class BaseHandler<T extends BaseHandler.BaseHandlerCallBack> extends Handler {

    WeakReference<T> wr;

    public BaseHandler(T t) {
        wr = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = wr.get();
        if (t != null) {
            t.callBack(msg);
        }
    }

    public interface BaseHandlerCallBack {
        void callBack(Message msg);
    }
}