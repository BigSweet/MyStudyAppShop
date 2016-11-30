package com.demo.swt.mystudyappshop.bean;

/**
 * Created by pc on 2016/11/30.
 */

public class BannerBean {
    private String img;
    private String des;

    public BannerBean(String img, String des) {
        this.img = img;
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
