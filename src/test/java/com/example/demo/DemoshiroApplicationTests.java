package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.entityModel.TaskModel;
import com.example.demo.util.ConstantUtil;
import com.example.demo.util.DatabaseUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoshiroApplicationTests {

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	@Value("${spring.datasource.jdbc-url}")
	public String url;

	@Value("${spring.datasource.username}")
	public String username;

	@Value("${spring.datasource.password}")
	public String password;

	@Test
	public void contextLoads() {
	}
	@Test
	public void deploy(){
		Deployment deployment = repositoryService.createDeployment()

				.name("请假流程")

				.addClasspathResource("processes/leave.bpmn")

				.addClasspathResource("processes/leave.png")

				.deploy();


		deployment.getKey();
	}
	@Test
	public void start(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("apply",2);
		//请假天数小于3天
		variables.put("flag","false");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("leave2",variables);
		String processId = pi.getId();
		System.out.println("流程创建成功，当前流程实例ID：" + processId);


		Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
		System.out.println("第一次执行前，任务名称：" + task.getName());
		//获取本部门项目经理
		variables = new HashMap<String, Object>();
		variables.put("manager",3);
		taskService.complete(task.getId(),variables);
		String executionId = task.getExecutionId();
	}

	@Test
	public void Deal(){
		Map<String, Object> variables = new HashMap<String, Object>();
		String taskid = "32503";
		variables.put("flag","true");
//		variables.put("boss",5);
		taskService.complete(taskid,variables);
	}
	@Test
	public void applyAgain(){
		Map<String, Object> variables = new HashMap<String, Object>();
		String taskid = "10003";
		variables.put("flag","false");
		variables.put("manager",3);
		taskService.complete(taskid,variables);
	}
	//终止任务
	@Test
	public void destoryTask(){
		String processId = "37501";  //为act_ru_task表中的 PROC_INST_ID_ 字段
		runtimeService.deleteProcessInstance(processId, "结束");
	}

	//获取待办任务
	@Test
	public void waitTask(){
			List<TaskModel> list = new ArrayList<>();
			Connection conn = null;
			PreparedStatement prestate = null;
			ResultSet rest = null;
			int userId = 2;
			String sql = "select ID_,EXECUTION_ID_,NAME_,ASSIGNEE_ from act_ru_task where ASSIGNEE_ = ?";
			try {
				conn =  DatabaseUtil.getConnection(url,username,password);
				prestate = conn.prepareStatement(sql);
				prestate.setInt(1,userId);
				rest = prestate.executeQuery();
				while (rest.next()){
					TaskModel taskModel = new TaskModel();
					taskModel.setTaskId(rest.getString(1));
					taskModel.setExecution_id(rest.getString(2));
					taskModel.setNode(rest.getString(3));
					taskModel.setAssign(rest.getInt(4));
					list.add(taskModel);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		for (int i = 0; i < list.size(); i++) {
			TaskModel taskModel = list.get(i);
			System.out.println("获取待办任务：------------"+taskModel.getTaskId()+","+taskModel.getExecution_id()+","+taskModel.getNode()+","
					+taskModel.getAssign());
		}
//			return list;
	}

	@Test
	public void getResourcePath(){

		System.out.println(System.getProperty("user.dir"));

		try {
			String path2 = Class.class.getResource("/").toURI().getPath();
			path2 = path2.substring(1);
			System.out.println("path2: "+path2);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


		String aa = ConstantUtil.getResourceUrl();
		System.out.println(aa);
	}

	@Test
	public void writeUrl(){
		try {

			File csv = new File(ConstantUtil.getResourceUrl(),"mappingurl.csv"); // CSV数据文件
			if (!csv.exists()) {
				csv.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
			// 添加新的数据行
			bw.write("\"张三\"" + "," + "\"2000\"" + "," + "\"2004\"");
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			// File对象的创建过程中的异常捕获
			e.printStackTrace();
		} catch (IOException e) {
			// BufferedWriter在关闭对象捕捉异常
			e.printStackTrace();
		}
	}


	@Autowired
	private HttpServletRequest request;
	//获取登登录者的ip
	@Test
	public void getCliectIp() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		User user = (User)request.getSession().getAttribute("user");
		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		System.out.println("ip----"+ip);
	}

}
