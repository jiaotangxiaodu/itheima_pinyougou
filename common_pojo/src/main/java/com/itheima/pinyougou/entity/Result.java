package com.itheima.pinyougou.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;
    private String info;

    public Result() {
    }

    public Result(boolean success, String info) {
        this.success = success;
        this.info = info;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
