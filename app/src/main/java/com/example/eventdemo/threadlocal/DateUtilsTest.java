package com.example.eventdemo.threadlocal;


import java.text.ParseException;

/**********************************
 * Project：EventDemo
 * PackageName： com.example.eventdemo.threadlocal
 * ClassName： DateUtilsTest
 * Author： dolphkon
 * Date：2021/3/18 15:31
 * Description： TODO
 ************************************/
public class DateUtilsTest {
    public static class SimpleDateFormatThread extends Thread{
        private String mDate;
   public SimpleDateFormatThread(String strDate){
       this.mDate=strDate;
   }


        @Override
        public void run() {
            try {
                System.out.print("当前所在线程为:"+this.getName()+"-------"+"当前日期为:"+DateUtil.parse(mDate)+"\n");

            } catch (ParseException e) {
                e.printStackTrace();
            }

//            System.out.print("当前所在线程为:"+this.getName()+"-------"+"当前日期为:"+System.currentTimeMillis()+"\n");
        }
    }

    public static void main(String[] args) {
        String dateStr = "2021-03-18 15:36:20";
        for (int i = 0; i < 5; i++) {
            new SimpleDateFormatThread(dateStr).start();
        }

    }
}
