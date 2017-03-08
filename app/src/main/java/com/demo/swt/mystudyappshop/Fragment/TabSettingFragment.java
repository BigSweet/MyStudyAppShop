package com.demo.swt.mystudyappshop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by pc on 2016/11/29.
 */

public class TabSettingFragment extends Fragment implements View.OnClickListener {

    public static TabSettingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TabSettingFragment fragment = new TabSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private Button qqlogin;
    private Button weixinlogin;
    private UMShareAPI mShareAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lauch, container, false);
        qqlogin = (Button) view.findViewById(R.id.qqlogin);
        weixinlogin = (Button) view.findViewById(R.id.weixinlogin);
        mShareAPI = UMShareAPI.get(getActivity());

        qqlogin.setOnClickListener(this);
        weixinlogin.setOnClickListener(this);
        return view;
    }


    private void requestAuthQQ() {
        UMShareAPI mShareAPI = UMShareAPI.get(getActivity());
        mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
        // mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);//听说是拿用户信息的，但是不知道怎么拿哦

    }

    private void requestAuthWeiXin() {
        mShareAPI.doOauthVerify(getActivity(), SHARE_MEDIA.WEIXIN, umAuthListener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "登陆成功，写入跳转代码", Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(LanchActivity.this, MainActivity.class);
            startActivity(intent);*/

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getActivity(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getActivity(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qqlogin:
                requestAuthQQ();
                break;
            case R.id.weixinlogin:
                requestAuthWeiXin();
                break;
        }
    }
}
