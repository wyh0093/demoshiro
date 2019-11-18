package com.example.demo.controller;

import com.example.demo.entity.ActiviHistory;
import com.example.demo.entity.User;
import com.example.demo.service.ActivityHistoryService;
import com.example.demo.service.UserService;
import com.example.demo.util.ConstantUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demoshiro
 * @description: 请假工作流
 * @author: wyh
 * @create: 2019/11/10 22:15
 **/
@Api(description = "请假流程")
@RestController
@RequestMapping("/leaveActivity")
public class LeaveActivityController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityHistoryService activityHistoryService;

    @ApiOperation(value = "开启任务实例")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void start(@RequestParam(name = "flag") String flag, @RequestParam(name = "size") Integer size,
                      @RequestParam(name = "keyword",required = false) String keyword,
                      @RequestParam(name = "page",required = true) String page,
                      @RequestParam(name = "day",required = true) int day,
                      HttpServletRequest request){
        Map<String, Object> variables = new HashMap<String, Object>();
        //获取当前登录人
        User user = (User)request.getSession().getAttribute("user");
        variables.put("apply",user.getId());
        //定义是否大于3天标志
        String sign = "flse";
        //请假天数是否小于3天
        if(day>3){
            sign = "true";
        }
        variables.put("flag",sign);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("leave",variables);
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID：" + processId);


        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("第一次执行前，任务名称：" + task.getName());
        variables = new HashMap<String, Object>();
        //----------判断如果小于等于3天，提交给项目经理；大于3天，提交给总经理
        if("false".equals(sign)){
            //获取本部门项目经理
            User user2 = userService.findManagerByDepart(user.getDepartment());
            variables.put("manager",user2.getId());
        }else {
            //获取总经理
            User user2 = userService.findBoss();
            variables.put("boss",user2.getId());
        }
        taskService.complete(task.getId(),variables);
        String executionId = task.getExecutionId();
        //保存流程历史
        ActiviHistory history = new ActiviHistory();
        history.setExecutionId(executionId);
        history.setNode(ConstantUtil.leaveStart);
        history.setOperateTime(new Date());
        history.setDealPerson(user.getcName());
        history.setDealOpinion(ConstantUtil.leaveStart);
        activityHistoryService.save(history);

    }

    @ApiOperation(value = "处理任务")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void deal(@RequestParam(name = "taskId",required = true) String taskId, @RequestParam(name = "executionId",required = true) String executionId,
                      @RequestParam(name = "nodeName",required = true) String nodeName,
                      @RequestParam(name = "dealOpinion",required = true) String dealOpinion,
                     @RequestParam(name = "flag",required = false) String flag,
                     HttpServletRequest request){

        //获取当前登录人
        User user = (User)request.getSession().getAttribute("user");
        Map<String, Object> variables = new HashMap<String, Object>();
        if(ConstantUtil.leaveManagerApproval.equals(nodeName)){
            //项目经理审批

            //获取总经理
            User user2 = userService.findBoss();
            variables.put("boss",flag);
            //判断经理审批是否通过  yes:提交给总经理 no：退回给申请人
            if("false".equals(flag)){
                variables.put("boss",user2.getId());
            }else {
                //获取当前任务的申请人
                variables.put("boss",user2.getId());
            }
        }else if(ConstantUtil.leaveBossApproval.equals(nodeName)){
            //总经理审批


        }else if(ConstantUtil.leaveEnd.equals(nodeName)){
            //申请人办结

        }
        taskService.complete(taskId,variables);
        //保存流程历史
        ActiviHistory history = new ActiviHistory();
        history.setExecutionId(executionId);
        history.setNode(ConstantUtil.leaveStart);
        history.setOperateTime(new Date());
        history.setDealPerson(user.getcName());
        history.setDealOpinion(ConstantUtil.leaveStart);
        activityHistoryService.save(history);

    }
}
