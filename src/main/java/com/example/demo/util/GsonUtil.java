package com.example.demo.util;

/**
 * Created by qwe on 2019/6/30.
 */
public class GsonUtil {
    //转换成json
    public static String toJson(Object object){
        com.google.gson.Gson gson = new com.google.gson.Gson();
        String str = gson.toJson(object).toString();
        return str;
    }
}
