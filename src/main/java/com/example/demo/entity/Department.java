package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by qwe on 2019/6/29.
 */
@Entity
@Table(name = "department")
public class Department implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //部门编号  主键-自增

    private String name; //部门名称

    private String descript; //部门描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
