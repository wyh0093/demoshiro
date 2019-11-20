package com.example.demo.enums;

/**
 * Created by qwe on 2019/8/6.
 */
public enum UserStatus {

    NORMAL("离职"),leave("在职");

    private String str;

    UserStatus(String str){
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
