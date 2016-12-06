package com.demo.swt.mystudyappshop.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.swt.mystudyappshop.Adapter.RecyclePagerAdapter;
import com.demo.swt.mystudyappshop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    //    private String morentu = "http://pic.anlaiye.com.cn/1c978a81759543cb8f1c0fed0a4b2416_800x1066.png";
    private ArrayList<String> tulist = new ArrayList<>();
    private ViewPager viewPager;
    private List<SimpleDraweeView> mlist = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigimagee);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tulist = bundle.getStringArrayList("tulist");
        viewPager.setAdapter(new RecyclePagerAdapter(this, tulist));

    }

}
