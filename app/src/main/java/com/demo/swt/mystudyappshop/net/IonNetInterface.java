package com.demo.swt.mystudyappshop.net;

import android.content.Context;
import android.os.Handler;

import com.demo.swt.mystudyappshop.Listener.RequestListner;
import com.demo.swt.mystudyappshop.Util.LogUtils;
import com.demo.swt.mystudyappshop.Util.NetworkUtils;
import com.demo.swt.mystudyappshop.convert.ConvertJson;
import com.demo.swt.mystudyappshop.convert.GsonConvertJson;
import com.demo.swt.mystudyappshop.convert.GsonListConvertJson;
import com.demo.swt.mystudyappshop.exception.DataException;
import com.demo.swt.mystudyappshop.exception.NoDataException;
import com.demo.swt.mystudyappshop.exception.ServerException;
import com.demo.swt.mystudyappshop.ion.InterceptNet;
import com.demo.swt.mystudyappshop.ion.InterceptResult;
import com.demo.swt.mystudyappshop.result.ResultCode;
import com.demo.swt.mystudyappshop.result.ResultMessage;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.FutureBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import static com.koushikdutta.ion.Ion.getDefault;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/2
 */

public class IonNetInterface implements NetInterface {


    private static IonNetInterface ionNetInterface;
    private Handler mHandler;

    private IonNetInterface() {
        mHandler = new Handler();
    }

    public static IonNetInterface get() {
        if (ionNetInterface == null) {
            synchronized (IonNetInterface.class) {
                if (ionNetInterface == null) {
                    ionNetInterface = new IonNetInterface();
                }
            }
        }
        return ionNetInterface;
    }


    private Context app;
    private InterceptNet interceptNet;

