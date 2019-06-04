package com.demo.swt.mystudyappshop.Util;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.demo.swt.mystudyappshop.TuLingJiQi.Aes;
import com.demo.swt.mystudyappshop.TuLingJiQi.Md5;
import com.demo.swt.mystudyappshop.TuLingJiQi.PostServer;
import com.demo.swt.mystudyappshop.bean.ChatMessage;
import com.demo.swt.mystudyappshop.bean.Result;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/14
 */

public class ChatUtil {
    private static String url = "http://www.tuling123.com/openapi/api";
    private HashMap<String, String> mmap = new HashMap<>();
    //图灵网站上的secret
    private static String secret = "ad9301dcf7d37e3f";
    //图灵网站上的apiKey
    private static String apiKey = "f1b34f3f073840c5a15b7c6b87f54402";


    public static ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = postmsg(msg);
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
            chatMessage.setUrl(result.getUrl());
        } catch (Exception e) {
            chatMessage.setMsg("服务器繁忙，请稍候再试");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }


    public static String postmsg(String cmd) {

        //待加密的json数据
        String data = "{\"key\":\"" + apiKey + "\",\"info\":\"" + cmd + "\"}";
        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成密钥
        String keyParam = secret + timestamp + apiKey;
        String key = Md5.MD5(keyParam);

        //加密
        Aes mc = new Aes(key);
        data = mc.encrypt(data);

        //封装请求参数
        JSONObject json = new JSONObject();
        json.put("key", apiKey);
        json.put("timestamp", timestamp);
        json.put("data", data);
        //请求图灵api
        String result = PostServer.SendPost(json.toString(), url);
        Log.i("Data", result);
        return result;
    }
}
