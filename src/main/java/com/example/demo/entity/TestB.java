package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/16 11:03
 **/
@Entity
@Table(name = "test_b")
public class TestB extends TestA implements Serializable {
    private static final long serialVersionUID = 1L;


    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
