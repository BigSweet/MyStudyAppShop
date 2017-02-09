package com.demo.swt.mystudyappshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.swt.mystudyappshop.Activity.NineLockActivity;
import com.demo.swt.mystudyappshop.Activity.QrCodeActivity;
import com.demo.swt.mystudyappshop.Activity.RecordDemoActivity;
import com.demo.swt.mystudyappshop.Activity.RgbaActivity;
import com.demo.swt.mystudyappshop.Activity.WebChatActivity;
import com.demo.swt.mystudyappshop.Activity.ZhiMaLeiDaActivity;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.SelectCityActivity.SelectCityActivity;

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

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
