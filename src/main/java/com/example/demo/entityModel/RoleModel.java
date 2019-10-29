package com.example.demo.entityModel;

import java.io.Serializable;

/**
 * Created by qwe on 2019/7/1.
 */
public class RoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;  //编号  主键-自增

    private String cname; //角色名称(中文)

    private String ename; //角色名称(英文)

    private String description; //描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
