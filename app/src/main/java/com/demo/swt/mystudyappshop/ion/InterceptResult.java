package com.demo.swt.mystudyappshop.ion;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class InterceptResult {
    /**
     * 处理后的json字符串
     */
    private String result;
    /**
     * 处理后的结果信息  如服务器返回的flag，message信息，
     * 如服务器返回结果异常或者服务器处理未成功，通过异常抛出来，见InterceptNet
     */
    private String message;

    public InterceptResult(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
