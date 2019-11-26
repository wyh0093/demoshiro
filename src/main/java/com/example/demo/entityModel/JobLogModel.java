package com.example.demo.entityModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/25 19:01
 **/
public class JobLogModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;  //编号  主键-自增
    private String title; //标题
    private String author; //发布人
    private String content;//发布内容

    private String startTime; //发布开始时间

    private String endTime; //发布结束时间

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
