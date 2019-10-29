package com.example.demo.entityModel;

import java.io.Serializable;

/**
 * Created by qwe on 2019/6/30.
 */
public class PermissionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;  //编号  主键-自增

    private String name;//名称

    private String level;//级别  一级、二级

    private String url;// url

    private Integer pId;// 父id

    private boolean checked=false; //是否被选中

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }



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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
