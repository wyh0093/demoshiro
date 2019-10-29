package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by qwe on 2019/8/15.
 */
@Entity
@Table(name = "activity_type")
public class ActivityType implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  //编号  主键-自增

    private String ac_name; //流程名称

    private String ac_bpmnName; //bpmn名称

    private String img_name; //图片名称

    private String ac_Key; //流程的key

    public String getAc_name() {
        return ac_name;
    }

    public void setAc_name(String ac_name) {
        this.ac_name = ac_name;
    }

    public String getAc_bpmnName() {
        return ac_bpmnName;
    }

    public void setAc_bpmnName(String ac_bpmnName) {
        this.ac_bpmnName = ac_bpmnName;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getAc_Key() {
        return ac_Key;
    }

    public void setAc_Key(String ac_Key) {
        this.ac_Key = ac_Key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
