package com.demo.swt.mystudyappshop.bean;

import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class FeedBean {
    private PostInfoBean post;
    private long display_time;
    private UserBean user;
    private List<UpsBean> ups;
    private List<CommentBean> comments;
    private int feed_type;

    public int getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(int feed_type) {
        this.feed_type = feed_type;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public List<UpsBean> getUps() {
        return ups;
    }

    public void setUps(List<UpsBean> ups) {
        this.ups = ups;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public long getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(long display_time) {
        this.display_time = display_time;
    }

    public PostInfoBean getPost() {
        return post;
    }

    public void setPost(PostInfoBean post) {
        this.post = post;
    }
}
