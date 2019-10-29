package com.example.demo.entityModel;

import java.io.Serializable;

/**
 * Created by qwe on 2019/6/30.
 */
public class ZtreeNode implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id; //id

    private String pid; //父id

    private String name; //名称

    private String url; //url

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
