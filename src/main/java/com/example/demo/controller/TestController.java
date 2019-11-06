package com.example.demo.controller;

import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qwe on 2019/8/2.
 */
@Api(description = "测试接口")
@Controller
@RequestMapping("/test")
public class TestController {

    @Value("${spring.datasource.jdbc-url}")
    private String username;

    @Autowired
    private UserService userService;

    @RequestMapping("/aa")
    @ResponseBody
    public String aa(){
        userService.findUserById(1);
        System.out.println(username);
        return "aa";
    }

    @RequestMapping("/cc")
    @ResponseBody
    @RequiresPermissions("test:cc")
    public String cc(){
        return "cc";
    }


}
