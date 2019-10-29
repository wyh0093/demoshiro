package com.example.demo.service.impl;

import com.example.demo.entity.WorkTask;
import com.example.demo.entityModel.TaskHistoryModel;
import com.example.demo.entityModel.TaskModel;
import com.example.demo.repository.WorkTaskRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.WorkTaskService;
import com.example.demo.util.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/8/11.
 */
@Service
public class WorkTaskServiceImpl implements WorkTaskService {

    @Value("${spring.datasource.jdbc-url}")
    private String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    @Autowired
    private WorkTaskRepository workTaskRepository;
    @Autowired
    private UserService userService;

    @Override
    public void save(WorkTask workTask) {
        workTaskRepository.save(workTask);
    }

    @Override
    public List<TaskModel> findToDoTaskByUserId(Integer userId) {

        List<TaskModel> list = new ArrayList<>();
        String sql ="select ID_,EXECUTION_ID_,name_,w.type,u.c_name from act_ru_task t,work_task w ,user u where\n" +
                " w.execution_id=t.EXECUTION_ID_   and    w.user_id=u.id  and assignee_ =?";
        Connection conn = DatabaseUtil.getConnection(url,username,password);
        PreparedStatement pstat = null;
        ResultSet result = null;
        if(conn!=null){
            try {
                pstat = conn.prepareStatement(sql);
                pstat.setString(1,userId.toString());
                result = pstat.executeQuery();
                ResultSetMetaData rsmd = pstat.getMetaData() ;
                int columnCount = rsmd.getColumnCount();
                while (result.next()){
                    TaskModel taskModel = new TaskModel();
                    taskModel.setTaskId(result.getString(1));
                    taskModel.setExecution_id(result.getString(2));
                    taskModel.setNode(result.getString(3));
                    taskModel.setType(result.getString(4));
                    taskModel.setApplyPerson(result.getString(5));
                    list.add(taskModel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.closePreparedStatement(pstat);
                DatabaseUtil.closeConnection(conn);
            }
        }
        return list;
    }

    @Override
    public List<TaskHistoryModel> findTaskHistory(String executionId) {

        List<TaskHistoryModel> list = new ArrayList<>();
        String sql ="SELECT ACT_NAME_,ASSIGNEE_,END_TIME_ from act_hi_actinst WHERE EXECUTION_ID_=?\n" +
                " and TASK_ID_ is not null  and END_TIME_ is not NULL;";
        Connection conn = DatabaseUtil.getConnection(url,username,password);
        PreparedStatement pstat = null;
        ResultSet result = null;
        if(conn!=null){
            try {
                pstat = conn.prepareStatement(sql);
                pstat.setString(1,executionId);
                result = pstat.executeQuery();
                ResultSetMetaData rsmd = pstat.getMetaData() ;
                int columnCount = rsmd.getColumnCount();
                while (result.next()){
                    TaskHistoryModel taskHistoryModel = new TaskHistoryModel();
                    taskHistoryModel.setNode(result.getString(1));
                    String detailPerson = result.getString(2);
                    String detailPersonCName = "";
                    int userId = 0;
                    if(StringUtils.isEmpty(detailPerson)){
                        userId = this.findByExecutionId(executionId).getUser_id();
                    }else {
                        userId = Integer.parseInt(detailPerson);
                    }
                    detailPersonCName = userService.findUserById(userId).getcName();
                    taskHistoryModel.setDetailPerson(detailPersonCName);
                    taskHistoryModel.setDetailTime(result.getDate(3));
                    list.add(taskHistoryModel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseUtil.closePreparedStatement(pstat);
                DatabaseUtil.closeConnection(conn);
            }
        }
        return list;
    }

    @Override
    public WorkTask findByExecutionId(String executionId) {
        return workTaskRepository.findByExecutionId(executionId);
    }
}
