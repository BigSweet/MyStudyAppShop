package com.demo.swt.mystudyappshop.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.BasePackage.SWBaseFragment;
import com.demo.swt.mystudyappshop.MainActivity;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.PatternHelper;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.List;

/**
 * introduce：这里写介绍
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 25/7/18
 */
public class WelcomeFragment extends SWBaseFragment {

    private PatternLockerView patternLockerView;
    private PatternIndicatorView patternIndicatorView;
    private TextView textMsg;
    private PatternHelper patternHelper;
    private boolean settting = true;

    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        removeTopbanner();
        String per[] ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(per,1);
        patternHelper = new PatternHelper();
        this.patternIndicatorView = findViewById(R.id.pattern_indicator_view);
        this.patternLockerView = findViewById(R.id.pattern_lock_view);
        this.textMsg = findViewById(R.id.text_msg);
        String password = patternHelper.getFromStorage();
        //没有设置密码
        if (TextUtils.isEmpty(password)) {
            settting = true;
            this.textMsg.setText("请设置密码");
        } else {
            settting = false;
            this.textMsg.setText("请输入密码");
        }
        this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isOk;
                if (settting) {
                    isOk = !isPatternSetting(hitList);
                } else {
                    isOk = !isPatternOk(hitList);
                }
                view.updateStatus(isOk);
                patternIndicatorView.updateState(hitList, !isOk);
                updateMsg();

            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });

    }

    private boolean isPatternSetting(List<Integer> hitList) {
        this.patternHelper.validateForSetting(hitList);
        return this.patternHelper.isOk();
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ?
                getResources().getColor(R.color.colorPrimary) :
                getResources().getColor(R.color.colorAccent));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish()) {
            if (this.patternHelper.isOk()) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
            getActivity().finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity;
    }
}
