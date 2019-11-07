package com.example.demo.controller;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entityModel.ExcelData;
import com.example.demo.entityModel.RoleModel;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.util.ExcelUtils;
import com.example.demo.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qwe on 2019/7/1.
 */
@Api(description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    @ApiOperation(value = "查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pege", value = "是否分页(true:分页查询，false:不分页)", dataType = "boolean", paramType = "query",
                    allowableValues = "true,false",defaultValue = "true")
    })
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    @RequiresPermissions("role:query")
    public Map<String,Object> findAll(@RequestParam(name = "currentPage") Integer currentPage, @RequestParam(name = "size") Integer size,
                          @RequestParam(name = "roleName",required = false) String roleName,
                          @RequestParam(name = "page",required = true) String page,
                                      HttpServletRequest request){

        Role role = null;
        if(roleName!=null && !"".equals(roleName)){
            role = new Role();
            role.setCname(roleName);
        }
        List<RoleModel> roleModels = roleService.findAllCriteria(currentPage,size,role,page);
        Map<String,Object> map = new HashMap<>();
        map.put("content",roleModels);
        map.put("totalCount",roleService.totalCount(role));
        map.put("totalPage",(Integer)((roleService.totalCount(role)/size)+1));
        String[] strArray = new String[roleModels.size()];
        List<String> permossionStr = (List<String>)request.getSession().getAttribute("permossionStr");
        for(int i=0;i<roleModels.size();i++){
            strArray[i]=this.compire(roleModels.get(i).getId(),permossionStr);
        }
        map.put("operate",strArray);
        return map;
    }

    /**
     *
     * 根据权限组织前端权限
     * @param id
     * @param permossionStr
     * @return
     */
    public String compire(Integer id,List<String> permossionStr){
        String str2 = "";
        for(String str:permossionStr) {
            if ("role:update".equals(str)) {
                str2 += "<shiro:hasPermission name=\"role:update\"><a href='javascript:openlayer(2,"+id+")'>修改</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("role:delete".equals(str)){
                str2 += "<shiro:hasPermission name=\"role:delete\"><a href='javascript:del("+id+")'>删除</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("role:allotPermission".equals(str)){
                str2 += "<shiro:hasPermission name=\"role:allotPermission\"><a href='javascript:openPermission("+id+")'>分配权限</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
        }
        return str2;
    }

    @ApiOperation(value = "根据id查找角色信息")
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @RequiresPermissions("role:update")
    public RoleModel findOneById(@RequestParam(name = "id") Integer id){

        Role role = roleService.findOne(id);
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(role,roleModel);
        return roleModel;
    }

    @ApiOperation(value = "创建角色")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions("role:add")
    public String save(RoleModel roleModel){

        Role role = new Role();
        BeanUtils.copyProperties(roleModel,role);
        roleService.save(role);
        return "success";
    }

    @ApiOperation(value = "更新角色")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("role:update")
    public String update(RoleModel roleModel){

        Role role = new Role();
        BeanUtils.copyProperties(roleModel,role);
        roleService.save(role);
        return "success";
    }

    @ApiOperation(value = "删除角色")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @RequiresPermissions("role:delete")
    public String del(@RequestParam(name = "rid") Integer rid){
        roleService.del(rid);
        return "success";
    }




    @ApiOperation(value = "导出角色信息")
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    @RequiresPermissions("role:export")
    public void export(HttpServletResponse response, @RequestParam(name = "roleName",required = false) String roleName){
        Role role = null;
        if(roleName!=null && !"".equals(roleName)){
            role = new Role();
            role.setCname(roleName);
        }
        List<RoleModel> list = roleService.findAllCriteria(0,0,role,"false");
        int rowIndex = 0;
        ExcelData data = new ExcelData();
        data.setName("角色信息");
        List<String> titles = new ArrayList();
        titles.add("序号");
        titles.add("角色名称");
        titles.add("角色描述");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            RoleModel roleModel = list.get(i);
            List<Object> row = new ArrayList();
            row.add((i+1));
            row.add(roleModel.getCname());
            row.add(roleModel.getDescription());
            rows.add(row);
        }
        data.setRows(rows);
        String fileName = "角色信息";
        try{
            ExcelUtils.exportExcel(response,fileName,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
