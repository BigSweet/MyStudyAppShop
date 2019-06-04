package com.demo.swt.mystudyappshop.convert;

import com.google.gson.Gson;


/**
 * 介绍：解析json
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public abstract class BaseGsonConvertJson<T>  implements ConvertJson<T> {
    protected static Gson gson = new Gson();

    public static void initGson(Gson gson){
        BaseGsonConvertJson.gson = gson;
    }



}
