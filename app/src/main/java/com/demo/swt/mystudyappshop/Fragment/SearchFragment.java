package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.demo.swt.mystudyappshop.Adapter.SearchAdapter;
import com.demo.swt.mystudyappshop.Http.OkHttpClientManager;
import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.RecyclerLinearDivider;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.FeedBeanList;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class SearchFragment extends Fragment {
    private List<FeedBean> feedlist = new ArrayList<>();
    private List<FeedBean> morenlist = new ArrayList<>();
    private SearchAdapter myAdapter;
    private RecyclerView mHomeRv;
    private String nt = "";
    private String host = "https://aggr.anlaiye.com.cn/aggre/user/1311139/follow/feed/list?appid=1&appplt=aph&token=c42832d72cdb08f39d2d4c930327ecf7&appver=3.1.0&pageSize=20";
    private MaterialRefreshLayout materialRefreshLayout;
    private String url = host + nt;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFESH = 1;
    private static final int STATE_LOADMORE = 2;
    private int state = STATE_NORMAL;
    private FeedBeanList feedbeanlist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        mHomeRv = (RecyclerView) view.findViewById(R.id.home_recycle);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        requestRecycleview();
        initReswipe();

        return view;
    }

    private void initReswipe() {
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state = STATE_REFESH;
                requestRecycleview();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                state = STATE_LOADMORE;
                nt = feedbeanlist.getData().getNt();
                url = host + "&nt=" + nt;
                requestRecycleview();
            }

        });


    }


    //请求recyclerview的数据
    private void requestRecycleview() {
        OkHttpClientManager.getAsyn(url,
                new OkHttpClientManager.ResultCallback<FeedBeanList>() {


                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(FeedBeanList response) {
                        if (response.getData() != null) {
                            feedlist = response.getData().getList();
                            morenlist = response.getData().getList();
                            feedbeanlist = response;
                            initRecycler();
                        }

                    }


                });

    }


    //设置recyclerview的数据和基本布局
    private void initRecycler() {

        switch (state) {
            case STATE_NORMAL:
                myAdapter = new SearchAdapter(getActivity(), feedlist, R.layout.wuyu);
                mHomeRv.addItemDecoration(new RecyclerLinearDivider(getActivity(), RecyclerView.VERTICAL, 5, getResources().getColor(R.color.gray1)));
                mHomeRv.setAdapter(myAdapter);
                mHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<FeedBean>() {
                    @Override
                    public void onClick(int position, FeedBean feedBean) {
                        SwtToast.show("item被点击了" + position);
                    }
                });
                break;

            case STATE_LOADMORE:
                myAdapter.addData(myAdapter.getdata().size(), feedlist);
                mHomeRv.scrollToPosition(myAdapter.getdata().size());
                materialRefreshLayout.finishRefreshLoadMore();
                break;

            case STATE_REFESH:
                myAdapter.addData(morenlist);
                materialRefreshLayout.finishRefresh();
                break;
        }


    }
}
