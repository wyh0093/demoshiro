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
 * @author: wyh
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

    //请假流程的节点名称
    //----------start-------------------------
    public static final String leaveStart = "申请";
    public static final String leaveManagerApproval = "经理审批";
    public static final String leaveBossApproval = "总经理审批";
    public static final String leaveEnd = "办结";
    //----------end---------------------------


    /**
     * 根据executionId获取申请人信息
     * @param executionId
     * @return
     */
    public User getApplyPerson(String executionId) {

        return null;
    }
}
