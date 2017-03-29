package com.demo.swt.mystudyappshop.Util;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public interface IFileName {

    /*是否打开过APP*/
    String PREFERENCE_OPENDED_APP = "opended_app";
    String PREFERENCE_OPENDED_APP_KEY = "opended_app_key";
    String ROOTER_FILE = "router.json";
    String SEND_MP3 = "send_message.mp3";
    String IM_FODLE_PROTOL = "imfolder.json";

    String PUSH_TOKEN = "push_";

    // 内置表情包
    String EMOTION_DATA_PATH = "emotions";
    String EMOTION_BUILD_IN = "monkey.zip";
    String EMOTION_BUILD_IN_1 = "monkey_god.zip";
    String EMOTION_MOKEYY_FILE_NAME = "monkey";
    String EMOTION_MOKEYY_GOD_FILE_NAME = "monkey_god";


    /* 长连接服务器*/
    public final static String ASSETS_LON_CONNECTION = "im_server";

    String PATCH_VERSION_FILE = "patch_version";
    String PATCH_VERSION_KEY = "patch_version_key";

    //zhangxutong 朋友 通讯录匹配里的 CstDeviceId 保存名
    String CST_DEVICE_ID = "cst_device_id";


    //xjzhao
    /*省份列表*/
    String PROVINCE_LIST_JSON = "province.json";
    /*图片阅读模式*/
    String IMG_READ = "img_read";
    String IMG_READ_KEY = "im_read_key";

    //权限
    String AUTH_BREAKFAST = "auth_breakfast";
    String AUTH_BREAKFAST_KEY = "auth_breakfast_key";
    String AUTH_CLOUD = "auth_cloud";
    String AUTH_CLOUD_KEY = "auth_cloud_key";
    String AUTH_EARTH = "auth_earth";
    String AUTH_EARTH_KEY = "auth_earth_key";

    //启动时间间隔
    String OPEN_APP_TIME = "open_app_time";
    String OPEN_APP_TIME_KEY = "open_app_time_key";

    //备注名列表是否获取成功
    String REMARK_LIST_LOAD_SUCESS = "remark_list_load_sucess";
    String REMARK_LIST_LOAD_SUCESS_KEY = "remark_list_load_sucess_key";

    //第一次打开
    String FIRST_OPEN_USERCENTER = "first_open_usercenter";
    String FIRST_OPEN_USERCENTER_KEY = "first_open_usercenter_key";
    String FIRST_OPEN_FRIEND = "first_open_friend";
    String FIRST_OPEN_FRIEND_KEY = "first_open_friend_key";
    String FIRST_OPEN_BBS = "first_open_bbs";
    String FIRST_OPEN_BBS_KEY = "first_open_bbs_key";
    String FIRST_OPEN_COMMUNITY = "first_open_community";
    String FIRST_OPEN_COMMUNITY_KEY = "first_open_community_key";


    //算了,第一次打开的其他页面放在同一个文件中
    String FIRST_FCK_OPEN = "ffirst_fck_open";
    String KEY_MAIN_FIRST = "key_main_first";
    String KEY_BBS_FRIEND_FIRST = "key_bbs_friend_first";
    String KEY_SERVER_FRIEND_FIRST = "key_server_friend_first";
    String KEY_IM_FIRST = "key_im_first";
    String KEY_TICKET_GIFT_FIRST = "key_ticket_gift_first";

}
