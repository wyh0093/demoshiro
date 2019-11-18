package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entityModel.ExcelData;
import com.example.demo.entityModel.RoleModel;
import com.example.demo.entityModel.UserModel;
import com.example.demo.enums.UserStatus;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.ExcelUtils;
import com.example.demo.util.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qwe on 2019/6/27.
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private DepartmentRepository departmentRepository;

    @ApiOperation(value = "根据id查询用户")
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @RequiresPermissions("user:update")
    public UserModel findById(@RequestParam(name = "id") Integer id){

        User user = userService.findUserById(id);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user,userModel);

        return userModel;
    }


    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pege", value = "是否分页(true:分页查询，false:不分页)", dataType = "boolean", paramType = "query",
                    allowableValues = "true,false",defaultValue = "true")
    })
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    @RequiresPermissions("user:query")
    public Map<String,Object> findAll(HttpServletRequest request,@RequestParam(name = "currentPage") Integer currentPage, @RequestParam(name = "size") Integer size,
                                      @RequestParam(name = "keyword",required = false) String keyword,
                                      @RequestParam(name = "status",required = false) Integer status){

        Pageable Pageable = PageRequest.of(currentPage - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        PageInfo<User> pageInfo =  userService.findAll(Pageable,keyword,status,true);
//        int totalCount = userService.findAll(Pageable,keyword,status,false).size();
        int totalPage =  (Integer)((pageInfo.getCount()/size)+1);
        Map<String,Object> map = new HashMap<>();
        map.put("content",pageInfo.getList());
        map.put("totalCount",pageInfo.getCount());
        map.put("totalPage",totalPage);
        String[] strArray = new String[pageInfo.getCount()];
        List<String> permossionStr = (List<String>)request.getSession().getAttribute("permossionStr");
        for(int i=0;i<pageInfo.getCount();i++){
            strArray[i]=this.compire(pageInfo.getList().get(i).getId(),permossionStr);
        }
        map.put("operate",strArray);
        return map;
    }

    public String compire(Integer id,List<String> permossionStr){

        String str2 = "";
        for(String str:permossionStr) {
            if ("user:update".equals(str)) {
                str2 += "<shiro:hasPermission name=\"user:update\"><a href='javascript:openlayer(2,"+id+")'>修改</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("user:delete".equals(str)){
                str2 += "<shiro:hasPermission name=\"user:delete\"><a href='javascript:del("+id+")'>删除</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("user:allotRole".equals(str)){
                str2 += "<shiro:hasPermission name=\"user:allotRole\"><a href='javascript:openRole("+id+")'>分配角色</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
        }
        return str2;
    }

    /**
     * 获取部门对应信息
     * @param keyword
     * @return
     */
    public String getDepart(String keyword){
        String depart = "";
        if(!StringUtils.isEmpty(keyword)){
            List<Department> DepartmentList = departmentRepository.findByCName(keyword);
            if(DepartmentList!=null && DepartmentList.size()>0){
                for(Department department: DepartmentList){
                    depart+=department.getId()+",";
                }
                depart = depart.substring(0,depart.length()-1);
            }
        }
        return depart;
    }


    @ApiOperation(value = "导出用户信息")
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    @RequiresPermissions("user:export")
    public void export(HttpServletResponse response, @RequestParam(name = "keyword",required = false) String keyword,
                       @RequestParam(name = "status",required = false) Integer status){

//        List<UserModel> list = userService.findAll(null,keyword,status,false);

        PageInfo<User> pageInfo = userService.findAll(null,keyword,status,false);
        List<User> list = pageInfo.getList();
        ExcelData data = new ExcelData();

        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            User user = list.get(i);
            List<Object> row = new ArrayList();
            row.add((i+1));
            row.add(user.getcName());
            row.add(user.getDepartmentName());
            String statusStr = "";
            for(UserStatus userStatus :UserStatus.values()){
                if(user.getStatus()==userStatus.ordinal()){
                    statusStr = userStatus.getStr();
                }
            }
            row.add(statusStr);
            rows.add(row);
        }

        data.setRows(rows);
        String fileName = "用户信息";
        data.setName("用户信息");
        List<String> titles = new ArrayList();
        titles.add("序号");
        titles.add("用户名称");
        titles.add("用户部门");
        titles.add("是否在职");
        data.setTitles(titles);
        try{
            ExcelUtils.exportExcel(response,fileName,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "创建用户")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions("user:add")
    public String save(UserModel userModel){

        userModel.setStatus(0);
        userModel.setPassword("123");
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        User user2 = userService.save(user);
        //存入登录名
       /* int id= user2.getId();
        StringBuilder str = new StringBuilder();
        String username = user2.getcName();
        str.append(user2.getcName());
        if(id<10){
            str.append("00"+user2.getId());
        }else if(id<100){
            str.append("0"+user2.getId());
        }
        user2.setUserName(str.toString());
        userService.save(user2);*/

        saveUserName(user2);

        return "success";
    }

    public void saveUserName(User user2){
        int id= user2.getId();
        StringBuilder str = new StringBuilder();
        String username = user2.getcName();
        str.append(user2.getcName());
        if(id<10){
            str.append("00"+user2.getId());
        }else if(id<100){
            str.append("0"+user2.getId());
        }
        user2.setUserName(str.toString());
        userService.save(user2);
    }

    @ApiOperation(value = "更新用户")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("user:update")
    public String update(UserModel userModel){

        User user = userService.findUserById(userModel.getId());
        user.setcName(userModel.getcName());
        user.setDepartment(userModel.getDepartment());
        user.setDepartmentName(userModel.getDepartmentName());
        userService.save(user);
        this.saveUserName(user);
        return "success";
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @RequiresPermissions("user:delete")
    public String del(@RequestParam(name = "uid") Integer uid){
        userService.del(uid);
        return "success";
    }


    @ApiOperation(value = "根据id查找角色信息")
    @RequestMapping(value = "/findRolesByuserId",method = RequestMethod.POST)
    public List<RoleModel> findRolesByuserId(@RequestParam(name = "uid") Integer uid){

        List<RoleModel> roleModelList = new ArrayList<>();
        User user = userService.findUserById(uid);
        if(user!=null && !user.getRoles().isEmpty()){
            List<Role> roleList = user.getRoles();
            for(int i=0;i<roleList.size();i++){
                RoleModel roleModel = new RoleModel();
                BeanUtils.copyProperties(roleList.get(i),roleModel);
                roleModelList.add(roleModel);
            }

        }
        return roleModelList;

    }

    @ApiOperation(value = "给用户分配角色")
    @RequestMapping(value = "/allotRole",method = RequestMethod.POST)
    @RequiresPermissions("user:allotRole")
    public String allotRole(@RequestParam(name = "uid") Integer uid,@RequestParam(name = "rid") String rid){


        User user = userService.findUserById(uid);
        List<Role> roles = new ArrayList<>();
        String[] strArr = null;
        if(rid.contains(",")){
            strArr = rid.split(",");
        }else {
            strArr = new String[1];
            strArr[0] = rid;
        }
        for(String id : strArr){
            Role role = roleService.findRoleById(Integer.parseInt(id));
            roles.add(role);
        }
        user.setRoles(roles);
        userService.save(user);

        return "success";
    }

}


