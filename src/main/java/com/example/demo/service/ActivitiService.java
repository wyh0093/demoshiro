package com.example.demo.service;

import com.example.demo.entityModel.TaskModel;

import java.util.List;

/**
 * Created by qwe on 2019/9/15.
 */
public interface ActivitiService  {
    public byte[] getProcessImage(String processInstanceId) throws Exception;


    public List<TaskModel> findTaskByUserId(int id);

    public String getTaskIdByExecutionId(String executionId);
}
