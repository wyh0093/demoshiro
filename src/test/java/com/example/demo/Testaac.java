package com.example.demo;

import io.swagger.models.auth.In;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/19 19:24
 **/
public class Testaac {

    public static Integer count;

    public Testaac(){
        this.count=10;
    }

    public void asd(String name){
        synchronized (this){
            if(this.count>0){
                System.out.println(name+"------------"+this.count+"张票");
                this.count--;
            }else {
                System.out.println("票已售完");
                return ;
            }
        }
    }
}
