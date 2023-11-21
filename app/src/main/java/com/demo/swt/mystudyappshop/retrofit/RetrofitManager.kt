package com.demo.swt.mystudyappshop.retrofit

import bean.FeedBeanList
import com.android.okhttp.monitor.interceptor.MonitorInterceptor
import com.demo.swt.mystudyappshop.MyApplication
import com.demo.swt.mystudyappshop.Util.SpUtils
import com.demo.swt.mystudyappshop.bean.PlayBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/24
 */
class RetrofitManager private constructor() {
    private val baseurl = "http://m.primedu.cn/"
    private val gson: Gson
    private val retrofit: Retrofit
    var cacheFile = File(MyApplication.getmContext().cacheDir, "cache")
    var cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小
    var mApiService: ApiService


    companion object {
        private var instant: RetrofitManager? = null
            get() {
                return field ?: RetrofitManager()
            }

        @JvmStatic
        @Synchronized//添加synchronized同步锁
        fun getInstance(): RetrofitManager {
            return requireNotNull(instant)
        }
    }


    //测试类
    val test: Observable<*>
        get() = mApiService.test.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    //测试类
    val playData: Observable<BaseData<PlayBean>>
        get() = mApiService.playData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    suspend fun getFriend(page: String) = withContext(Dispatchers.IO) {
        mApiService.getFriend(page).await()
    }


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }


    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url.newBuilder() // Provide your custom parameter here
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder() // Provide your custom header here
                    .method(originalRequest.method, originalRequest.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    init {
        gson = GsonBuilder()
                .setLenient()
                .create()
        val client = OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor()) //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
                .addInterceptor(MonitorInterceptor()) // token过滤
                .addInterceptor(LogInterceptor().setLevel(LogInterceptor.Level.BODY))
                .cache(cache) //添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS) //                .addNetworkInterceptor(new LogInterceptor())
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        mApiService = retrofit.create(ApiService::class.java)
    }
}