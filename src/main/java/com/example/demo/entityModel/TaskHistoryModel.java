package com.example.demo.entityModel;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by qwe on 2019/8/11.
 */
public class TaskHistoryModel implements Serializable{

    private static final long  serialVersionUID = 1l;

    private String node;

    private String detailPerson;

    private Date detailTime;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getDetailPerson() {
        return detailPerson;
    }

    public void setDetailPerson(String detailPerson) {
        this.detailPerson = detailPerson;
    }

    public Date getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(Date detailTime) {
        this.detailTime = detailTime;
    }
}
