package com.laughing.message.until;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/18 11:54
 */
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("first DynamicTask，"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}