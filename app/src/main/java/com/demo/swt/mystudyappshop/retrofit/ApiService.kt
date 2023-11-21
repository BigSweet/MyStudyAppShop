package com.demo.swt.mystudyappshop.retrofit

import com.demo.swt.mystudyappshop.bean.PlayBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * 介绍：所有的接口都写到这里
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/19
 */
interface ApiService {
    // Base_URL = "http://ip.taobao.com/";
    /**
     * 测试类
     */
    @get:GET("c/os/getPhonicsCourse")
    val test: Observable<BaseData<CateBean>>

    @get:GET("http://m.primedu.cn/c/res/getCartoonList?count=500&&s_category_id=37")
    val playData: Observable<BaseData<PlayBean>>

    @POST("https://api.xiyacs.com/app/woshimi_app/square/getSquareHotTopicDetailList")
    fun getFriend(@Body page: String): Call<BaseData<String>>
}