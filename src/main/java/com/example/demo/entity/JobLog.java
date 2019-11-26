package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/25 18:26
 **/
@Entity
@Table(name = "job_log")
public class JobLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //编号  主键-自增

    private String title; //标题

    private String author; //发布人
    @Column(length = 1028)
    private String content;//发布内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
