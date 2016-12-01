package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.swt.mystudyappshop.Adapter.CommonAdapter;
import com.demo.swt.mystudyappshop.Holder.ViewHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.BannerBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private PagerIndicator indicator;
    private List<BannerBean> bannerBeanList = new ArrayList<>();
    private RecyclerView mHomeRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        mHomeRv = (RecyclerView) view.findViewById(R.id.home_recycle);
        requestImage();
        // initRecycler();
        initSlider();
        return view;
    }

    private List<BannerBean> mbannerlist;
    private Gson gson = new Gson();

    //okhttp网路请求
    private void requestImage() {
        String url = "http://10.0.12.130:8080/nihao.txt";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Type type = new TypeToken<List<BannerBean>>() {
                    }.getType();
                  // mbannerlist = gson.fromJson(json, type);
                    Log.e(getTag(), json);
                }
            }
        });


    }

    private void initRecycler() {
       /* mHomeRv.setAdapter(new CommonAdapter(getActivity(), ) {

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public void convert(ViewHolder holder, Object o) {

            }

        });*/
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
        for (final BannerBean bannerBean : bannerBeanList) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(bannerBean.getDes())
                    .image(bannerBean.getImg());
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

    @Override
    public void onDestroy() {
        sliderLayout.stopAutoCycle();
        super.onDestroy();
    }
}
