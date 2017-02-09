package com.demo.swt.mystudyappshop.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.GoodDraweeView.LockPatternView;
import com.demo.swt.mystudyappshop.MainActivity;
import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/9
 */

public class PassWordFragment extends Fragment implements LockPatternView.OnPatterChangeListener, View.OnClickListener {
    public static final String TYPE_SETTING = "setting";
    public static final String TYPE_CHECK = "check";
    public static final String ARG_YTPE = "type";
    private TextView lockText, password;
    private LockPatternView lockview;
    private String passwdrdstr;
    private Button commit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_password, container, false);
        lockText = (TextView) view.findViewById(R.id.locktext);
        lockview = (LockPatternView) view.findViewById(R.id.lockview);
        commit = (Button) view.findViewById(R.id.commit);
        password = (TextView) view.findViewById(R.id.password);
        if (getArguments() != null) {
            //设置密码类型的
            if (TYPE_SETTING.equals(getArguments().getString(ARG_YTPE))) {
                commit.setVisibility(View.VISIBLE);
            }
        }
        lockview.setOnPatterChangeListener(this);
        view.findViewById(R.id.commit).setOnClickListener(this);
        view.findViewById(R.id.getpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getString("password",""))) {
                    password.setText("你还没有设置密码");
                } else {
                    password.setText("你的密码是" + getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getString("password",""));

                }
            }
        });
        return view;
    }

    public static PassWordFragment newInstance(String type) {
        Bundle args = new Bundle();
        PassWordFragment fragment = new PassWordFragment();
        args.putString(ARG_YTPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void OnPatterChange(String passWorkstr) {
        this.passwdrdstr = passWorkstr;
        if (TextUtils.isEmpty(passWorkstr)) {
            lockText.setText("至少选择5个图案");

        } else {
            lockText.setText("您设置的密码是" + passWorkstr);
            if (getArguments() != null) {
                //密码检查
                if (TYPE_CHECK.equals(getArguments().getString(ARG_YTPE))) {
                    SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    if (passwdrdstr.equals(sp.getString("password", ""))) {
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    } else {
                        lockText.setText("密码错误");
                        lockview.resertPoint();
                    }
                }
            }
        }

    }

    @Override
    public void OnPatterStart(boolean isStart) {
        if (isStart) {
            lockText.setText("请绘制图案");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                sp.edit().putString("password", passwdrdstr).commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;
        }
    }
}
