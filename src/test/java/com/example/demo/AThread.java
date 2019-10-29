package com.example.demo;

/**
 * Created by qwe on 2019/8/12.
 */
public class AThread extends Thread {

    public synchronized void run(String str) {
        System.out.println(str);
    }

    AThread(String str){
        run(str);
    }
}
