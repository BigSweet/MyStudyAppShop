package com.demo.swt.mystudyappshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Fragment.CateGoryFragment;
import com.demo.swt.mystudyappshop.Fragment.HomeFragment;
import com.demo.swt.mystudyappshop.Fragment.MyCenterFragment;
import com.demo.swt.mystudyappshop.Fragment.SearchFragment;
import com.demo.swt.mystudyappshop.Fragment.ShopCartFragment;
import com.demo.swt.mystudyappshop.Wight.CstToolbar;
import com.demo.swt.mystudyappshop.Wight.FragmentTabHost;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.bean.Tab;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater inflater;
    private List<Tab> mTabs = new ArrayList<>();
    private CstToolbar mCstToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initTab();
        initToolBar();
        SwtToast.init(this);
    }

    private void initToolBar() {
        mCstToolBar = (CstToolbar) findViewById(R.id.csttoolbar);
        mCstToolBar.setTitle(R.string.title);
        mCstToolBar.setLeft(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwtToast.show("你好");
            }
        });
    }

    private void initTab() {
        Tab tab_home = new Tab(R.string.home, R.drawable.select_icon_home, HomeFragment.class);
        Tab tab_search = new Tab((R.string.search), R.drawable.select_icon_search, SearchFragment.class);
        Tab tab_category = new Tab((R.string.category), R.drawable.select_icon_category, CateGoryFragment.class);
        Tab tab_shopcart = new Tab((R.string.shopcart), R.drawable.select_icon_shopcart, ShopCartFragment.class);
        Tab tab_mycenter = new Tab((R.string.mycenter), R.drawable.select_icon_my, MyCenterFragment.class);
        mTabs.add(tab_home);
        mTabs.add(tab_search);
        mTabs.add(tab_category);
        mTabs.add(tab_shopcart);
        mTabs.add(tab_mycenter);
        inflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        // mTabHost.setup(this,getSupportFragmentManager());
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }


    private View buildIndicator(Tab tab) {
        View view = inflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView txt = (TextView) view.findViewById(R.id.txt_indicator);
        img.setImageResource(tab.getIcon());
        txt.setText(getString(tab.getTitle()));
        return view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }





}
