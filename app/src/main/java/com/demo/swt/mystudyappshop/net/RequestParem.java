package com.demo.swt.mystudyappshop.net;


import com.demo.swt.mystudyappshop.Util.LogUtils;
import com.demo.swt.mystudyappshop.ion.InterceptNet;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class RequestParem {

    protected static Gson gson = new Gson();
    private final String url;
    private final String requestMethod;

    protected Map<String,Object> mapParems = new HashMap<>();

    protected Map<String,String> mapHeader = new HashMap<>();


    private Map<String,File> fileMap = new HashMap<>();



    private boolean intercept;//设置是否拦截请求结果，默认拦截

    private InterceptNet interceptNet;//设置自定义拦截

    protected RequestParem(String url, String requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.intercept = true;
        mapHeader.put("Content-Type", "application/json");
    }

    public static RequestParem auto(String url, String type){
        RequestParem requestParem = new RequestParem(url,type);
        return requestParem;
    }

    public static RequestParem get(String url){
        RequestParem requestParem = new RequestParem(url,"GET");
        return requestParem;
    }

    public static RequestParem put(String url){
        RequestParem requestParem = new RequestParem(url,"PUT");
        return requestParem;
    }

    public static RequestParem post(String url){
        RequestParem requestParem = new RequestParem(url,"POST");
        return requestParem;
    }

    public static RequestParem delete(String url){
        RequestParem requestParem = new RequestParem(url,"DELETE");
        return requestParem;
    }


    public String getUrl() {
        LogUtils.d("urrrrrrrrrl:"+url);
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }


    public Map<String, Object> getMapParems() {
        return mapParems;
    }

    public void setMapParems(Map<String, Object> mapParems) {
        if(mapParems!=null){
            this.mapParems = mapParems;
        }
    }

    public Map<String, String> getMapHeader() {
        return mapHeader;
    }

    public void setMapHeader(Map<String, String> mapHeader) {
        if(mapHeader!=null){
            mapHeader.put("Content-Type", "application/json");
            this.mapHeader = mapHeader;
        }
    }

    public boolean isIntercept() {
        return intercept;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }

    public InterceptNet getInterceptNet() {
        return interceptNet;
    }

    public void setInterceptNet(InterceptNet interceptNet) {
        this.interceptNet = interceptNet;
    }



    public RequestParem putHeader(String key, String value){
        mapHeader.put(key,value);
        return this;
    }

    public RequestParem put(String key, Object value){
        mapParems.put(key,value);
        return this;
    }


    protected static final String JSON_BEAN = "jsonBean";
    public static String toJsonFormMap(Map<String,Object> map){
        if(map==null){
            map = new HashMap<>();
        }

        if(map.size()==1&&map.containsKey(JSON_BEAN)){
            Object o = map.get(JSON_BEAN);
            return gson.toJson(o);
        }
        return gson.toJson(map);
    }


    public Map<String, File> getFileMap() {
        return fileMap;
    }

    public RequestParem put(String key, File file){
        fileMap.put(key,file);
        return this;
    }

    public RequestParem putFileUrl(String key, String filePath){
        return this;
    }

    public RequestParem setFileUrls(List<String> urls){
        return this;
    }

    public List<String> getFileUrls(){
        return new ArrayList<>();
    }

    public void convertUrlToFile(){}




    @Override
    public String toString() {
        return "RequestParem{" +
                "url='" + url + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", mapParems=" + mapParems +
                ", mapHeader=" + mapHeader +
                ", fileMap=" + fileMap +
                ", intercept=" + intercept +
                ", interceptNet=" + interceptNet +
                '}';
    }

}
