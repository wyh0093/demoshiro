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

    private Integer departmentId;//所在部门id

    private Integer status;//状态  (1:在职 0:离职 )
    @Transient
    private String statusCName; //状态名称
    @Transient
    private String departmentName; //所在部门名称
    @Column(name = "creator")
    private String createUserCName; //创建人姓名
    @Column(name = "modifier")
    private String  lastModifyUserCName;//最后修改人姓名

    private String phone; //手机号

    private int jobNumber; //工号

    private String position; //职位

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
