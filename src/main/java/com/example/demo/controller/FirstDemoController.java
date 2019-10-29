package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qwe on 2019/8/8.
 */
@Api(description = "测试activity接口")
@RestController
@RequestMapping("/demo")
public class FirstDemoController {



    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/firstDemo",method = RequestMethod.GET)
    public void firstDemo() {

        //根据bpmn文件部署流程
//        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/leave3.bpmn").deploy();

        Deployment deployment = repositoryService.createDeployment()

                .name("请假流程")

                .addClasspathResource("processes/hq4.bpmn")
                .addClasspathResource("processes/hq4.png")

                .deploy();

        //获取流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
//        ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());
        Map<String, Object> variables = new HashMap<String, Object>();

        variables.put("assign","1");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("hq",variables);
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID：" + processId);


        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("第一次执行前，任务名称：" + task.getName());

        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("assignList",list);
        taskService.complete(task.getId(),map2);

//        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println("第二次执行前，任务名称：" + task.getName());
//        taskService.complete(task.getId());
//
//        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        System.out.println("task为null，任务执行完毕：" + task);
    }

    @ApiOperation(value = "查询当前用户任务")
    @RequestMapping(value = "/aa",method = RequestMethod.GET)
    public void aa(){
        String assignee = "12";
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee)//指定查询人

                .list();

        if(taskList.size()>0){

            for (Task task : taskList){


                System.out.println("代办任务ID:"+task.getId());

                System.out.println("代办任务name:"+task.getName());

                System.out.println("代办任务创建时间:"+task.getCreateTime());

                System.out.println("代办任务办理人:"+task.getAssignee());

                System.out.println("流程实例ID:"+task.getProcessInstanceId());

                System.out.println("执行对象ID:"+task.getExecutionId());

                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("boss","100");
                taskService.complete(task.getId(),variables);

            }

        }

    }
}
