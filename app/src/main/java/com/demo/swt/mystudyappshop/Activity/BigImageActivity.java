package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    private SimpleDraweeView simpleDraweeView;
    private String morentu = "http://pic.anlaiye.com.cn/1c978a81759543cb8f1c0fed0a4b2416_800x1066.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigimagee);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.bigimage);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String uri = bundle.getString("tu", morentu);
        simpleDraweeView.setImageURI(uri);
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
