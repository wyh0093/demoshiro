package com.example.demo.util;/**
 * Created by qwe on 2019/11/4.
 */

import com.example.demo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demoshiro
 * @description:
 * @author: Zonglin Yue
 * @create: 2019-11-04 14:33
 **/
public class ConstantUtil {


    public static Map<String,String> activityKey = new HashMap<>();

    public static User user = new User();

    public static String getResourceUrl(){
        String path = "";
        try {
            path = Class.class.getResource("/").toURI().getPath();
            path = path.substring(1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

}
