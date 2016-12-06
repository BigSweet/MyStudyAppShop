package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.demo.swt.mystudyappshop.Adapter.RecyclePagerAdapter;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoPreloadViewPager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    //    private String morentu = "http://pic.anlaiye.com.cn/1c978a81759543cb8f1c0fed0a4b2416_800x1066.png";
    private ArrayList<String> tulist = new ArrayList<>();
//    private ViewPager viewPager;
    private NoPreloadViewPager noPreloadViewPager;
    private List<SimpleDraweeView> mlist = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigimagee);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
        noPreloadViewPager = (NoPreloadViewPager) findViewById(R.id.nopreload);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tulist = bundle.getStringArrayList("tulist");
//        viewPager.setOffscreenPageLimit(0);
        noPreloadViewPager.setAdapter(new RecyclePagerAdapter(this, tulist));
    }

}