    @Override
    public void start(Context app) {
        if (this.app == null) {
            this.app = app;

            //此处获取拦截接口的实现类，剥离json数据的封装   耗时  改为set
//            try {
//                String interceptNetName = ConfigUtil.getInterceptNet();
//                interceptNet = (InterceptNet) Class.forName(interceptNetName).newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }


    public void setInterceptNet(InterceptNet interceptNet) {
        this.interceptNet = interceptNet;
    }

    @Override
    public void stop() {
        getDefault(app).cancelAll();
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner) {
        doRequest(requestParem, requestListner, null);
    }

    @Override
    public <T> void doRequest(RequestParem requestParem, final RequestListner<T> requestListner, final ConvertJson<T> convertJson) {

        if (requestParem == null) return;


       /*
       // 5.0以下手机线上环境会出现 javax.net.ssl.SSLException: Not trusted sever certificate exception. 就这么办,暂时注释掉
        if (android.os.Build.VERSION.SDK_INT < 16){
       TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};
        //Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            sc.setDefault(sc);

        } catch (Exception e) {
        }

        Ion.getDefault(app).getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustAllCerts);
        Ion.getDefault(app).getHttpClient().getSSLSocketMiddleware().setSSLContext(sc);

        Ion.getDefault(app).getHttpClient().getSSLSocketMiddleware().setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);}*/


        requestListner.onStart();

        if (!NetworkUtils.isNetwork()) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ResultMessage e = ResultMessage.error(ResultCode.ERROR_NO_NET, "无网络");
                    requestListner.onEnd(e);
                }
            }, 300);
            return;
        }
        //处理参数
        FutureBuilder builder = handlerParems(requestParem);

        //处理回调
        FutureCallback<String> callback = handlerCallBack(requestParem.getUrl(), requestListner, convertJson, requestParem.isIntercept(), requestParem.getInterceptNet());

        //请求
        //builder.group(requestListner.getTag()).asString().setCallback(callback);


        builder.group(requestListner.getTag()).asString(Charset.forName("UTF-8")).setCallback(callback);

    }


    @Override
    public void cancel(Object tag) {
        getDefault(app).cancelAll(tag);
    }


    /**
     * 处理ion的请求参数
     *
     * @param requestParem
     * @return
     */
    private FutureBuilder handlerParems(RequestParem requestParem) {

        LogUtils.d(requestParem.toString());

        Map<String, String> headerParams = requestParem.getMapHeader();
        Map<String, Object> mapParameter = requestParem.getMapParems();
        Map<String, File> fileMap = requestParem.getFileMap();
        boolean jsonParem = false;//java post 参数
        boolean fileParem = false;//表单上传参数

        if (fileMap != null && fileMap.size() > 0) {
            headerParams.put("Content-Type", "multipart/form-data");
            fileParem = true;
        }


        Builders.Any.B b = Ion.with(app).
                load(requestParem.getRequestMethod(), requestParem.getUrl());


        if (headerParams != null && headerParams.size() > 0) {

            if (!fileParem) {
                for (String key : headerParams.keySet()) {
                    b = b.addHeader(key, headerParams.get(key));
                }
            }
            jsonParem = headerParams.containsKey("Content-Type") && headerParams.get("Content-Type").contains("json");
        }
        if (mapParameter != null && mapParameter.size() > 0) {
            if (jsonParem) {

                LogUtils.e("JAVAPAREM", "url:" + requestParem.getUrl() + "\nheader:" + requestParem.getMapHeader() + "\nbody" + RequestParem.toJsonFormMap(mapParameter));
                return b.setStringBody(RequestParem.toJsonFormMap(mapParameter));

            }
            if (fileParem) {
                return handlerMultipartParameter(b, fileMap, mapParameter);
            } else {
                return handlerBodyParameter(b, mapParameter);
            }
        }
        return b;
    }

    //Post application/x-www-form-urlencoded and read a String
    private Builders.Any.U handlerBodyParameter(Builders.Any.B b, Map<String, Object> mapParameter) {
        Builders.Any.U u = null;
        for (String key : mapParameter.keySet()) {
            if (null == u) {
                u = b.setBodyParameter(key, String.valueOf(mapParameter.get(key)));
            } else {
                u = u.setBodyParameter(key, String.valueOf(mapParameter.get(key)));
            }
        }
        return u;
    }

    //Post multipart/form-data and read JSON with an upload progress bar
    private Builders.Any.B handlerMultipartParameter(Builders.Any.B b, Map<String, File> fileMap, Map<String, Object> mapParameter) {
        for (String key : mapParameter.keySet()) {
            b.setMultipartParameter(key, String.valueOf(mapParameter.get(key)));
        }
        for (String key : fileMap.keySet()) {
            File file = fileMap.get(key);
            if (file != null) {
                b.setMultipartFile(key, "application/jpg", fileMap.get(key));
            }
        }
        return b;
    }

    private <T> FutureCallback<String> handlerCallBack(final String url, final RequestListner<T> requestListner, final ConvertJson<T> convertJson, final boolean intercept, final InterceptNet cstInterceptNet) {
        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception exception, String response) {
                if (response != null && response.length() > 0) {
                    if (response.charAt(0) == 65279) {//去掉Bom头
                        response = response.substring(1, response.length());
                    }
                }

                LogUtils.e("onCompleted() called with " + "class:" + requestListner.getClazz().getSimpleName() + "exception = [" + exception + "], response = [" + response + "]");
                ResultMessage resultMessage = null;
                if (null == exception) {
                    if (response != null && response.length() > 0) {
                        if (response.charAt(0) == 65279) {//去掉Bom头
                            response = response.substring(1, response.length());
                        }
                    }

                    //拦截到服务器返回的字符串，剥离包装的flag，message
                    InterceptNet tempInterceptNet = cstInterceptNet == null ? interceptNet : cstInterceptNet;
                    if (tempInterceptNet != null && intercept) {
                        InterceptResult result = null;
                        try {
                            result = tempInterceptNet.handler(response);
                        } catch (DataException e) {
                            e.printStackTrace();

                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                        } catch (ServerException e) {
                            resultMessage = e.parse();
                            e.printStackTrace();
                        } catch (Exception e) {
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, DataException.ERROR_PARSE, e);
                        }
                        if (result == null) {
                            requestListner.onEnd(resultMessage == null ? ResultMessage.error(ResultMessage.ERROR_OTHER, "未知错误") : resultMessage);
                            return;
                        } else {
                            response = result.getResult();
                            resultMessage = ResultMessage.success(result.getMessage());
                        }
                    }
                    try {
                        boolean success = false;
                        if (convertJson != null) {
                            T t = convertJson.convert(response);
                            success = requestListner.onSuccess(t);
                        } else if (null != response && response.length() > 1 && response.charAt(0) == '[' && response.charAt(response.length() - 1) == ']') {
                            List<T> t = new GsonListConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        } else if (requestListner.getClazz() == String.class) {
                            success = requestListner.onSuccess((T) response);
                        } else if (null != response && response.length() > 1 && response.charAt(0) == '{' && response.charAt(response.length() - 1) == '}') {
                            T t = new GsonConvertJson<>(requestListner.getClazz()).convert(response);//解析层已经处理空数据情况
                            success = requestListner.onSuccess(t);
                        }
                        if (!success) {
                            resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, "数据处理失败");
                        }
                    } catch (NoDataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_NO_DATA, e);
                    } catch (DataException e) {
                        e.printStackTrace();
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_DATA, e);
                    } catch (Exception e) {
                        LogUtils.e("hw----------", e);
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_UI, "数据处理异常", e);
                    } finally {
                        requestListner.onEnd(resultMessage == null ? ResultMessage.success() : resultMessage);
                    }

                } else {
                    if (exception instanceof CancellationException) {
                        resultMessage = ResultMessage.error(ResultMessage.ERROR_CANCEL, "已取消请求", exception);
                    } else {
                        resultMessage = ResultMessage.error(ResultMessage.EROOR_NET, exception);
                    }
                    requestListner.onEnd(resultMessage);
                }
            }
        };
        return callback;
    }


    @Override
    public String doSyncRequest(String tag, RequestParem requestParem) {
        if (requestParem == null) return null;
        if (!NetworkUtils.isNetwork()) {
            ResultMessage e = ResultMessage.error(ResultCode.ERROR_NO_NET, "无网络");
            return null;
        }
        //处理参数
        FutureBuilder builder = handlerParems(requestParem);

        try {
            String re = builder.group(tag).asString(Charset.forName("UTF-8")).get();
            return re;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //处理
        return null;

    }
}
