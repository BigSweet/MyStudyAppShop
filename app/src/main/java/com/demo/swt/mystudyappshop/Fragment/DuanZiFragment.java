package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.demo.swt.mystudyappshop.Adapter.BaiKeAdapter;
import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.bean.BaiKeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/22
 */

public class DuanZiFragment extends Fragment {
    private int index = 1;
    private List<BaiKeBean> mBaiKeBeanList = new ArrayList<>();
    private List<BaiKeBean> nomallist = new ArrayList<>();
    private BaiKeAdapter myAdapter;
    private RecyclerView mBaiKeRv;
    private MaterialRefreshLayout materialRefreshLayout;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFESH = 1;
    private static final int STATE_LOADMORE = 2;
    private int state = STATE_NORMAL;
    private int FlAG;
    String remen = "http://www.qiushibaike.com/8hr/page/" + index + "/";
    String retu = "http://www.qiushibaike.com/imgrank/page/" + index + "/";
    String xiaoshi = "http://www.qiushibaike.com/hot/page/" + index + "/";
    String wenzi = "  http://www.qiushibaike.com/text/page/" + index + "/";
    String chuanyue = " http://www.qiushibaike.com/history/862c9499d3e81ceb75ef070ed0c11b23/page/" + index + "/";
    String qiutu = "    http://www.qiushibaike.com/pic/page/" + index + "/";
    String xinxian = "http://www.qiushibaike.com/textnew/page/" + index + "/";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initRecycler();

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            FlAG = bundle.getInt("FLAG");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qiushibaike, container, false);
        mBaiKeRv = (RecyclerView) view.findViewById(R.id.baike_recycle);
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
                index++;
                requestRecycleview();
            }

        });


    }


    //请求recyclerview的数据
    private void requestRecycleview() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String url = null;
                    if (FlAG == 1) {
                        url = retu;
                    }
                    if (FlAG == 2) {
                        url = xiaoshi;
                    }
                    if (FlAG == 3) {
                        url = remen;
                    }
                    if (FlAG == 4) {
                        url = wenzi;
                    }
                    if (FlAG == 5) {
                        url = chuanyue;
                    }
                    if (FlAG == 6) {
                        url = qiutu;
                    }
                    if (FlAG == 7) {
                        url = xinxian;
                    }

                    Document document = Jsoup.connect(url).get();
                    Elements datas = document.select("div.article.block.untagged.mb15");
                    for (int i = 0; i < datas.size(); i++) {
                        BaiKeBean mBaiKeBean = new BaiKeBean();
                        List<String> imgs = new ArrayList<>();
                        Element element = datas.get(i);
                        Elements infoData = element.select("div.author.clearfix");
                        Elements picdata = element.select("div.thumb");
                        Elements conentData = element.select("div.content");
                        if (infoData.size() > 0) {
                            mBaiKeBean.setTouxiang(infoData.get(0).getElementsByIndexEquals(0).attr("src"));
                            mBaiKeBean.setName(infoData.get(0).getElementsByIndexEquals(1).text());
                        } else {
                            mBaiKeBean.setTouxiang(null);
                            mBaiKeBean.setName("没有名字");
                        }
                        mBaiKeBean.setContent(conentData.text());
                        if (picdata.size() > 0) {
                            imgs.add(picdata.get(0).getElementsByIndexEquals(0).attr("src"));
                            mBaiKeBean.setImgs(imgs);
                        } else {

                        }
                        mBaiKeBeanList.add(mBaiKeBean);
                        nomallist.add(mBaiKeBean);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.obtainMessage();
            }
        }, 1000);

    }


    //设置recyclerview的数据和基本布局
    private void initRecycler() {
        switch (state) {
            case STATE_NORMAL:
                myAdapter = new BaiKeAdapter(getActivity(), mBaiKeBeanList, R.layout.baike_item);
//                mBaiKeRv.addItemDecoration(new RecyclerLinearDivider(getActivity(), RecyclerView.VERTICAL, 5, getResources().getColor(R.color.gray1)));
                mBaiKeRv.setAdapter(myAdapter);
                mBaiKeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                myAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<BaiKeBean>() {
                    @Override
                    public void onClick(int position, BaiKeBean baiKeBean, View view) {
                        SwtToast.show("item被点击了" + position);
                    }
                });
                break;

            case STATE_LOADMORE:
                myAdapter.addData(myAdapter.getdata().size(), mBaiKeBeanList);
                mBaiKeRv.scrollToPosition(myAdapter.getdata().size());
                materialRefreshLayout.finishRefreshLoadMore();
                break;

            case STATE_REFESH:
                myAdapter.addData(nomallist);
                materialRefreshLayout.finishRefresh();
                break;
        }


    }
}
