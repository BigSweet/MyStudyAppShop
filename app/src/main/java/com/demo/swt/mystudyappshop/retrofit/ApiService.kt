package com.demo.swt.mystudyappshop.retrofit

import bean.FeedBeanList
import com.demo.swt.mystudyappshop.bean.PlayBean
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
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

    @GET("https://sns-center.anlaiye.com.cn/sns/feed/flow/3/list?deviceId=2cf32c42-885c-4a3b-ba31-85a056300627&locationType=1&token=ca054bfdd21b5d7a1164c9f5d6f716f6&ps=20&pageSize=20&appid=1&appplt=aph&subFeedType=0&pageNo=1&schoolId=1&lng=121.542289&feedType=0&appver=5.3.3&page_size=20&pageNum=1&currentPage=1&actionType=0&page=1&lat=31.219224&pagesize=20")
    fun getFriend(@Query("nt") nt: String): Call<FeedBeanList>
}