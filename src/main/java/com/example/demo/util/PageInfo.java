package com.example.demo.util;

import java.io.Serializable;
import java.util.List;

/**
 * @program: demo
 * @description:
 * @author: wyh
 * @create: 2019/11/18 18:22
 **/
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int count; //总条数

    private int totalPage; //总页数

    private List<T> list;   //结果集




    public PageInfo(List<T> list,int count,int totalPage){
        this.list = list;
        this.count = count;
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
