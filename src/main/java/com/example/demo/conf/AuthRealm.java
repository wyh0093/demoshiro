package com.example.demo.conf;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.RolePermission;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/8/2.
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean isAuthorizationCachingEnabled() {
        return false;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        List<String> permissions = new ArrayList<String>();
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findUserByUserName(username);
        if(user!=null){
            List<Role> roleList = user.getRoles();
            if(roleList!=null && roleList.size()>0){
                for (Role role :roleList){
                    List<RolePermission> permissionList = role.getRolePermissions();
                    for(RolePermission rolePermission: permissionList){
                        String str = rolePermission.getPermission().getPermission();
                        permissions.add(str);
                    }
                }
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
//        SecurityUtils.getSubject().getSession().setAttribute("user",user);
//        SecurityUtils.getSubject().getSession().setAttribute("permossionStr",permissions);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken)token;//获取用户输入的token
        String username = utoken.getUsername();
        String password = new String((char[]) utoken.getCredentials());
//            User user = service.findUserByName(username);

        User user = userService.findUserByUserName(username);
        if(user==null){
            throw new UnknownAccountException();
        }
        User user2 = userService.findUserByUserNamePassword(username,password);
        if(user2==null){
            throw new IncorrectCredentialsException();
        }
        if(user2!=null){
            return new SimpleAuthenticationInfo(username, password,getName());
        }
        return null;
    }
}
