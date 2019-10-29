package com.example.demo.service;

import com.example.demo.entity.WorkTask;
import com.example.demo.entityModel.TaskHistoryModel;
import com.example.demo.entityModel.TaskModel;

import java.util.List;

/**
 * Created by qwe on 2019/8/11.
 */
public interface WorkTaskService {

    public void save(WorkTask workTask);

    List<TaskModel> findToDoTaskByUserId(Integer userId);


    List<TaskHistoryModel> findTaskHistory(String executionId);

    WorkTask findByExecutionId(String executionId);

}
