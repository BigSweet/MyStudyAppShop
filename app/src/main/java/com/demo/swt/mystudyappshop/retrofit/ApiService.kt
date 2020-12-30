package com.demo.swt.mystudyappshop.retrofit

import com.demo.swt.mystudyappshop.bean.PlayBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("http://jax-api.uugtv.com/g/zone/dynamic/plaza-list?limit=20&toid=1338602&token=c2gz9SYd819009&sid=f453946b37024f1879f86ca92f377c01&cv=xiaomi-social_3.0.0&ua=vangogh&dev=80e8ca4759566625&androidId=80e8ca4759566625&oaid=dcf2f3d0b33b7736&conn=WIFI&osversion=android_29&cid=6&traceId=da007870-3b18-4f87-8fb1-0c0d7c80add3&imei=&nonce=fd4cce6820072cb513adc860f0519772&sign=18224706B16AD7411C43FD761730BDAD&debug=")
    fun getFriend(@Query("page") page: Int): Call<BaseData<MainHeartData>>
}