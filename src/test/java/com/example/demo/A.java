package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qwe on 2019/9/2.
 */
public class A {
   /* static{
        System.out.println("1");
    }*/
    public A(){
        System.out.println("2");
    }

    public static String getA(){return "a";}

    public static void main(String[] args) {
        String a = "a";
        final String c = "a";
        String b = a+"b";
        String d =  c+"b";
        String e = getA()+"b";
        String compare = "ab";

//        System.out.println(b==compare);
//
//        System.out.println(d==compare);
//
//        System.out.println(e==compare);

        String a1 = "h"+"e";
        String a2 = "h";
        String a4 = a2+"e";
//        final String a3 = "h";
//        System.out.println(a1==a4);

        String i="hello2";
        String j="hello"+2;
//        System.out.println("a==c-->  "+(i==j));

        String p1 = new String("sd");
        String p2 = "sd";
        String p3 = new String("sd");

//        System.out.println(p1==p2);
//        System.out.println(p2==p3);
//        System.out.println(p1==p3);


        List<Integer> lista = new ArrayList<>();
        lista.add(1);
        lista.add(2);
        lista.add(3);
        lista.add(4);

        Iterator<Integer> it = lista.iterator();
        while (it.hasNext()){
            if(it.next()<3)
            it.remove();
        }
        System.out.println(lista.size());

    }
}
