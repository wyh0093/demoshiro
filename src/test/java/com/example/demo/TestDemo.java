package com.example.demo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qwe on 2019/8/12.
 */
public class TestDemo {
    public  static  void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(new AThread("qqq"));


//        maopao();


        /*arr = new int[]{4,1,5,3,7,6,9,8};
        aaaaa(0,7);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }*/



//        String str = test2("Sddd");


        /*Testaac test = new Testaac();
        TestA a1 = new TestA(test);
        new Thread(a1,"aa").start();
        new Thread(a1,"bb").start();
        new Thread(a1,"cc").start();*/

      /*  TestD d = new TestD();
        new Thread(d,"aa").start();
        new Thread(d,"bb").start();
        new Thread(d,"cc").start();*/
        /*a1.setName("窗口1");
        TestA a2 = new TestA(test);
        a2.setName("窗口2");
        TestA a3 = new TestA(test);
        a3.setName("窗口3");
        a1.start();
        a2.start();
        a3.start();*/


        TestD tr=new TestD();
        //四个线程对应四个窗口
        Thread t1=new Thread(tr, "窗口A");
        Thread t2=new Thread(tr, "窗口B");
        Thread t3=new Thread(tr, "窗口C");
        Thread t4=new Thread(tr, "窗口D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();




    }

    public static String test2(String str){
         char [] chars = str.toCharArray();
         chars[0]+= 32;
         return String.valueOf(chars);
    }

    public static void test1(){

        HashMap<String,Integer> hashMap = new HashMap<>();
        hashMap.put("1",1110);

        try {
            Integer integer = hashMap.get(1);
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Integer dsds=3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("www");
    }

    public static void maopao(){

        int[] a = new int[]{1,5,3,6,2};

        for(int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-i-1;j++){
                if(a[j]>a[j+1]){
                    int in = a[j];
                    a[j] = a[j+1];
                    a[j+1]=in;
                }
            }
        }

        for(int i: a){
            System.out.println(i);
        }

    }


    public static int[] arr = new int[8];

    public static void aaaaa(int left,int right){

        int i,j,t,temp;
        if(left>right){
            return;
        }
        i=left;
        j=right;
        temp = arr[left];
        while (i!=j){
            while (arr[j]>=temp && i<j){
                j--;
            }
            while (arr[i]<=temp && i<j){
                i++;
            }
            if(i<j){
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }

        }
        arr[left] = arr[i];
        arr[i] = temp;

        aaaaa(left,i-1);
        aaaaa(i+1,right);
        return;


    }

    public static void kuaipai(int left,int right){

        int i,j,t,temp;
        if(left>right)
            return;
        i= left;
        j = right;
        temp = arr[left]; //存基准数
        while(i!=j){
            while (arr[j]>=temp && i<j){
                j--;
            }
            while (arr[i]<=temp && i<j){
                i++;
            }
            if(i<j){
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[left] = arr[i];
        arr[i] = temp;

        //kuaipai(left,i-1);
        //kuaipai(i+1,right);
        return ;


    }

    public static void aa3(int left,int right){
        int i,j,t,temp;

        if(left>right)
            return;
        i = left;
        j = right;
        temp = arr[left];
        while (i!=j){
            while (arr[j]>=temp && i<j){
                j--;
            }
            while (arr[i]<=temp && i<j){
                i++;
            }
            if(i<j){
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }

        }

        arr[left] = arr[i];
        arr[i] = temp;

        aa3(left,i-1);
        aa3(i+1,right);
        return;
    }

    public static void aa2(int left,int right){

        int i,j,t,temp;

        if(left>right)
            return;
        i = left;
        j = right;
        temp = arr[left];

        while (i!=j){
            while (arr[j]>=temp && i<j){
                j--;
            }
            while (arr[i]<=temp && i<j){
                i++;
            }
            if(i<j){
                t= arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }


        }
        arr[left] = arr[i];
        arr[i] = temp;


        aa2(left,i-1);
        aa2(i+1,right);
        return ;
    }


    public static void list(){
        List<String> strList = new ArrayList<>();
        strList.add("111");
        strList.add("333");
        strList.add("222");

        Collections.sort(strList);

        for(String str: strList){
            System.out.println(str);
        }
        System.out.println(strList.isEmpty());

        StringBuffer s = new StringBuffer("sss");
        s.append("ss");

        StringBuilder b = new StringBuilder("dd");
        b.append("dsd");
    }

}
