package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.entity.JobLog;
import com.example.demo.entityModel.JobLogModel;
import com.example.demo.service.JobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/25 19:10
 **/
@Api(description = "工作日志")
@RestController
@RequestMapping(value = "/jobLog")
public class JobLogController {

    @Autowired
    private JobLogService jobLogService;

    @ApiOperation(value = "查询工作日志")
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    @RequiresPermissions("joblog:query")
    public Map<String, Object> findAll(@RequestParam(name = "currentPage") Integer currentPage, @RequestParam(name = "size") Integer size,
                                       @RequestParam(name = "title",required = false) String title,
                                       @RequestParam(name = "author",required = false) String author,
                                       @RequestParam(name = "startTime",required = false) String startTime,
                                       @RequestParam(name = "endTime",required = false) String endTime,
                                       @RequestParam(name = "page",required = true) String page, HttpServletRequest request){

        Pageable Pageable = PageRequest.of(currentPage - 1, size, Sort.by(Sort.Direction.ASC, "id"));

        JobLogModel log = new JobLogModel();
        log.setAuthor(author);
        log.setTitle(title);
        log.setStartTime(startTime);
        log.setEndTime(endTime);

        List<JobLog> list = jobLogService.findAll(currentPage,size,log,page);

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

    /**
     *
     * 根据权限组织前端权限
     * @param id
     * @param permossionStr
     * @return
     */
    public String operation(Integer id,List<String> permossionStr){
        String str2 = "";
        for(String str:permossionStr) {
            if ("role:update".equals(str)) {
                str2 += "<shiro:hasPermission name=\"role:update\"><a href='javascript:openlayer(2,"+id+")'>修改</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
            if("role:delete".equals(str)){
                str2 += "<shiro:hasPermission name=\"role:delete\"><a href='javascript:del("+id+")'>删除</a>&nbsp;&nbsp;</shiro:hasPermission>";
            }
        }
        return str2;
    }


}
