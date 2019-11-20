package com.example.demo;

import io.swagger.models.auth.In;
import org.omg.CORBA.INTERNAL;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/19 18:59
 **/
public class TestA extends Thread{

    private Testaac a=null;

    public TestA(Testaac b){
//        this.count=10;
        this.a = b;
    }

//    private Integer count;



    @Override
    public void run() {
        while (this.a.count>0) {
            this.a.asd(Thread.currentThread().getName());
            try {
                sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
