package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.swt.mystudyappshop.Http.OkHttpClientManager;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.NewBannerBean;
import com.demo.swt.mystudyappshop.bean.NewBannerListBean;
import com.squareup.okhttp.Request;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class TabMainFragment extends Fragment {

    public static TabMainFragment newInstance() {

        Bundle args = new Bundle();

        TabMainFragment fragment = new TabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SliderLayout sliderLayout;
    private PagerIndicator indicator;
    private List<NewBannerBean> bannerBeanList = new ArrayList<>();
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        button = (Button) view.findViewById(R.id.share);
        final UMImage image = new UMImage(getActivity(), R.mipmap.psb);//本地文件  zhe
        final UMImage image1 = new UMImage(getActivity(), R.mipmap.psb1);//本地文件 95
        final UMImage image2 = new UMImage(getActivity(), R.mipmap.psb2);//本地文件 shou
        final UMImage image3 = new UMImage(getActivity(), R.mipmap.psb3);//本地文件 yao
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


        requestImage();
        return view;
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    //okhttp网路请求
    private void requestImage() {
        String url = "  http://www.imooc.com/api/teacher?type=4&num=30";

        OkHttpClientManager.getAsyn(url,
                new OkHttpClientManager.ResultCallback<NewBannerListBean>() {


                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(NewBannerListBean response) {
                        bannerBeanList = response.getData().subList(0, 3);
                        initSlider();
                        //requestRecycleview();
                    }


                });

    }

    /*测试debug用
        @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            requestRecycleview();

        }*/


    /**
     * 封装一下这个加载banner的方法
     */
    private void initSlider() {
        for (final NewBannerBean bannerBean : bannerBeanList) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(bannerBean.getDescription())
                    .image(bannerBean.getPicSmall());
            //给每个textSliderView添加点击事件
           /* textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {

                }
            });*/
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        sliderLayout.setDuration(5000);
        //给sliderlayotu添加滑动改变事件
   /*     sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("swt", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("swt", "onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("swt", "onPageScrollStateChanged");
            }
        });*/
    }

    //生命周期结束的时候，banner停止滑动
    @Override
    public void onDestroy() {
        super.onDestroy();
        sliderLayout.stopAutoCycle();
    }
}
