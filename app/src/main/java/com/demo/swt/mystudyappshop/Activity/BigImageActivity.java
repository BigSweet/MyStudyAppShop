package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoPreloadViewPager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    private ArrayList<String> tulist = new ArrayList<>();
    private NoPreloadViewPager noPreloadViewPager;
    private int pos;
    private List<SimpleDraweeView> mSimplelist = new ArrayList<>();

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
                return  view== object;
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


    protected List<SimpleDraweeView> getSimpleList() {
        for (int i = 0; i < tulist.size(); i++) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            simpleDraweeView.setLayoutParams(params);
            simpleDraweeView.setImageURI(tulist.get(i));
            mSimplelist.add(simpleDraweeView);
            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        return mSimplelist;

    }

}
