package com.demo.swt.mystudyappshop.TuLingJiQi;


import com.alibaba.fastjson.JSONObject;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 加密请求测试类
 * @author 图灵机器人
 *
 */
public class AesTest {
	public static void main(String[] args) {
		AesTest utils= new AesTest();
		utils.testAes();
	}
	public void testAes(){
		try {
			KeyGenerator keyGenerator= KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			SecretKey secretKey=keyGenerator.generateKey();
			byte[] key =secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//图灵网站上的secret
		String secret = "ad9301dcf7d37e3f";
		//图灵网站上的apiKey
		String apiKey = "f1b34f3f073840c5a15b7c6b87f54402";
		String cmd = "鱼香肉丝怎么做";//测试用例
		//待加密的json数据
		String data = "{\"key\":\""+apiKey+"\",\"info\":\""+cmd+"\"}";
		//获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());
		
		//生成密钥
		String keyParam = secret+timestamp+apiKey;
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
		String result = PostServer.SendPost(json.toString(), "http://www.tuling123.com/openapi/api");
		System.out.println(result);
	}
	
}