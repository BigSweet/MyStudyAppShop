package com.demo.swt.mystudyappshop;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by pc on 2016/12/5.
 */

public class MyApplication extends Application {
    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this).setProgressiveJpegConfig(new SimpleProgressiveJpegConfig()).build();
        Fresco.initialize(this, config);
        com.wanjian.sak.LayoutManager.init(this);
        SDKInitializer.initialize(getApplicationContext());

    }

}
