package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by qwe on 2019/8/8.
 */
@Entity
@Table(name = "work_task")
public class WorkTask implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //编号  主键-自增

    private Integer user_id; //人员id

    private String execution_Id;//任务的execution_id

    private Integer days; //天数

    private String descript; //描述

    private String status; //状态（待办、完结）

    private String type; //流程类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getExecution_Id() {
        return execution_Id;
    }

    public void setExecution_Id(String execution_Id) {
        this.execution_Id = execution_Id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
