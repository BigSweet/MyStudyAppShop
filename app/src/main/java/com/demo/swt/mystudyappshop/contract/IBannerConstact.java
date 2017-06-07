package com.demo.swt.mystudyappshop.contract;


import com.demo.swt.mystudyappshop.Util.CstAutoSlideBaseView;
import com.demo.swt.mystudyappshop.bean.BannerBean;

import java.util.List;

/**
 * 介绍：广告
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间： 2016/7/19
 */
public interface IBannerConstact {
    interface IView  {
        void showBanner(List<BannerBean> bannerBeen);
        void showToken(TokenBean tokenBean);
        CstAutoSlideBaseView getSideBaseView();
    }

    interface IPresenter {
        void requestBanner(int type);
        void requestToken();
    }
}
