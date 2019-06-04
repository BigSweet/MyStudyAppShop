package com.demo.swt.mystudyappshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Activity.HooliganActivity;
import com.demo.swt.mystudyappshop.Fragment.TabDuanZiFragment;
import com.demo.swt.mystudyappshop.Fragment.TabMainFragment;
import com.demo.swt.mystudyappshop.Fragment.TabSettingFragment;
import com.demo.swt.mystudyappshop.Util.QQNaviView;
import com.demo.swt.mystudyappshop.Wight.CstTopBanner;
import com.demo.swt.mystudyappshop.Wight.NoPreloadViewPager;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import fragment.TabFenLeiFragment;
import fragment.TabFriendFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback {
    private CstTopBanner cstTopBanner;
    private QQNaviView MainNavi, FriendNavi, FenLeiNavi, DuanZiNavi, SetitingNavi;
    //    private ImageButton MainButton, FriendButton, FenLeiButton, DuanZiButton, SettingButton;
    private TextView MainTv, FriendTv, FenLeiTv, DuanZiTv, SettingTv;
    //    private ViewPager mViewPager;
    private NoPreloadViewPager mViewPager;
    private List<Fragment> mViews = new ArrayList<>();
    private List<Integer> imglist = new ArrayList<>();
    private List<Integer> selectimglist = new ArrayList<>();
    private List<Integer> smallselectimglist = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        Log.d("dpi", "dpi+" + getResources().getDisplayMetrics().densityDpi);
    }


    private void initView() {
        mViewPager = findViewById(R.id.viewpager);

        MainNavi = (QQNaviView) findViewById(R.id.id_tab_zhuye);
        FriendNavi = (QQNaviView) findViewById(R.id.id_tab_frd);
        FenLeiNavi = (QQNaviView) findViewById(R.id.id_tab_fenlei);
        DuanZiNavi = (QQNaviView) findViewById(R.id.id_tab_duanzi);
        SetitingNavi = (QQNaviView) findViewById(R.id.id_tab_setting);

        MainTv = (TextView) findViewById(R.id.tv_zhuye);
        FriendTv = (TextView) findViewById(R.id.tv_friend);
        FenLeiTv = (TextView) findViewById(R.id.tv_fenlei);
        DuanZiTv = (TextView) findViewById(R.id.tv_duanzi);
        SettingTv = (TextView) findViewById(R.id.tv_center);

//        MainLayout.setOnClickListener(this);
        FriendNavi.setOnClickListener(this);
        FenLeiNavi.setOnClickListener(this);
        DuanZiNavi.setOnClickListener(this);
        SetitingNavi.setOnClickListener(this);

        getFrament();
        getimg();
        getname();
        getSelectImg();
        mViewPager.setAdapter(new ViewPagweAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currItem = mViewPager.getCurrentItem();
                reset();
                resetColor();
                switch (currItem) {
                    case 0:
                        MainNavi.setBigIcon(selectimglist.get(position));
                        MainNavi.setSmallIcon(R.drawable.small);
                        MainTv.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case 1:
                        FriendNavi.setBigIcon(selectimglist.get(1));
                        FriendNavi.setSmallIcon(smallselectimglist.get(0));
                        FriendTv.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case 2:
                        FenLeiNavi.setBigIcon(selectimglist.get(2));
//                        FenLeiNavi.setSmallIcon(R.drawable.toumingtaiyang);
                        FenLeiTv.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        DuanZiNavi.setBigIcon(selectimglist.get(3));
                        DuanZiNavi.setSmallIcon(smallselectimglist.get(1));
                        DuanZiTv.setTextColor(getResources().getColor(R.color.blue));
                        break;
                    case 4:
                        SetitingNavi.setBigIcon(selectimglist.get(4));
                        SetitingNavi.setSmallIcon(smallselectimglist.get(2));
                        SettingTv.setTextColor(getResources().getColor(R.color.blue));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(new BootCompleteReceiver(), filter);

    }

    public class BootCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                HooliganActivity.startHooligan();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                HooliganActivity.killHooligan();
            }
        }
    }

    private void resetColor() {
        MainTv.setTextColor(Color.parseColor("#2b2b2b"));
        FriendTv.setTextColor(Color.parseColor("#2b2b2b"));
        FenLeiTv.setTextColor(Color.parseColor("#2b2b2b"));
        DuanZiTv.setTextColor(Color.parseColor("#2b2b2b"));
        SettingTv.setTextColor(Color.parseColor("#2b2b2b"));
    }

    private void getSelectImg() {
        selectimglist.add(R.drawable.biglanseaixin);
        selectimglist.add(R.drawable.star_big);
        smallselectimglist.add(R.drawable.star_small);
        selectimglist.add(R.drawable.select_fenlei_taiyang);
//        smallselectimglist.add(R.drawable.bubble_small);
        selectimglist.add(R.drawable.bubble_big);
        smallselectimglist.add(R.drawable.bubble_small);
        selectimglist.add(R.drawable.person_big);
        smallselectimglist.add(R.drawable.person_small);
    }


    TabFenLeiFragment mTabFenLeiFragment;
    private void getFrament() {
        mTabFenLeiFragment=TabFenLeiFragment.newInstance();
        mViews.add(TabMainFragment.newInstance());
        mViews.add(TabFriendFragment.newInstance());
        mViews.add(mTabFenLeiFragment);
        mViews.add(TabDuanZiFragment.newInstance());
        mViews.add(TabSettingFragment.newInstance());
    }


    public List<String> getname() {
        nameList.add("主页");
        nameList.add("朋友圈");
        nameList.add("分类");
        nameList.add("段子");
        nameList.add("个人中心");
        return nameList;
    }

    public List<Integer> getimg() {
        imglist.add(R.drawable.pangziaixin);
        imglist.add(R.drawable.pre_star_big);
        imglist.add(R.drawable.toumingtaiyang);
        imglist.add(R.drawable.pre_bubble_big);
        imglist.add(R.drawable.pre_person_big);
        return imglist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_zhuye:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.id_tab_frd:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.id_tab_fenlei:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.id_tab_duanzi:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.id_tab_setting:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    private void reset() {
        MainNavi.setBigIcon(imglist.get(0));
        MainNavi.setSmallIcon(R.drawable.small);
        FriendNavi.setBigIcon(imglist.get(1));
        FriendNavi.setSmallIcon(R.drawable.pre_star_small);
        FenLeiNavi.setBigIcon(imglist.get(2));
//        FenLeiNavi.setSmallIcon(R.drawable.toumingtaiyang);
        DuanZiNavi.setBigIcon(imglist.get(3));
        DuanZiNavi.setSmallIcon(R.drawable.pre_bubble_small);
        SetitingNavi.setBigIcon(imglist.get(4));
        SetitingNavi.setSmallIcon(R.drawable.pre_person_small);
    }

    class ViewPagweAdapter extends FragmentPagerAdapter {


        public ViewPagweAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mViews.get(position);
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

    }

    private void initToolBar() {
        cstTopBanner = (CstTopBanner) findViewById(R.id.csttopbanner);

        if (needToolBar() && null != cstTopBanner) {
            cstTopBanner.setVisibility(View.VISIBLE);
            cstTopBanner.getCentreText().setCompoundDrawables(null, null, null, null);
            cstTopBanner.getCentreText().setOnClickListener(null);
        }
        cstTopBanner.setCentre(null, "孙文韬的学习app", null);
        cstTopBanner.setLeft(R.mipmap.back, "", null);
    }


    protected boolean needToolBar() {
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0

                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTabFenLeiFragment.requestPhotoSuccess();

                } else {

// permission denied, boo! Disable the

// functionality that depends on this permission.

                }
                return;
            }
        }
    }



}
