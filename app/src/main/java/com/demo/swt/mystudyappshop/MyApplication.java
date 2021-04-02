package com.demo.swt.mystudyappshop;

import android.app.Application;
import android.content.Context;

import com.android.okhttp.monitor.MonitorHelper;
import com.baidu.mapapi.SDKInitializer;
import com.demo.swt.mystudyappshop.Util.LoadImgUtils;
import com.demo.swt.mystudyappshop.Util.LogUtils;
import com.demo.swt.mystudyappshop.Util.NetworkUtils;
import com.demo.swt.mystudyappshop.Wight.Config;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.net.IonNetInterface;
import com.demo.swt.mystudyappshop.net.MyIntercept;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * Created by pc on 2016/12/5.
 */

public class MyApplication extends Application {
    private static Context mContext;
    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SwtToast.init(mContext);
        //只在debug下才打印出日志
        LogUtils.setDebug(Config.isDebug);
        UMShareAPI.get(this);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this).setProgressiveJpegConfig(new SimpleProgressiveJpegConfig()).build();
        Fresco.initialize(this, config);
//        com.wanjian.sak.LayoutManager.init(this);
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SMSSDK.initSDK(this, "1b352297f9785", "f955ca405239479857a016b78b3bac5b");
        NetworkUtils.setContext(this);
        IonNetInterface.get().start(this);
        IonNetInterface.get().setInterceptNet(new MyIntercept());
        LoadImgUtils.setContext(this);
        MonitorHelper.INSTANCE.init(this, "freebrio");
//        BigImageViewer.initialize(GlideImageLoader.with(this));

    }


    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }

}
