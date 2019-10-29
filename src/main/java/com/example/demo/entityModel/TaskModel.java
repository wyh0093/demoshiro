package com.example.demo.entityModel;

import java.io.Serializable;

/**
 * Created by qwe on 2019/8/11.
 */
public class TaskModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String taskId;

    private String execution_id;

    private String node;

    private String type;

    private String applyPerson;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecution_id() {
        return execution_id;
    }

    public void setExecution_id(String execution_id) {
        this.execution_id = execution_id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }
}
