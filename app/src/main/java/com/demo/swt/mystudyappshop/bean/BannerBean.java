package com.demo.swt.mystudyappshop.bean;


import com.demo.swt.mystudyappshop.Interface.ISlideModel;

/**
 * @author Qianjujun
 * @time 2016/3/29
 */
public class BannerBean implements ISlideModel {
    private int adId;
    private String title;
    private String imageUrl;
    private String type;
    private String data;
    private int showTime;
    private boolean canClose;

    private String imageTitle;

    public BannerBean() {
    }

    public BannerBean(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public void setCanClose(boolean canClose) {
        this.canClose = canClose;
    }

    public int getAdId() {
        return adId;
    }

    @Override
    public String getImgUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getShowTime() {
        return showTime;
    }

    public boolean isCanClose() {
        return canClose;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
