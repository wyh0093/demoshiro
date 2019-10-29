package com.example.demo.entityModel;

import java.io.Serializable;

/**
 * Created by qwe on 2019/6/30.
 */
public class UserModel implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;  //编号  主键-自增

    private String cName; //姓名

    private String userName;//用户名-一般为姓名+编号

    private String password;//密码

    private Integer department;//部门id

    private Integer status;//状态id  (在职 1、离职 0)

    private String statusCName;

    private String departmentName; //部门名称


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatusCName() {
        return statusCName;
    }

    public void setStatusCName(String statusCName) {
        this.statusCName = statusCName;
    }
}
