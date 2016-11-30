package com.demo.swt.mystudyappshop.Fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.BannerBean;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private PagerIndicator indicator;
    private List<BannerBean> bannerBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        initSlider();
        return view;
    }

    /**
     * 封装一下这个加载banner的方法
     */
    private void initSlider() {
        BannerBean bannerBean1 = new BannerBean(
                "http://bpic.588ku.com/element_origin_min_pic/16/11/18/3d130707fdd156a27b92936e9e135dd0.jpg", "双12");
        BannerBean bannerBean2 = new BannerBean(
                "http://bpic.588ku.com/back_pic/00/03/35/34561e1273d9aeb.jpg", "DREAM");
        BannerBean bannerBean3 = new BannerBean(
                "http://bpic.588ku.com/element_origin_min_pic/00/00/11/225833e8c2a2889.jpg", "天猫");

        bannerBeanList.add(bannerBean1);
        bannerBeanList.add(bannerBean2);
        bannerBeanList.add(bannerBean3);
        for (BannerBean bannerBean : bannerBeanList) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(bannerBean.getDes())
                    .image(bannerBean.getImg());
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderLayout.setDuration(5000);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
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
        });
    }

    @Override
    public void onDestroy() {
        sliderLayout.stopAutoCycle();
        super.onDestroy();

    }
}
