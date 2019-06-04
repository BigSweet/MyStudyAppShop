package com.demo.swt.mystudyappshop.net;

import android.text.TextUtils;

import com.demo.swt.mystudyappshop.Util.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class JavaRequestParem extends RequestParem {
    private StringBuilder urlBuild;

    private Map<String, Object> bodyParem = new HashMap<>();

    protected JavaRequestParem(String url, String requestMethod) {
        super(url, requestMethod);
        mapHeader.put("Content-Type", "application/json");
        put("appplt", "aph");
        put("appver", "3.1.5");
        put("appid", "1");
    }


    public JavaRequestParem(String url) {
        super(url, "GET");
        mapHeader.put("Content-Type", "application/json");
    }

    @Override
    public Map<String, Object> getMapParems() {
        return bodyParem;
    }


    public JavaRequestParem putBody(String key, Object value) {
        if(value!=null&&!TextUtils.isEmpty(String.valueOf(value))){
            bodyParem.put(key, value);
        }
        return this;
    }

    public JavaRequestParem putBody(List<?> list) {
        bodyParem.put(JSON_BEAN, list);
        return this;
    }

    public JavaRequestParem putBody(Object object) {
        bodyParem.put(JSON_BEAN, object);
        return this;
    }


    @Override
    public String getUrl() {

        if (mapParems.size() == 0) {
            return super.getUrl();
        }
        urlBuild = new StringBuilder(super.getUrl());
        urlBuild.append("?");
        Object vaule ;
        for (String key : mapParems.keySet()) {
            vaule = mapParems.get(key);
            if(vaule==null|| TextUtils.isEmpty(vaule.toString().trim())){
                continue;
            }
            urlBuild.append(key);
            urlBuild.append("=");
            urlBuild.append(mapParems.get(key));
            urlBuild.append("&");
        }

        String url = urlBuild.toString();
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        LogUtils.d("getUrl", url);
        LogUtils.d("body", bodyParem.toString());
        return url;
    }

    public static JavaRequestParem auto(String url, String requestMethod) {
        return new JavaRequestParem(url, requestMethod);
    }

    public static JavaRequestParem get(String url) {
        JavaRequestParem requestParem = new JavaRequestParem(url, "GET");
        return requestParem;
    }

    public static JavaRequestParem put(String url) {
        JavaRequestParem requestParem = new JavaRequestParem(url, "PUT");
        return requestParem;
    }

    public static JavaRequestParem post(String url) {
        JavaRequestParem requestParem = new JavaRequestParem(url, "POST");
        return requestParem;
    }

    public static JavaRequestParem delete(String url) {
        JavaRequestParem requestParem = new JavaRequestParem(url, "DELETE");
        return requestParem;
    }


}
