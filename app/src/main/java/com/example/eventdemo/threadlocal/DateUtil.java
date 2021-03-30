package com.example.eventdemo.threadlocal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**********************************
 * Project：EventDemo
 * PackageName： com.example.eventdemo.threadlocal
 * ClassName： DateUtil
 * Author： dolphkon
 * Date：2021/3/18 15:25
 * Description： TODO
 ************************************/
public class DateUtil {

//    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
//        @Override
//        protected DateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        }
//    };

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }

}
