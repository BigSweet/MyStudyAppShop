package com.demo.swt.mystudyappshop.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.Adapter.CommonAdapter;
import com.demo.swt.mystudyappshop.Adapter.ViewHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.CardConfig;
import com.demo.swt.mystudyappshop.Util.OverLayCardLayoutManager;
import com.demo.swt.mystudyappshop.Util.TanTanCallback;
import com.demo.swt.mystudyappshop.bean.SwipeCardBean;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TanTanActivity extends AppCompatActivity {
    RecyclerView mRv;
    CommonAdapter<SwipeCardBean> mAdapter;
    List<SwipeCardBean> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());
        mRv.setAdapter(mAdapter = new CommonAdapter<SwipeCardBean>(this, R.layout.item_swipe_card, mDatas = SwipeCardBean.initDatas()) {
            public static final String TAG = "zxt/Adapter";

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
                super.onBindViewHolder(holder, position);
            }

            @Override
            public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
                Log.d(TAG, "convert() called with: viewHolder = [" + viewHolder + "], swipeCardBean = [" + swipeCardBean + "]");
                viewHolder.setText(R.id.tvName, swipeCardBean.getName());
                viewHolder.setText(R.id.tvPrecent, swipeCardBean.getPostition() + " /" + mDatas.size());
                viewHolder.getView(R.id.cardv).setBackgroundColor(getResources().getColor(R.color.gray9));
                Picasso.with(TanTanActivity.this).load(swipeCardBean.getUrl()).into((ImageView) viewHolder.getView(R.id.iv));
            }
        });

        CardConfig.initConfig(this);

//        final TanTanCallback callback = new TanTanCallback(mRv, mAdapter, mDatas);
        TanTanCallback callback = new TanTanCallback(mRv, mAdapter, mDatas);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }


}

