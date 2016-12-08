package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.MainActivity;
import com.demo.swt.mystudyappshop.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by pc on 2016/12/8.
 */

public class LanchActivity extends FragmentActivity {
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lauch);

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAuth();//第三方登陆
            }
        });
    }


    private void requestAuth() {
        UMShareAPI mShareAPI = UMShareAPI.get(this);
        mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
       // mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);//听说是拿用户信息的，但是不知道怎么拿哦

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LanchActivity.this, MainActivity.class);
            startActivity(intent);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}
