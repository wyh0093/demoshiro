package com.example.demo.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2019/8/2.
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //编号  主键-自增

    private String cName; //姓名

    private String userName;//用户名-一般为姓名+编号

    private String password;//密码

    private Integer department;//部门id

    private Integer status;//状态  (在职 1、离职 0)
    @Transient
    private String statusCName; //状态名称

    private String departmentName; //部门名称
    @CreatedBy
    private Integer createUserId; //创建用户id
    @LastModifiedBy
    private Integer lastModifyUserId; //最后修改用户ID
    @Transient
    private String createUserCName; //创建人姓名
    @Transient
    private String  lastModifyUserCName;//最后修改人姓名


    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getLastModifyUserId() {
        return lastModifyUserId;
    }

    public void setLastModifyUserId(Integer lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
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

    public String getStatusCName() {
        return statusCName;
    }

    public void setStatusCName(String statusCName) {
        this.statusCName = statusCName;
    }
}
