package com.demo.swt.mystudyappshop.retrofit;

import com.demo.swt.mystudyappshop.bean.PlayBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 介绍：所有的接口都写到这里
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/19
 */
public interface ApiService {
    // Base_URL = "http://ip.taobao.com/";
    /**
     *测试类
     */
    @GET("c/os/getPhonicsCourse")
    Observable<BaseData<CateBean>> getTest();


    @GET("c/res/getCartoonList?count=500&&s_category_id=37")
    Observable<BaseData<PlayBean>> getPlayData();
}
