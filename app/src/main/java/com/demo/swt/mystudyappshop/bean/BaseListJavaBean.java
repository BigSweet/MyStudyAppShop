package com.demo.swt.mystudyappshop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class BaseListJavaBean<T> {
    @SerializedName("list")
    private List<T> list;         // 瀑布式
    @SerializedName("page_list")  //分页式
    private List<T> pageList;
    @SerializedName("dataList")  //分页式
    private List<T> dataList;
    @SerializedName("total")  //分页式
    private int total;//总数。
    @SerializedName("page_count")
    private int pageCount;	//页数
    @SerializedName("page_size")
    private int pageSize;	//每页数量
    @SerializedName("page_no")
    private int pageNo;	//当前页面号
    @SerializedName("count")
    private int count;//
    @SerializedName("nt")
    private String nt;	//版本Id，用作下一页版本号，null表示没有下一页
    @SerializedName("pt")
    private String pt;	//版本Id，用作上一页版本号


    public List<T> getList() {
        if (list != null){
            return list;
        }else if (dataList != null){
            return dataList;
        }else if (pageList != null){
            return pageList;
        }
        return null;
    }

    public int getTotal() {
        return count == 0 ? total : count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getCount() {
        return count == 0 ? total : count;
    }

    public String getNt() {
        return nt;
    }

    public String getPt() {
        return pt;
    }


    public boolean isDataEmpty(){
        return getList()==null||getList().size()==0;
    }
}
