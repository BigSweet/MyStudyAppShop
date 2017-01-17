package com.demo.swt.mystudyappshop.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/17
 */

public class RgbaActivity extends FragmentActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rgba);
        imageView = (ImageView)findViewById(R.id.image111);
        findViewById(R.id.alpbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setAlpha(0.5f);
            }
        });
        findViewById(R.id.noalpbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setAlpha(1f);
            }
        });
    }
}
