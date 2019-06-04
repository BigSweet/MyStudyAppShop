package com.demo.swt.mystudyappshop.contract;


import com.demo.swt.mystudyappshop.bean.BannerBean;

/**
 * 介绍：广告
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/7/19
 */
public interface IBannerConstact {
    interface IView  {
        void showBanner(BannerBean bannerBeen);
    }

    interface IPresenter {
        void requestBanner();

    }
}
