package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview中的viewpager的适配器
 * Created by Administrator on 2016/8/30.
 */
public class RecyclePagerAdapter extends PagerAdapter {
    private Context context;
    private List<SimpleDraweeView> mlist;
    private List<String> mtulist;


    public RecyclePagerAdapter(Context context, List<String> tulist) {
        this.context = context;
        this.mtulist = tulist;
        mlist = new ArrayList<>();

    }


    @Override
    public int getCount() {
        return mtulist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //  container.removeView((View) object);
        container.removeView(mlist.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mtulist.size() > 0) {
            for (int i = 0; i < mtulist.size(); i++) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                simpleDraweeView.setLayoutParams(params);
                simpleDraweeView.setImageURI(mtulist.get(position));
                container.addView(simpleDraweeView);
                mlist.add(simpleDraweeView);
                return simpleDraweeView;
            }
        }
        return null;
    }




}
