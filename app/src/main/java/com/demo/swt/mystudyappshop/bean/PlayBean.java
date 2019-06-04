package com.demo.swt.mystudyappshop.bean;

import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/25
 */
public class PlayBean {


    /**
     * cartoon_list : []
     * cartoon_total : 26
     * category_name : 第一季-英文版
     * has_more : false
     */

    private int cartoon_total;
    private String category_name;
    private boolean has_more;
    private List<CartoonListBean> cartoon_list;

    public int getCartoon_total() {
        return cartoon_total;
    }

    public void setCartoon_total(int cartoon_total) {
        this.cartoon_total = cartoon_total;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<CartoonListBean> getCartoon_list() {
        return cartoon_list;
    }

    public void setCartoon_list(List<CartoonListBean> cartoon_list) {
        this.cartoon_list = cartoon_list;
    }
}
