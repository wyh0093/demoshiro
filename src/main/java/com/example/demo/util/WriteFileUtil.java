package com.example.demo.util;

import java.io.*;
import java.util.Map;

/**
 * @program: demoshiro
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/5 22:27
 **/
public class WriteFileUtil {

    /**
     * 向文件追加信息
     * @param map  信息
     * @param fileName  文件名称
     */
    public static void writeUrl(Map<String, String> map,String fileName){
        try {

            File csv = new File(ConstantUtil.getResourceUrl()+fileName); // CSV数据文件
            if (!csv.exists()) {
                csv.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write(map.get("url")+","+map.get("className")+","+map.get("method")+","+map.get("type"));
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }
}
