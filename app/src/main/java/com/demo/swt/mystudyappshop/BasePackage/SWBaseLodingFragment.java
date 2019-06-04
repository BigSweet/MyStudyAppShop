package com.demo.swt.mystudyappshop.BasePackage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.demo.swt.mystudyappshop.R;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/12
 */
public abstract class SWBaseLodingFragment extends SWBaseFragment {

    private static final String ERROR_FRAGMENT = "LoadingBaseFragmentError";
    protected FrameLayout scuccessRootView;
    private State state = State.Loading;


    private ViewGroup lodingLayout;
    private ProgressBar appProgressBar;

    private ViewGroup errorLayout;
    private SWNoDataFragment noDataFragment;

    private enum State {
        Nomoral,
        Loading,
        NoData,
        Exception
    }

    /**
     * @return 子类如果想直接返回View 重写 返回false,并重写 getSuccessLayoutView()方法
     */
    protected boolean inflateSuccessViewById() {
        return true;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        lodingLayout = findViewById(R.id.logingLayout);
        appProgressBar = findViewById(R.id.app_progress);
        errorLayout = findViewById(R.id.empty_container);

        scuccessRootView = findViewById(R.id.success_container);
        if (inflateSuccessViewById()) {
            LayoutInflater.from(getActivity()).inflate(getSuccessLayoutId(), scuccessRootView, true);
        } else {
            scuccessRootView.addView(getSuccessLayoutByView(scuccessRootView));
        }
        //initSuccessView(savedInstanceState);
        if (firstShowLoding()) {
            showLodingView();
        } else {
            showSuccessView();
        }
        initSuccessView(savedInstanceState);

        onSuccessViewCreated();

    }

    protected boolean firstShowLoding() {
        return true;
    }

    /**
     * 展示正确界面 ，同时根据缓存需要 处理loging  remove错误
     */
    @Override
    public final void showSuccessView() {

        state = State.Nomoral;
        stopLoding();
        removeFragment(ERROR_FRAGMENT);
        setFragmentVisable(scuccessRootView, true);
        setFragmentVisable(lodingLayout, false);
        setFragmentVisable(errorLayout, false);

    }
    @Override
    public final void showLodingView() {
        state = State.Loading;

        startLoding();
        removeFragment(ERROR_FRAGMENT);
        setFragmentVisable(scuccessRootView, false);
        setFragmentVisable(errorLayout, false);

    }

    @Override
    public final void showNoDataView() {
        state = State.NoData;
        noDataFragment = (SWNoDataFragment) getNoDataFragment();
        showError(noDataFragment);

    }
    protected Handler mHandler = new Handler();

    private void showError(final Fragment fragment) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragmentVisable(errorLayout, true);
                setFragmentVisable(scuccessRootView, false);
                stopLoding();
                if (!mFragmentManager.isDestroyed()) {
                    replace(R.id.empty_container, fragment);
                }
            }
        }, 200);

    }

    protected Fragment getNoDataFragment() {
        return new SWNoDataFragment();
    }
    private void startLoding() {
        setFragmentVisable(lodingLayout, true);
      /*  if (!appProgressBar.isLoading()) {
            appProgressBar.startAnimation();
        }*/
    }

    private void stopLoding() {
        setFragmentVisable(lodingLayout, false);
      /*  if (null != appProgressBar) {//add by zhangxutong  空指针
            appProgressBar.stopAnimation();
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopLoding();
    }

    private void setFragmentVisable(View v, boolean visible) {
        if (v == null) {
            return;
        }
        if (visible) {
            if (v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        } else {
            if (v.getVisibility() != View.INVISIBLE) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }
    protected abstract void initSuccessView(Bundle savedInstanceState);
    protected void onSuccessViewCreated() {
    }
    protected abstract int getSuccessLayoutId();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_loding;
    }

    protected View getSuccessLayoutByView(FrameLayout scuccessRootView) {
        return new View(mActivity);
    }
}
