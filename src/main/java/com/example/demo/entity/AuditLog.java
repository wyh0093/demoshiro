package com.example.demo.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: demo
 * @description:
 * @author: Yunhuan Wang
 * @create: 2019/11/5 17:08
 **/
@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //部门编号  主键-自增

    private String username; //登录人名称

    private String ip; //登录机器的ip

    private String business; //业务模块

    private String operateType; //操作类型
    @CreatedDate
    private Date createTime; //开始时间
    @LastModifiedDate
    private Date endTime; //结束时间

    private String descript; //操作描述

    private String result; // 结果：(success|failed)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
