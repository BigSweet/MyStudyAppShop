package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.swt.mystudyappshop.Adapter.CommonAdapter;
import com.demo.swt.mystudyappshop.Adapter.MyAdapter;
import com.demo.swt.mystudyappshop.Holder.ViewHolder;
import com.demo.swt.mystudyappshop.Http.OkHttpClientManager;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageUtils;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.FeedBeanList;
import com.demo.swt.mystudyappshop.bean.NewBannerListBean;
import com.demo.swt.mystudyappshop.bean.NewBannerBean;
import com.demo.swt.mystudyappshop.bean.PostInfoBean;
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
    private RecyclerView mHomeRv;
    private List<FeedBean> feedlist = new ArrayList<>();
    private List<PostInfoBean> postlist = new ArrayList<>();
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        mHomeRv = (RecyclerView) view.findViewById(R.id.home_recycle);
        requestImage();
        return view;
    }


    //okhttp网路请求
    private void requestImage() {
        // String url = "http://aggr.imcoming.com.cn/aggre/rmd/feed/list?appid=1&school_id=5&appplt=aph&token=942755b4e265cff31f1b27c37b32e036&appver=3.1.0&pageSize=20";
        String url = "  http://www.imooc.com/api/teacher?type=4&num=30";
        // String url = "http://relation.imcoming.com.cn/v1/user/assoc/role?appid=1&appplt=aph&org_id=226&token=942755b4e265cff31f1b27c37b32e036&appver=3.1.0&user_id=9295747";

        OkHttpClientManager.getAsyn(url,
                new OkHttpClientManager.ResultCallback<NewBannerListBean>() {


                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(NewBannerListBean response) {
                        bannerBeanList = response.getData().subList(0, 3);
                        initSlider();
                        requestRecycleview();
                    }


                });

    }

/*
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        requestRecycleview();

    }*/

    private void requestRecycleview() {
        String url = "http://aggr.imcoming.com.cn/aggre/rmd/feed/list?appid=1&school_id=5&appplt=aph&token=942755b4e265cff31f1b27c37b32e036&appver=3.1.0&pageSize=20";

        OkHttpClientManager.getAsyn(url,
                new OkHttpClientManager.ResultCallback<FeedBeanList>() {


                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(FeedBeanList response) {
                        feedlist = response.getData().getList();
                        //  postlist=feedlist.get()
                        initRecycler();
                    }


                });

    }

    private void initRecycler() {

      /*  for (FeedBean feedBean : feedlist) {
            feedBean.getPost().getImages();
        }*/
        myAdapter = new MyAdapter(getActivity(), feedlist);
        mHomeRv.setAdapter(myAdapter);
        mHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
/*
        mHomeRv.setAdapter(new CommonAdapter<FeedBean>(getActivity(), R.layout.home_rv_left_item, feedlist) {

            @Override
            public void convert(ViewHolder holder, FeedBean feedBean) {
                ImageUtils.loadImage((ImageView) holder.getView(R.id.imageview_top), feedBean.getPost().getImages().get(1));
                ImageUtils.loadImage((ImageView) holder.getView(R.id.imageview_top), feedBean.getPost().getImages().get(2));
                ImageUtils.loadImage((ImageView) holder.getView(R.id.imageview_top), feedBean.getPost().getImages().get(3));
            }


        });*/
    }

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

    @Override
    public void onDestroy() {
        sliderLayout.stopAutoCycle();
        super.onDestroy();
    }
}
