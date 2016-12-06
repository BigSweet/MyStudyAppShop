package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private int[] imagesid;
    private List<SimpleDraweeView> mlist;
    private List<String> mtulist;


    public RecyclePagerAdapter(Context context, List<String> tulist) {
        this.context = context;
        this.imagesid = imagesid;
        this.mtulist = tulist;
        mlist = new ArrayList<SimpleDraweeView>();

    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        simpleDraweeView.setLayoutParams(params);
        simpleDraweeView.setImageURI(mtulist.get(0));
        simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER);
        container.addView(simpleDraweeView);
        mlist.add(simpleDraweeView);
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return simpleDraweeView;
        // ImageView imageView = new ImageView(context);
        //imageView.setImageResource(imagesid[position % imagesid.length]);
        // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // container.addView(imageView);
        // mlist.add(imageView);
        //    return imageView;
    }


}
