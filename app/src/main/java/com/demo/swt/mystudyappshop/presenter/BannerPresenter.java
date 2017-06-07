package com.demo.swt.mystudyappshop.presenter;

import com.demo.swt.mystudyappshop.Listener.RequestListner;
import com.demo.swt.mystudyappshop.Util.RequestParemUtils;
import com.demo.swt.mystudyappshop.bean.BannerBean;
import com.demo.swt.mystudyappshop.bean.BannerBeanList;
import com.demo.swt.mystudyappshop.contract.IBannerConstact;
import com.demo.swt.mystudyappshop.contract.TokenBean;
import com.demo.swt.mystudyappshop.net.NetInterfaceFactory;
import com.demo.swt.mystudyappshop.net.RequestParem;
import com.demo.swt.mystudyappshop.result.ResultMessage;

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
    public void requestBanner(int type) {
        RequestParem requestParem = RequestParemUtils.getBanner(type);
        NetInterfaceFactory.getNetInterface().doRequest(requestParem, new RequestListner<BannerBeanList>(view, BannerBeanList.class) {
            @Override
            public boolean onSuccess(BannerBeanList bannerList) throws Exception {
                List<BannerBean> bannerBeen = bannerList.getList();
                view.showBanner(bannerBeen);
                return true;
            }

            @Override
            public void onEnd(ResultMessage e) {
                super.onEnd(e);
                if(!e.isSuccess()){
                    view.showBanner(new ArrayList<BannerBean>());
                }
            }
        });

    }

    @Override
    public void requestToken() {
        RequestParem requestParem = RequestParemUtils.gettoken();
        NetInterfaceFactory.getNetInterface().doRequest(requestParem, new RequestListner<TokenBean>(view, TokenBean.class) {
            @Override
            public boolean onSuccess(TokenBean TokenBean) throws Exception {
//                List<BannerBean> bannerBeen = bannerList.getList();
//                view.showBanner(bannerBeen);
                return true;
            }

            @Override
            public void onEnd(ResultMessage e) {
                super.onEnd(e);
                if(!e.isSuccess()){
                    view.showBanner(new ArrayList<BannerBean>());
                }
            }
        });

    }



}
