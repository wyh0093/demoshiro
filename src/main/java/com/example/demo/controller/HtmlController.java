package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/6 15:38
 **/
@Api(description = "跳转到页面")
@Controller
public class HtmlController {
    @ApiOperation(value = "跳转到登录页面")
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @ApiOperation(value = "跳转到首页")
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @ApiOperation(value = "跳转到用户管理页面")
    @RequestMapping("/user")
    public String user(){
        return "user";
    }
    @ApiOperation(value = "跳转到角色管理页面")
    @RequestMapping("/role")
    public String role(){
        return "role";
    }
    @ApiOperation(value = "跳转到部门管理页面")
    @RequestMapping("/department")
    public String department(){
        return "department";
    }
    @RequestMapping("/403")
    public String noPermission(){
        return "403";
    }
}
