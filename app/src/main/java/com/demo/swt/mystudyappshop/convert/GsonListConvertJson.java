package com.demo.swt.mystudyappshop.convert;


import com.demo.swt.mystudyappshop.Util.LogUtils;
import com.demo.swt.mystudyappshop.exception.DataException;
import com.demo.swt.mystudyappshop.exception.NoDataException;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class GsonListConvertJson<T> extends BaseGsonConvertJson<List<T>> {

    private final Class<T> clazz;

    public GsonListConvertJson(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public List<T> convert(String str) throws DataException {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i = 0;i<jsonArray.length();i++){
                String beanString = jsonArray.getString(i);
                list.add(gson.fromJson(beanString,clazz));
            }
        } catch (Exception e) {
            LogUtils.e("hw----gson error ----", e);
            e.printStackTrace();
            throw new DataException();
        }
        if(list.size()==0&&clazz!=String.class){
            throw new NoDataException();
        }
        return list;
    }
}
