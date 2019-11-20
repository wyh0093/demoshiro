package com.example.demo.entityModel;

import javax.persistence.Transient;
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

    private Integer departmentId;//部门id

    private Integer status;//状态id  (在职 1、离职 0)

    private String statusCName;

    private String departmentName; //部门名称

    private String createUserCName; //创建人姓名

    private String  lastModifyUserCName;//最后修改人姓名

    private String phone; //手机号

    private int jobNumber; //工号

    private String position; //职位


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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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

    public String getCreateUserCName() {
        return createUserCName;
    }

    public void setCreateUserCName(String createUserCName) {
        this.createUserCName = createUserCName;
    }

    public String getLastModifyUserCName() {
        return lastModifyUserCName;
    }

    public void setLastModifyUserCName(String lastModifyUserCName) {
        this.lastModifyUserCName = lastModifyUserCName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
