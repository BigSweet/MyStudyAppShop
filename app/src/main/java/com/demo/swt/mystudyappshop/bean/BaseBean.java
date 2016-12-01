package com.demo.swt.mystudyappshop.bean;

import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class BaseBean<T> {

    private int status;
    private List<T> data;
    private String msg;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
