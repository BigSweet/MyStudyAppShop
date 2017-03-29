package com.demo.swt.mystudyappshop.exception;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */
public class DataException extends Exception {


    public static final String ERROR_PARSE = "数据解析错误";

    public static final String ERROR_NO_DATA = "无数据";

    public DataException(String detailMessage) {
        super(detailMessage);
    }

    public DataException(String message, Throwable throwable) {
        super(ERROR_PARSE,throwable);
    }

    public DataException(){
        super(ERROR_PARSE);
    }


}
