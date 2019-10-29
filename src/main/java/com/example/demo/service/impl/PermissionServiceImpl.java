package com.example.demo.service.impl;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.RolePermission;
import com.example.demo.entity.User;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RolePermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/7/14.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PermissionRepository permissionRepository;
    @Autowired
    protected RolePermissionRepository rolePermissionRepository;
    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void update(Integer rid, String[] pIds) {

        List<RolePermission> rolePermissionList = rolePermissionRepository.findByRoleId(rid);
        if(rolePermissionList!=null && rolePermissionList.size()>0){
            //先删除,再添加
            for(RolePermission rolePermission : rolePermissionList){

                rolePermissionRepository.delete(rolePermission);
            }

        }

        Role role = roleRepository.getOne(rid);

        if(pIds!=null && pIds.length>0 && role!=null){
            for(String str : pIds){
                Permission permission = permissionRepository.getOne(Integer.parseInt(str));
                if(permission!=null){
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(permission);
                    rolePermissionRepository.save(rolePermission);
                }
            }
        }

    }

    /**
     * 根据登录人获取拥有父权限
     * @param user
     * @return
     */
    @Override
    public List<String> findPermissionStrByUser(User user) {
        List<String> permissions = new ArrayList<String>();
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
        return permissions;
    }

}
