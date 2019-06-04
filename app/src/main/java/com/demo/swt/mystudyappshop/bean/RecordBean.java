package com.demo.swt.mystudyappshop.bean;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/6
 */

public class RecordBean {
    float time;
    String filepath;

    public RecordBean(float time, String filepath) {
        this.time = time;
        this.filepath = filepath;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
