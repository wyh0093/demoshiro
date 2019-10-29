package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.PermissionService;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by qwe on 2019/8/2.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    @RequestMapping("/department")
    public String department(){
        return "department";
    }

    @RequestMapping("/403")
    public String noPermission(){
        return "403";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginUrl(HttpServletRequest request, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password
    ,Map<String, Object> map){
// 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        }catch (UnknownAccountException e) {
            map.put("msg", "用户名不正确！");
            return "login";
        }  catch (IncorrectCredentialsException e) {
            map.put("msg", "密码不正确！");
            return "login";
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "login";
        }
        User user = userService.findUserByUserName(username);
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        List<String> permossionStr = permissionService.findPermissionStrByUser(user);
        session.setAttribute("permossionStr",permossionStr);
        return "index";
    }

    //退出操作
//    @ApiOperation(value = "用户退出", notes = "测试用接口")//这个你们不用管
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";

    }
}
