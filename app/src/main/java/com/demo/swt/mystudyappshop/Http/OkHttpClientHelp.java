package com.demo.swt.mystudyappshop.Http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.HttpMethod;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by pc on 2016/12/1.
 */

public class OkHttpClientHelp {

    private static OkHttpClient okHttpClient;
    private Gson gson;
    private Handler handler;

    private OkHttpClientHelp() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientHelp getInstance() {

        return new OkHttpClientHelp();
    }

    public void get(String url, BaseCallBack callBack) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        dorequest(request, callBack);
    }


    public void post(String url, Map<String, String> params, BaseCallBack callBack) {
        Request request = buildRequest(url, null, HttpMethodType.POST);
        dorequest(request, callBack);
    }

    public void dorequest(Request request, final BaseCallBack callBack) {

        //网络请求的加载框
        callBack.onRequestBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultstr = response.body().string();
                    if (callBack.type == String.class) {
                        //  callBack.onSuccess(response, resultstr);
                        callbackSuccess(callBack, response, resultstr);
                    } else {

                        try {
                            Objects objects = gson.fromJson(resultstr, callBack.type);
                            //   callBack.onSuccess(response, objects);
                            callbackSuccess(callBack, response, objects);

                        } catch (JsonParseException e) {
                            //   callBack.onError(response, response.code(), e);
                            callbackFailure(callBack, response, response.code(), e);
                        }


                    }
                } else {
                    callBack.onError(response, response.code(), null);
                }
            }
        });
    }

    private Request buildRequest(String url, Map<String, String> params, HttpMethodType httpMethodType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (httpMethodType == HttpMethodType.GET) {
            builder.get();
        } else if (httpMethodType == HttpMethodType.POST) {
            RequestBody body = buildForData(params);
            builder.post(body);
        }
        return null;
    }

    private RequestBody buildForData(Map<String, String> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callbackSuccess(final BaseCallBack callBack, final Response response, final Objects objects) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, objects);
            }
        });

    }

    private void callbackSuccess(final BaseCallBack callBack, final Response response, final String string) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, string);
            }
        });

    }


    private void callbackFailure(final BaseCallBack callBack, final Response response, final int code, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(response, code, e);
            }
        });

    }

    enum HttpMethodType {
        GET,
        POST
    }
}
