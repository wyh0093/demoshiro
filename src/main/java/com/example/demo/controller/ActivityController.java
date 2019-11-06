package com.example.demo.controller;

import com.example.demo.entity.ActivityType;
import com.example.demo.entity.User;
import com.example.demo.entity.WorkTask;
import com.example.demo.entityModel.TaskHistoryModel;
import com.example.demo.entityModel.TaskModel;
import com.example.demo.entityModel.WorkTaskModel;
import com.example.demo.service.ActivitiService;
import com.example.demo.service.ActivityTypeService;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

/**
 * Created by qwe on 2019/8/10.
 */
@Api(description = "工作流管理")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private WorkTaskService workTaskService;

    @Autowired
    private ActivityTypeService activityTypeService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private ActivitiService activitiService;

    @ApiOperation(value = "部署流程")
    @RequestMapping(value = "/deploy",method = RequestMethod.POST)
    public void startLeave( @RequestParam(name = "name",required = true) String acName,
                            @RequestParam(name = "bpmnName",required = true) String bpmnName,
                            @RequestParam(name = "imgName",required = true) String imgName,
                            @RequestParam(name = "acKey",required = true) String acKey){

        Deployment deployment = repositoryService.createDeployment()

                .name(acName)

                .addClasspathResource("processes/"+bpmnName)

                .addClasspathResource("processes/"+imgName)

                .deploy();


        deployment.getKey();
        //保存流程部署信息
        ActivityType activityType = new ActivityType();
        activityType.setAc_bpmnName(bpmnName);
        activityType.setAc_name(acName);
        activityType.setImg_name(imgName);
        activityType.setAc_Key(acKey);
        activityTypeService.save(activityType);


    }

    @ApiOperation(value = "获取所有流程类型")
    @RequestMapping(value = "/allType",method = RequestMethod.GET)
    public List<ActivityType> getAllActivityType(){
        return activityTypeService.findAll();
    }

    @ApiOperation(value = "开启流程并保存流程历史")
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public void startLeave(HttpServletRequest request, @RequestBody WorkTaskModel worktask,
                           @RequestParam(name = "acTypeId",required = true) Integer acTypeId){

        //根据id获取type信息
        ActivityType activityType = activityTypeService.getOne(acTypeId);



        ProcessInstance pi = runtimeService.startProcessInstanceByKey(activityType.getAc_Key());
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID：" + processId);


        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("第一次执行前，任务名称：" + task.getName());
        User user = (User)request.getSession().getAttribute("user");
        //获取本部门项目经理
        User user2 = userService.findManagerByDepart(user.getDepartment());
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("manager",user2.getId());
        taskService.complete(task.getId(),variables);
        String executionId = task.getExecutionId();

        //保存请假信息
        worktask.setExecution_Id(executionId);
        worktask.setUser_id(user.getId());
        worktask.setStatus("在办");
        worktask.setType(activityType.getAc_name());
        WorkTask workTask2 = new WorkTask();
        BeanUtils.copyProperties(worktask,workTask2);
        workTaskService.save(workTask2);

    }


    @ApiOperation(value = "获取待办任务")
    @RequestMapping(value = "/toDotask",method = RequestMethod.POST)
    public List<TaskModel> findToDoTask(@RequestParam(name = "userId",required = true) Integer userId){
        return workTaskService.findToDoTaskByUserId(userId);
    }

    @ApiOperation(value = "获取流程历史")
    @RequestMapping(value = "taskHistory",method = RequestMethod.POST)
    public List<TaskHistoryModel> findTaskHistory(@RequestParam(name = "executionId",required = true) String executionId){
        return workTaskService.findTaskHistory(executionId);
    }


    @ApiOperation(value = "处理任务")
    @RequestMapping(value = "/completetask",method = RequestMethod.POST)
    public void completetask(@RequestParam(name = "taskId",required = true) String taskId,
                             @RequestParam(name = "executionId",required = true) String executionId,
                             @RequestParam(name = "node",required = true) String node,
                             HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        WorkTask workTask = workTaskService.findByExecutionId(executionId);
        switch (node){
            case "经理审批":
                User user2 = userService.findBoss();
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("boss",user.getId());
                taskService.complete(taskId,variables);
                break;
            case "总经理审批":
                if("请假流程".equals(workTask.getType())){
                    taskService.complete(taskId);
                    workTask.setStatus("办结");
                }else if("离职流程".equals(workTask.getType())){
                    User user3 = userService.findManagerByDepart(user.getDepartment());
                    Map<String, Object> variables3 = new HashMap<String, Object>();
                    variables3.put("personnel",user.getId());
                    taskService.complete(taskId,variables3);
                }
                workTaskService.save(workTask);
                break;
            case  "人事部":
                taskService.complete(taskId);
                workTask.setStatus("办结");
                break;
            default:
                System.out.println("111");
        }


    }



    @ApiOperation(value = "获取流程图片")
    @RequestMapping("/viewProcessImg")
    public void viewProcessImg(HttpServletResponse response) throws Exception {

        try {
            byte[] processImage = activitiService.getProcessImage("95011");

            OutputStream outputStream = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(processImage);
            IOUtils.copy(in, outputStream);
            IOUtils.write(processImage,outputStream);
            // 输出资源内容到相应对象
//            byte[] b = new byte[1024];
//            int len;
//            while ((len = in.read(b, 0, 1024)) != -1) {
//                response.getOutputStream().write(b, 0, len);
//            }
        } catch (Exception e) {
            System.out.println("viewProcessImg---- {}");
//            logger.error("viewProcessImg---- {}", ExceptionUtil.stackTraceText(e));
        }

    }

    public InputStream getDiagram(String processInstanceId) {
        //获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = StringUtils.EMPTY;
        if (processInstance == null) {
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory =
                    historyService.createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
            if (processInstanceHistory == null)
                return null;
            else
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        //使用宋体
        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.EMPTY_LIST;
        if (processInstance != null)
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());

        return processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<String>(),
                        fontName, fontName, fontName, null, 1.0);
    }

    //查看当前节点所在图
    @RequestMapping("/image")
    public void image(HttpServletResponse response) {
        try {
            //此时的id为：proc_inst_id_
            InputStream is = this.getDiagram("97510");
            if (is == null)
                return;

            response.setContentType("image/png");
            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception ex) {
            System.out.println("查看流程图失败");
        }
    }

}
