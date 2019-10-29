package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.entityModel.ExcelData;
import com.example.demo.entityModel.UserModel;
import com.example.demo.enums.UserStatus;
import com.example.demo.service.DepartmentService;
import com.example.demo.util.ExcelUtils;
import com.example.demo.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Api(description = "部门接口")
@RestController
@RequestMapping("/depart")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "分页查询部门")
    @RequestMapping(value = "/findAllBypage",method = RequestMethod.POST)
    @RequiresPermissions("department:query")
    public Map<String, Object> findAllBypage(@RequestParam(name = "currentPage") Integer currentPage, @RequestParam(name = "size") Integer size,
                                          @RequestParam(name = "keyword",required = false) String keyword, HttpServletRequest request){

        Pageable Pageable = PageRequest.of(currentPage - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Department> list  = departmentService.findAll(Pageable,keyword,true);
        //获取该用户拥有权限
        List<String> permossionStr = (List<String>)request.getSession().getAttribute("permossionStr");
        String[] strArray = new String[list.size()];
        for(int i=0;i<list.size();i++){
            strArray[i]=this.operation(list.get(i).getId(),permossionStr);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("content",list);
        map.put("totalCount",list.size());
        map.put("totalPage",(Integer)(list.size()/size)+1);
        map.put("operate",strArray);

        return map;
    }

    public String operation(Integer id,List<String> permossionStr){

        String str2 = "";
        for(String str:permossionStr) {
            if ("user:update".equals(str)) {
                str2 += "<shiro:hasPermission name=\"department:update\"><a href='javascript:openlayer(2,"+id+")'>修改</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("user:delete".equals(str)){
                str2 += "<shiro:hasPermission name=\"department:delete\"><a href='javascript:del("+id+")'>删除</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
        }
        return str2;
    }

    @ApiOperation(value = "根据id查询部门")
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @RequiresPermissions("department:update")
    public Department findById(@RequestParam(name = "id") Integer id){
        return departmentService.findById(id);
    }

    @ApiOperation(value = "添加部门")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions("department:add")
    public String save(Department department){
        departmentService.save(department);
        return "success";
    }

    @ApiOperation(value = "修改部门")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("department:update")
    public String update(Department department){
        departmentService.save(department);
        return "success";
    }

    @ApiOperation(value = "删除部门")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("department:delete")
    public String delete(@RequestParam(name = "id") Integer id){
        departmentService.deleteById(id);
        return "success";
    }


    @ApiOperation(value = "导出信息")
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    @RequiresPermissions("department:export")
    public void export(HttpServletResponse response, @RequestParam(name = "keyword",required = false) String keyword){

        List<Department> list = departmentService.findAll(null,keyword,false);

        ExcelData data = new ExcelData();
        List<List<Object>> rows = new ArrayList();
        for(int i = 0, length = list.size();i<length;i++){
            Department department = list.get(i);
            List<Object> row = new ArrayList();
            row.add(i+1);
            row.add(department.getName());
            row.add(department.getDescript());
            rows.add(row);
        }
        data.setRows(rows);
        String fileName = "部门信息";
        data.setName("部门信息");
        List<String> titles = new ArrayList();
        titles.add("序号");
        titles.add("部门名称");
        titles.add("部门描述");
        data.setTitles(titles);
        try{
            ExcelUtils.exportExcel(response,fileName,data);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @ApiOperation(value = "查询所有部门")
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public List<Department> findAll(){
        List<Department> list = departmentService.findAll(null,null,false);
        return list;
    }






}
