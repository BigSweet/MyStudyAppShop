package com.demo.swt.mystudyappshop.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.GlideImageLoader;
import com.demo.swt.mystudyappshop.bean.BannerBean;
import com.demo.swt.mystudyappshop.contract.IBannerConstact;
import com.demo.swt.mystudyappshop.mobike.MoBikeView;
import com.demo.swt.mystudyappshop.presenter.BannerPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.youth.banner.Banner;

/**
 * Created by pc on 2016/11/29.
 */

public class TabMainFragment extends Fragment implements IBannerConstact.IView, SensorEventListener {
    private IBannerConstact.IPresenter mIPresenter;
    private MoBikeView mMobikeView;
    Banner banner;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int[] mImgs = {
            R.mipmap.ic_share_fb,
            R.mipmap.ic_share_kongjian,
            R.mipmap.ic_share_pyq,
            R.mipmap.ic_share_qq,
            R.mipmap.ic_share_tw,
            R.mipmap.ic_share_wechat,
            R.mipmap.ic_share_weibo
    };

    public static TabMainFragment newInstance() {

        Bundle args = new Bundle();

        TabMainFragment fragment = new TabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Button button;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIPresenter = new BannerPresenter(this);
        mIPresenter.requestBanner();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
//        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
//        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
         banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(6000);

        button = (Button) view.findViewById(R.id.share);
        final UMImage image = new UMImage(getActivity(), R.mipmap.psb);//本地文件  zhe
        final UMImage image1 = new UMImage(getActivity(), R.mipmap.psb1);//本地文件 95
        final UMImage image2 = new UMImage(getActivity(), R.mipmap.psb2);//本地文件 shou
        final UMImage image3 = new UMImage(getActivity(), R.mipmap.psb3);//本地文件 yao
        mMobikeView = view.findViewById(R.id.mo_bike);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        addViews();

        mMobikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMobikeView.onRandomChanged();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withText("壶窍家")
                        .withTitle("点击查看壶窍家的照")
                        .withMedia(image2)
                        .withTargetUrl("http://b158.photo.store.qq.com/psb?/V13BADFb0qMTmp/WxCrMRSU8NmoifYbC6rgB00WKajgXVhMaBSqvsttoKA!/b/dLqHMV6kDgAA&bo=WAIgAwAAAAABB1k!&rf=viewer_4")
                        .setCallback(umShareListener)
                        .share();*/
                v.setHapticFeedbackEnabled(true);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withText("煤炭坝")
                        .withTitle("点击查看煤炭坝的照")
                        .withMedia(image1)
                        .withTargetUrl("http://b158.photo.store.qq.com/psb?/V13BADFb0qMTmp/r9D86*HoqetA*U9*AWee1oxzLtmOQiYlyoM5QBuPq6Q!/b/dNyAMV5gDgAA&bo=ngL2AQAAAAABB0s!&rf=viewer_4")
                        .setCallback(umShareListener)
                        .share();


        /*        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withText("姚")
                        .withTitle("姚，并没有找到照片")
                        .withMedia(image3)
                        .withTargetUrl("http://a2.qpic.cn/psb?/V13BADFb0qMTmp/IRuW1w0MCRYYEU8xW8BKiLlMoi4iyx4*uk4iw.mevaU!/c/dI0BAAAAAAAA&bo=0wDhAAAAAAACBxE!&rf=viewer_4")
                        .setCallback(umShareListener)
                        .share();*/

                // http://b158.photo.store.qq.com/psb?/V13BADFb0qMTmp/r9D86*HoqetA*U9*AWee1oxzLtmOQiYlyoM5QBuPq6Q!/b/dNyAMV5gDgAA&bo=ngL2AQAAAAABB0s!&rf=viewer_4
              /*  new ShareAction(getActivity()).withText("颜哲，大傻逼").withMedia(image)
                        .withTitle("点开查看颜哲美照")
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .withTargetUrl("http://b6.photo.store.qq.com/psb?/V13BADFb3i30mP/zLkSa1wjAE5WmPZsa.JnR9yRg3g7iyYE4KMjhai0R7Q!/m/dAYAAAAAAAAAnull&bo=gAJVAwAAAAAFB*A!&rf=photolist&t=5")
                        .setCallback(umShareListener).open();*/
            }

            //  UmengTool.getSignature(getActivity());

        });

        return view;
    }

    private void addViews() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        for (int i = 0; i < mImgs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(mImgs[i]);
            iv.setTag(R.id.wd_view_circle_tag, true);
            mMobikeView.addView(iv, lp);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1] * 2.0f;
            mMobikeView.onSensorChanged(-x, y);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //因为没有写刷新所以加上这个算了
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        banner.stopAutoPlay();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void showBanner(BannerBean bannerBeen) {
        if (null != bannerBeen) {
            //设置图片集合
            banner.setImages(bannerBeen.getData());
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

}
