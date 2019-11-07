package com.example.demo.controller;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.RolePermission;
import com.example.demo.entityModel.PermissionModel;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/6/30.
 */
@Api(description = "权限接口")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @ApiOperation(value = "查询用户拥有的权限")
    @RequestMapping(value = "/userPermission",method = RequestMethod.POST)
    @ResponseBody
    public String userPermission(@RequestParam(name = "id"  ) Integer id){
        Role role = roleService.findRoleById(id);
        List<RolePermission> rolePermissionList = role.getRolePermissions();


        List<Permission> permissionList = permissionService.findAll();

        List<PermissionModel> permissionModelList = new ArrayList<>();
        if(permissionList!=null && permissionList.size()>0){
            for(int i=0;i<permissionList.size();i++){
                PermissionModel model = new PermissionModel();
                BeanUtils.copyProperties(permissionList.get(i), model);
                permissionModelList.add(model);
                //循环该角色拥有的权限，修改选中状态为true
                if(rolePermissionList!=null && rolePermissionList.size()>0){
                    for(RolePermission rolePermission : rolePermissionList){
                        if(rolePermission.getPermission().getId()==permissionList.get(i).getId()){
                            permissionModelList.get(i).setChecked(true);
                        }
                    }
                }

            }
        }
        String str = GsonUtil.toJson(permissionModelList);
        return str;
    }

    @ApiOperation(value = "给角色分配权限")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String save(@RequestParam(name = "rId"  ) Integer rId, @RequestParam(name = "pId"  ) String pId){
        String[] strArr = null;
        if(pId!=null && !"".equals(pId)){
            if(pId.contains(",")){
                strArr = pId.split(",");
            }else {
                strArr = new String[1];
                strArr[0] = pId;
            }
        }
        permissionService.update(rId,strArr);
        return "success";
    }
}
