package com.demo.swt.mystudyappshop.Fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class TabDuanZiFragment extends Fragment {

    public static TabDuanZiFragment newInstance() {

        Bundle args = new Bundle();

        TabDuanZiFragment fragment = new TabDuanZiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPager mViewPager;//
    private TabLayout mTabLayout; //
    private DuanZiFragment mDuanZiFragment;
    private DuanZiFragment mReMenFragment;
    private DuanZiFragment mXiaoShiFragment;
    private DuanZiFragment mWenZiFragment;
    private DuanZiFragment mChuangYueFragment;
    private DuanZiFragment mQiuTuFragment;
    private DuanZiFragment mXinXianFragment;
    public List<Fragment> mFragmentList = new ArrayList<>();
    private static final String[] mTitles = new String[]{"热图", "24小时", "热门", "文字", "穿越", "糗图", "新鲜"};
    private static int RE_TU = 1;
    private static int XIAO_SHI = 2;
    private static int RE_MEN = 3;
    private static int WEN_ZI = 4;
    private static int CHUANG_YUE = 5;
    private static int QIU_TU = 6;
    private static int XIN_XIAN = 7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.duanzi_main, container, false);
        mFragmentList.clear();
        mViewPager = (ViewPager) view.findViewById(R.id.duanzi_viepager);
        mTabLayout = (TabLayout) view.findViewById(R.id.duanzi_tab);
        mDuanZiFragment = getReTuFragment();
        mReMenFragment = getReMenFragment();
        mXiaoShiFragment = getXiaoShiFragment();
        mWenZiFragment = getWenziFragment();
        mChuangYueFragment = getChuangYueFragment();
        mQiuTuFragment = getQiuTuFragment();
        mXinXianFragment = getXinXianFragment();
        mFragmentList.add(mDuanZiFragment);
        mFragmentList.add(mReMenFragment);
        mFragmentList.add(mXiaoShiFragment);
        mFragmentList.add(mWenZiFragment);
        mFragmentList.add(mChuangYueFragment);
        mFragmentList.add(mQiuTuFragment);
        mFragmentList.add(mXinXianFragment);
        mXiaoShiFragment = getXiaoShiFragment();
        mViewPager.setAdapter(new MyFragmentAdapter(getFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }


    private class MyFragmentAdapter extends FragmentPagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return null == mFragmentList ? 0 : mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }


    private DuanZiFragment getReTuFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", RE_TU);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getReMenFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", RE_MEN);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getXiaoShiFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", XIAO_SHI);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getWenziFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", WEN_ZI);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getChuangYueFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", CHUANG_YUE);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getQiuTuFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", QIU_TU);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }

    private DuanZiFragment getXinXianFragment() {

        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", XIN_XIAN);
        return (DuanZiFragment) Fragment.instantiate(getActivity(), DuanZiFragment.class.getName(), bundle);
    }
}
