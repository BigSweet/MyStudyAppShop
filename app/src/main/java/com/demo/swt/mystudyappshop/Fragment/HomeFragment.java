package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.swt.mystudyappshop.Http.OkHttpClientManager;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.NewBannerListBean;
import com.demo.swt.mystudyappshop.bean.NewBannerBean;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private PagerIndicator indicator;
    private List<NewBannerBean> bannerBeanList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);

        requestImage();
        return view;
    }


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
        sliderLayout.stopAutoCycle();
        super.onDestroy();
    }
}
