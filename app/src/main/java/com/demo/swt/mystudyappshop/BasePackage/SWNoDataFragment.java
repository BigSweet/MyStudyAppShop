package com.demo.swt.mystudyappshop.BasePackage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;


public class SWNoDataFragment extends SWBaseFragment{
    private TextView nodata;
    private TextView nodataBtn;
    View llRoot;
    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_nodata;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        nodata = findViewById(R.id.load_no_data);
        nodataBtn = findViewById(R.id.load_no_data_btn);
        NoNullUtils.setOnClickListener(findViewById(R.id.llRoot), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setNodataText(String s){
        NoNullUtils.setText(nodata, s);
    }

    public void setNodataBtn(String s, View.OnClickListener l){
        if (!TextUtils.isEmpty(s)){
            NoNullUtils.setVisible(nodataBtn, true);
            NoNullUtils.setOnClickListener(nodataBtn, l);
        }else {
            NoNullUtils.setVisible(nodataBtn, false);
        }
    }
}
