package com.example.demo;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/19 20:01
 **/
public class TestD implements Runnable {

    private int count = 100;

    private String str = "aa";

    @Override
    public void run() {

        while (count>0){
            synchronized (str) {

                if(count>0){
                    System.out.println(Thread.currentThread().getName() + "售出第" + count + "张票");
                    count--;
                    try {
                        Thread.currentThread().sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
