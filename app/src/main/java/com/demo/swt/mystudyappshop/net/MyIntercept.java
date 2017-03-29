package com.demo.swt.mystudyappshop.net;

import android.text.TextUtils;

import com.demo.swt.mystudyappshop.exception.DataException;
import com.demo.swt.mystudyappshop.exception.ServerException;
import com.demo.swt.mystudyappshop.ion.InterceptNet;
import com.demo.swt.mystudyappshop.ion.InterceptResult;
import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * Created by huyf on 2016/2/28.
 */
public class MyIntercept implements InterceptNet {
    public static final Gson gson = new Gson();
    @Override
    public InterceptResult handler(String resopne) throws DataException,ServerException {
        String message = null;
        try{
            JSONObject jsonObject = new JSONObject(resopne);
            //boolean success = jsonObject.getBoolean("result");
            String flagStr = jsonObject.optString("flag");
            message = jsonObject.optString("message");
            String data = jsonObject.optString("data");
            int flag = Integer.parseInt(flagStr);
            if(TextUtils.isEmpty(data)){
                //throw new NoDataException();
                //此处不能抛出空数据异常，避免某些接口本身就无返回数据情况
            }
            if(flag==1){
                return new InterceptResult(data,message);
            }else{
                throw new ServerException(message,flag);
            }
        } catch (ServerException e){
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            if(TextUtils.isEmpty(message)){
                throw new DataException();
            }else{
                throw new ServerException("返回数据格式错误!  "+message,0);
            }
        }
//        JsonString jsonString = null;
//        try {
//            jsonString = gson.fromJson(resopne,JsonString.class);
//            int flag = Integer.parseInt(jsonString.flag);
//            if(flag==1){
//                return  new InterceptResult(jsonString.data,jsonString.message);
//            }
//            throw new ServerException(jsonString.message,flag);
//        }catch (NumberFormatException e){
//            if(jsonString!=null&& !TextUtils.isEmpty(jsonString.message)){
//                throw new ServerException(jsonString.message,0);
//            }else{
//                throw new DataException();
//            }
//        }






    }


    static class JsonString{
        boolean result;
        String message;
        String flag;
        String data;
    }


}
