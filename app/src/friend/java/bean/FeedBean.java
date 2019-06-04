package bean;

import java.util.List;

/**
 * Created by pc on 2016/12/1.
 */

public class FeedBean {

    private int id;
    private int feedType;
    private int subFeedType;
    private int refId;
    private UserBean user;
    private String content;
    private double lat;
    private double lng;
    private int viewCt;
    private int upCt;
    private int upFlag;
    private int commentCt;
    private int shareCt;
    private int slashCt;
    private long createTime;
    private ChannelInfoBean channelInfo;
    private List<String> imageList;
    private List<UpsBean> ups;
    private List<CommentsBean> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeedType() {
        return feedType;
    }

    public void setFeedType(int feedType) {
        this.feedType = feedType;
    }

    public int getSubFeedType() {
        return subFeedType;
    }

    public void setSubFeedType(int subFeedType) {
        this.subFeedType = subFeedType;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getViewCt() {
        return viewCt;
    }

    public void setViewCt(int viewCt) {
        this.viewCt = viewCt;
    }

    public int getUpCt() {
        return upCt;
    }

    public void setUpCt(int upCt) {
        this.upCt = upCt;
    }

    public int getUpFlag() {
        return upFlag;
    }

    public void setUpFlag(int upFlag) {
        this.upFlag = upFlag;
    }

    public int getCommentCt() {
        return commentCt;
    }

    public void setCommentCt(int commentCt) {
        this.commentCt = commentCt;
    }

    public int getShareCt() {
        return shareCt;
    }

    public void setShareCt(int shareCt) {
        this.shareCt = shareCt;
    }

    public int getSlashCt() {
        return slashCt;
    }

    public void setSlashCt(int slashCt) {
        this.slashCt = slashCt;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public ChannelInfoBean getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfoBean channelInfo) {
        this.channelInfo = channelInfo;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<UpsBean> getUps() {
        return ups;
    }

    public void setUps(List<UpsBean> ups) {
        this.ups = ups;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class UserBean {
        /**
         * userId : 1517187
         * name : 💋祝佳乐
         * avatar : https://pic.anlaiye.com.cn/909da60a590d481297247447063c120b_720x960.jpg
         * gender : 0
         * entityId : 1651
         * entityName : 河北师范大学汇华学院
         * certified : 1
         * orgId : 1654
         * relation : 3
         * level : 2
         * type : 0
         * constellation : 射手座
         * avatarList : ["https://pic.anlaiye.com.cn/909da60a590d481297247447063c120b_720x960.jpg","https://pic.anlaiye.com.cn/d3d75488c0ce4673990f6364cca0ecb3_658x690.jpg","https://pic.anlaiye.com.cn/8cea56feb72f4314a6cb2f7b745c5b7c_720x1280.jpg","https://pic.anlaiye.com.cn/5c99a311e375441b9efc17eaa3fba379_960x1280.jpg","https://pic.anlaiye.com.cn/7153be55f72c44d2a07a5c759eecc638_1200x1600.jpg","https://pic.anlaiye.com.cn/085be29dbbbc4ae2bf79769e37a1980b_540x960.jpg"]
         * userType : 1
         */

        private int userId;
        private String name;
        private String avatar;
        private int gender;
        private String entityId;
        private String entityName;
        private int certified;
        private String orgId;
        private int relation;
        private int level;
        private int type;
        private String constellation;
        private int userType;
        private List<String> avatarList;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public int getCertified() {
            return certified;
        }

        public void setCertified(int certified) {
            this.certified = certified;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public List<String> getAvatarList() {
            return avatarList;
        }

        public void setAvatarList(List<String> avatarList) {
            this.avatarList = avatarList;
        }
    }

    public static class ChannelInfoBean {
        /**
         * channelId : 71652
         * avatar : http://pic.anlaiye.com.cn/e7545ffbbc764a55a772c406d5167dea_200x200.jpg
         * name : 河北师范大学汇华学院
         * publishCt : 158
         * followCt : 223
         * type : 4
         */

        private int channelId;
        private String avatar;
        private String name;
        private int publishCt;
        private int followCt;
        private int type;

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPublishCt() {
            return publishCt;
        }

        public void setPublishCt(int publishCt) {
            this.publishCt = publishCt;
        }

        public int getFollowCt() {
            return followCt;
        }

        public void setFollowCt(int followCt) {
            this.followCt = followCt;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class UpsBean {
        /**
         * userId : 3086135
         * name : 孙宇航
         * avatar : https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg
         * gender : 1
         * entityId : 1865
         * entityName : 淮海工学院
         * certified : 0
         * orgId : 1868
         * relation : 0
         * level : 2
         * type : 0
         * constellation : 金牛座
         * avatarList : ["https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg","https://pic.anlaiye.com.cn/8ab11b9c90ee4c84b881a82674d72311_1620x1080.jpg","https://pic.anlaiye.com.cn/20b05ef3949341c7b1f6ee9c0a1e3709_1080x1254.jpg","https://pic.anlaiye.com.cn/fe8d84dd3e8e430dbc63b171f7aa8143_960x1440.jpg","https://pic.anlaiye.com.cn/b1e2d72710984a3997de66ec8b28293d_1620x1084.jpg","https://pic.anlaiye.com.cn/182e1ac1574947e7a0d84f570802622c_1620x1080.jpg"]
         * userType : 1
         */

        private int userId;
        private String name;
        private String avatar;
        private int gender;
        private String entityId;
        private String entityName;
        private int certified;
        private String orgId;
        private int relation;
        private int level;
        private int type;
        private String constellation;
        private int userType;
        private List<String> avatarList;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public int getCertified() {
            return certified;
        }

        public void setCertified(int certified) {
            this.certified = certified;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public List<String> getAvatarList() {
            return avatarList;
        }

        public void setAvatarList(List<String> avatarList) {
            this.avatarList = avatarList;
        }
    }

    public static class CommentsBean {
        /**
         * feedId : 2460677
         * commentId : 475149
         * user : {"userId":3086135,"name":"孙宇航","avatar":"https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg","gender":1,"entityId":"1865","entityName":"淮海工学院","certified":0,"orgId":"1868","relation":0,"level":2,"type":0,"constellation":"金牛座","avatarList":["https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg","https://pic.anlaiye.com.cn/8ab11b9c90ee4c84b881a82674d72311_1620x1080.jpg","https://pic.anlaiye.com.cn/20b05ef3949341c7b1f6ee9c0a1e3709_1080x1254.jpg","https://pic.anlaiye.com.cn/fe8d84dd3e8e430dbc63b171f7aa8143_960x1440.jpg","https://pic.anlaiye.com.cn/b1e2d72710984a3997de66ec8b28293d_1620x1084.jpg","https://pic.anlaiye.com.cn/182e1ac1574947e7a0d84f570802622c_1620x1080.jpg"],"userType":1}
         * upCt : 0
         * upFlag : 0
         * content : 彭于晏好帅
         * createTime : 1532076553000
         */

        private int feedId;
        private int commentId;
        private UserBeanX user;
        private int upCt;
        private int upFlag;
        private String content;
        private long createTime;

        public int getFeedId() {
            return feedId;
        }

        public void setFeedId(int feedId) {
            this.feedId = feedId;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public int getUpCt() {
            return upCt;
        }

        public void setUpCt(int upCt) {
            this.upCt = upCt;
        }

        public int getUpFlag() {
            return upFlag;
        }

        public void setUpFlag(int upFlag) {
            this.upFlag = upFlag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public static class UserBeanX {
            /**
             * userId : 3086135
             * name : 孙宇航
             * avatar : https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg
             * gender : 1
             * entityId : 1865
             * entityName : 淮海工学院
             * certified : 0
             * orgId : 1868
             * relation : 0
             * level : 2
             * type : 0
             * constellation : 金牛座
             * avatarList : ["https://pic.anlaiye.com.cn/4e7d04a993014c6e9aa3f2a42201caa6_852x1280.jpg","https://pic.anlaiye.com.cn/8ab11b9c90ee4c84b881a82674d72311_1620x1080.jpg","https://pic.anlaiye.com.cn/20b05ef3949341c7b1f6ee9c0a1e3709_1080x1254.jpg","https://pic.anlaiye.com.cn/fe8d84dd3e8e430dbc63b171f7aa8143_960x1440.jpg","https://pic.anlaiye.com.cn/b1e2d72710984a3997de66ec8b28293d_1620x1084.jpg","https://pic.anlaiye.com.cn/182e1ac1574947e7a0d84f570802622c_1620x1080.jpg"]
             * userType : 1
             */

            private int userId;
            private String name;
            private String avatar;
            private int gender;
            private String entityId;
            private String entityName;
            private int certified;
            private String orgId;
            private int relation;
            private int level;
            private int type;
            private String constellation;
            private int userType;
            private List<String> avatarList;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getEntityId() {
                return entityId;
            }

            public void setEntityId(String entityId) {
                this.entityId = entityId;
            }

            public String getEntityName() {
                return entityName;
            }

            public void setEntityName(String entityName) {
                this.entityName = entityName;
            }

            public int getCertified() {
                return certified;
            }

            public void setCertified(int certified) {
                this.certified = certified;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public int getRelation() {
                return relation;
            }

            public void setRelation(int relation) {
                this.relation = relation;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getConstellation() {
                return constellation;
            }

            public void setConstellation(String constellation) {
                this.constellation = constellation;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public List<String> getAvatarList() {
                return avatarList;
            }

            public void setAvatarList(List<String> avatarList) {
                this.avatarList = avatarList;
            }
        }
    }
}
