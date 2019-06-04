package com.demo.swt.mystudyappshop.bean;

import java.util.List;


public class BaseData<T> {
    protected boolean result;//废弃
    protected String msg;//错误的时候肯定有
    protected String status;//错误的时候肯定有
    protected int flag;//错误码
    protected T data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }




    public boolean isEmptyList(List<?> list){
        return list==null||list.isEmpty();
    }
}
