package com.demo.swt.mystudyappshop.result;

import android.text.TextUtils;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class ResultMessage implements ResultCode {

    private int errorCode;//错误码
    private String message;//友好的提示给用户
    private String detailMsg;//真实异常错误

    private boolean success;

    private ResultMessage(int errorCode, String message, String detailMsg, boolean success) {
        this.errorCode = errorCode;
        this.message = message;
        this.detailMsg = detailMsg;
        this.success = success;
    }


    public ResultMessage(int errorCode, String message, boolean success) {
        this(errorCode, message, message, success);
    }

    public ResultMessage(int errorCode, String message) {
        this(errorCode, message, message, false);
    }


    public static ResultMessage success(String msg) {
        return new ResultMessage(SUCCESS, msg, true);
    }

    public static ResultMessage success() {
        return new ResultMessage(SUCCESS, "请求成功", true);
    }

    public static ResultMessage error(int errorCode, String msg) {
        return new ResultMessage(errorCode, msg);
    }

    public static ResultMessage error(String msg) {
        return new ResultMessage(ERROR_OTHER, msg);
    }

    public static ResultMessage error(int errorCode, Throwable e) {
        return new ResultMessage(errorCode, "", e);
    }

    public static ResultMessage error(int errorCode, String msg, Throwable e) {
        return new ResultMessage(errorCode, msg, e);
    }


    private ResultMessage(int errorCode, String message, Throwable e) {
        if (null == e) {
            this.errorCode = ERROR_OTHER;
            this.message = message;
            this.detailMsg = message;
        } else {
            this.errorCode = errorCode;
            if (TextUtils.isEmpty(message)) {
                this.message = e.getMessage();
            } else {
                this.message = message;
            }
            this.detailMsg = e.getClass().getSimpleName() + ":" + e.getMessage();
        }
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public void show() {

    }

    public interface ShowFiltl {
        void filte(int flag);
    }

    @Override
    public String toString() {
        return "ResultMessage{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", detailMsg='" + detailMsg + '\'' +
                ", success=" + success +
                '}';
    }
}
