package com.example.demo.entity;

import org.apache.ibatis.annotations.Update;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/16 10:58
 **/
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "test_a")
@Inheritance(strategy = InheritanceType.JOINED)
public class TestA implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    private String name;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
