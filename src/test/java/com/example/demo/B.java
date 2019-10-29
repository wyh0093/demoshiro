package com.example.demo;

/**
 * Created by qwe on 2019/9/2.
 */
public class B extends A {
    static {
        System.out.println("a");
    }
    public B(){
        System.out.println("b");
    }

    public static void main(String[] args) {
        A a = new B();
        a = new B();
    }
}
