package com.pinyougou.entries;

import java.io.Serializable;

public class ResultInfo implements Serializable {
    private boolean success;
    private String info;

    public ResultInfo() {
    }

    public ResultInfo(boolean success, String info) {
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
