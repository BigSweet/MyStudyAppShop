package com.demo.swt.mystudyappshop.presenter;

import com.demo.swt.mystudyappshop.Http.OkHttpClientManager;
import com.demo.swt.mystudyappshop.Listener.RequestListner;
import com.demo.swt.mystudyappshop.Util.RequestParemUtils;
import com.demo.swt.mystudyappshop.bean.BannerBean;
import com.demo.swt.mystudyappshop.bean.BannerBeanList;
import com.demo.swt.mystudyappshop.contract.IBannerConstact;
import com.demo.swt.mystudyappshop.contract.TokenBean;
import com.demo.swt.mystudyappshop.net.NetInterfaceFactory;
import com.demo.swt.mystudyappshop.net.RequestParem;
import com.demo.swt.mystudyappshop.result.ResultMessage;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;


/**
 * 介绍：banner
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/7/19
 */
public class BannerPresenter implements IBannerConstact.IPresenter {

    private final IBannerConstact.IView view;

    public BannerPresenter(IBannerConstact.IView view) {
        this.view = view;
    }

    @Override
    public void requestBanner() {
        OkHttpClientManager.getAsyn("http://www.wanandroid.com/banner/json", new OkHttpClientManager.ResultCallback<BannerBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BannerBean response) {
                         view.showBanner(response);


            }


        });


    }




}
