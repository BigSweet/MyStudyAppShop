package com.demo.swt.mystudyappshop.BasePackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.swt.mystudyappshop.R;

import java.util.List;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/12
 */
public abstract class SWBaseFragment extends Fragment {
    protected View cacheView;
    protected FragmentManager mFragmentManager;
    protected CstTopBanner topBanner;
    private View divider;
    protected SWBaseActivity mActivity;
    protected Bundle bundle;
    protected LayoutInflater mInflater;

    //    protected Handler mHandler = new Handler();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (cacheView == null || !needCache()) {//如果view没有被初始化或者不需要缓存的情况下，重新初始化控件
            topBanner = mActivity.getCstTopBanner();
            divider = mActivity.getDivider();
            cacheView = initView(inflater, container);
            initView(savedInstanceState);
        } else {
            ViewGroup v = (ViewGroup) cacheView.getParent();
            if (v != null) {
                v.removeView(cacheView);
            }
        }
        onCreateView(savedInstanceState);
        return cacheView;

    }

    /**
     * true 避免当前Fragment被repalce后回退回来重走oncreateview，导致重复初始化View和数据
     *
     * @return
     */
    protected boolean needCache() {
        return true;
    }

    //直接findviewbyid在基类里面做了
    public <T extends View> T findViewById(int resId) {
        if (cacheView == null) {
            return null;
        }
        return (T) cacheView.findViewById(resId);
    }

    /**
     * 直接findViewById()初始化组件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (getLayoutId() == 0) {
            return null;
        }
        return inflater.inflate(getLayoutId(), container, false);
    }

    /**
     * 是否需要持有公共的toolbar，只有当fragment作为一个完整的页面时才需要
     * 暂未处理，默认处理方式：不重写resetToolBar（）
     *
     * @return
     */
    protected boolean needToolBar() {
        return true;
    }

    public void initToolBar() {
        if (needToolBar() && null != topBanner) {
            topBanner.setVisibility(View.VISIBLE);
            topBanner.getCentreText().setCompoundDrawables(null, null, null, null);
            topBanner.getCentreText().setOnClickListener(null);
        }
    }

    /**
     * 保证生命周期函数可以回调
     */
    public void onCreateView(Bundle savedInstanceState) {
        initToolBar();
    }

    /**
     * 拦截Activity的onbackpress方法
     * false  处理后，仍然交给Activity的onbackpress
     * ture  处理后，不交给Activity
     * <p/>
     * 正常情况下 ，重写该方法返回false
     *
     * @return
     */
    public boolean onFragmentBackPressd() {
        return false;
    }

    public void setFragmentBackResult(int resultCode, Bundle data) {
        mActivity.setFragmentResult(resultCode, data);
    }

    public void setFragmentBackResult(int resultCode) {
        setFragmentBackResult(resultCode, null);
    }

    public void onFragmentResult(int resultCode, Bundle data) {

    }

    public final void finishFragment() {
        mActivity.onBackPressed();
    }

    public final void finishFragmentWithResult() {
        setFragmentBackResult(Activity.RESULT_OK);
        mActivity.onBackPressed();
    }

    public final void finishFragmentWithResult(Bundle data) {
        setFragmentBackResult(Activity.RESULT_OK, data);
        mActivity.onBackPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (null == context || !(context instanceof SWBaseActivity)) {
            throw new RuntimeException("BaseFragment必须与BaseActivity配合使用");
        }
        mActivity = (SWBaseActivity) context;
        mFragmentManager = getChildFragmentManager();
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFragmentManager != null) {
            List<Fragment> fragments = mFragmentManager.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isAdded() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        }
    }


    public SWBaseFragment getCurrentFragment() {
        Fragment fragment = mFragmentManager.findFragmentById(R.id.base_activity_replace);
        if (fragment != null && fragment instanceof SWBaseFragment) {
            return (SWBaseFragment) fragment;
        }
        return null;
    }


    protected abstract int getLayoutId();


    public void showLodingView() {
        //throw new RuntimeException("请实现此方法，或者继承BaseLodingFragment");
    }

    public void showNoDataView() {
        //throw new RuntimeException("请实现此方法，或者继承BaseLodingFragment");
    }

    public void showSuccessView() {
        //throw new RuntimeException("请实现此方法，或者继承BaseLodingFragment");
    }

    public void showNoNetView() {
        //throw new RuntimeException("请实现此方法，或者继承BaseLodingFragment");
    }

    public void removeFragment(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            removeFragment(fragment);
        }
    }

    public void removeFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public final void replace(int contaninViewId, Fragment fragment) {
        if (null != fragment && null != mFragmentManager) {
            mFragmentManager
                    .beginTransaction()
                    .replace(contaninViewId, fragment)
                    .commitAllowingStateLoss();
        }
    }

    public void removeTopbanner() {
        if (null != mActivity) {
            mActivity.removeTopBanner();
        }
    }

    public void removeDivider() {
        if (null != divider) {
            divider.setVisibility(View.GONE);
        }
    }
}
