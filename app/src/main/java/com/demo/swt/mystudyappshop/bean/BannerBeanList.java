package com.demo.swt.mystudyappshop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Qianjujun
 * @time 2016/3/29
 */
public class BannerBeanList {

    /**
     * total : 0
     * list : []
     * pageNum : 1
     * pageSize : 100
     * pages : 0
     * size : 0
     */

    @SerializedName("total")
    private int total;
    @SerializedName("pageNum")
    private int pageNum;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("pages")
    private int pages;
    @SerializedName("size")
    private int size;
    @SerializedName("list")
    private List<BannerBean> list;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setList(List<BannerBean> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPages() {
        return pages;
    }

    public int getSize() {
        return size;
    }

    public List<BannerBean> getList() {
        return list;
    }
}
