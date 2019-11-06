package com.example.demo.util;

import java.io.*;
import java.util.List;
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
     * @param list  信息
     * @param fileName  文件名称
     */
    public static void writeUrl(List<Map<String, String>> list, String fileName){
        try {

            File csv = new File(ConstantUtil.getResourceUrl()+fileName); // CSV数据文件
            if (!csv.exists()) {
                csv.createNewFile();
            }
            //判断文件是否为空，是空时追加内容
            if(csv.length() == 0){
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = list.get(i);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
                    // 添加新的数据行
                    bw.write(map.get("url")+","+map.get("business")+","+map.get("operateType")+","+map.get("methodName")+","+map.get("requestWay")+","+map.get("className"));
                    bw.newLine();
                    bw.close();
                }
            }
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }
}
