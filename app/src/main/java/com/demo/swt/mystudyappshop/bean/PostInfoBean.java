package com.demo.swt.mystudyappshop.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class PostInfoBean {
    @SerializedName("images")
    List<String> images; //图片
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
