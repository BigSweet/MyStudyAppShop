package com.demo.swt.mystudyappshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.swt.mystudyappshop.Activity.ChatJiqiActivity;
import com.demo.swt.mystudyappshop.Activity.NineLockActivity;
import com.demo.swt.mystudyappshop.Activity.QrCodeActivity;
import com.demo.swt.mystudyappshop.Activity.RecordDemoActivity;
import com.demo.swt.mystudyappshop.Activity.RgbaActivity;
import com.demo.swt.mystudyappshop.Activity.SqliteDataBaseActivity;
import com.demo.swt.mystudyappshop.Activity.WebChatActivity;
import com.demo.swt.mystudyappshop.Activity.ZhiMaLeiDaActivity;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.SelectCityActivity.SelectCityActivity;
import com.demo.swt.mystudyappshop.Wight.SwtToast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by pc on 2016/11/29.
 */

public class CateGoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category, container, false);

        view.findViewById(R.id.select_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.zhima).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZhiMaLeiDaActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.rgbabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RgbaActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.qrcodebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.webchatbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebChatActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.recorddemobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordDemoActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.ninebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NineLockActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.smsservice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            SwtToast.show("地区" + country + "手机号" + phone + "的用户验证成功");
                        }
                    }
                });
                registerPage.show(getActivity());
            }
        });
        view.findViewById(R.id.aqlitebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SqliteDataBaseActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.chatjiqiren).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatJiqiActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
