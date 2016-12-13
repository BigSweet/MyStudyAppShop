package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.demo.swt.mystudyappshop.GoodDraweeView.MyPhotoView;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoPreloadViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    private ArrayList<String> tulist = new ArrayList<>();
    private NoPreloadViewPager noPreloadViewPager;
    private int pos;
    private List<MyPhotoView> mSimplelist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigimagee);
        noPreloadViewPager = (NoPreloadViewPager) findViewById(R.id.nopreload);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tulist = bundle.getStringArrayList("tulist");
        pos = bundle.getInt("pos");
        getSimpleList();
        noPreloadViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return tulist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mSimplelist.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                container.addView(mSimplelist.get(position));
                return mSimplelist.get(position);
            }

        });
        noPreloadViewPager.setCurrentItem(pos);
    }


    protected List<MyPhotoView> getSimpleList() {


        for (int i = 0; i < tulist.size(); i++) {

            MyPhotoView photoDraweeView = new MyPhotoView(getApplicationContext());


            photoDraweeView.setImageUri(tulist.get(i));
            mSimplelist.add(photoDraweeView);
            photoDraweeView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });

        }
        return mSimplelist;
    }


}
