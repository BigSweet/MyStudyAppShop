package com.demo.swt.mystudyappshop.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class FeedBean {
    private PostInfoBean post;
    private String display_time;
    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public PostInfoBean getPost() {
        return post;
    }

    public void setPost(PostInfoBean post) {
        this.post = post;
    }
}
