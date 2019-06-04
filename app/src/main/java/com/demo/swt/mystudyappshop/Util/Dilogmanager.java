package com.demo.swt.mystudyappshop.Util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/19
 */

public class Dilogmanager {
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private Context mContext;
    private TextView label;
    private View view;


    public Dilogmanager(Context context) {
        this.mContext = context;

    }


    public void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.ThemeRecordingDialog);
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_manager, null);
        mDialog.setContentView(view);
        mIcon = (ImageView) mDialog.findViewById(R.id.record_dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.record_dialog_voice);
        label = (TextView) mDialog.findViewById(R.id.record_dialog_label);
        mDialog.show();
    }


    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.recorder);
            label.setText("手指上滑 取消发送");
        }
    }


    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            label.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.cancel);
            label.setText("松开手指 取消发送");
        }
    }

    public void toShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            label.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.voice_to_short);
            label.setText("录音时间过短");
        }
    }

    public void dissMissDialog() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void updateVoiceLevel(int level) {

        if (mDialog != null && mDialog.isShowing()) {
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            label.setVisibility(View.VISIBLE);

            int resId = mContext.getResources().getIdentifier("v" + level, "mipmap", mContext.getPackageName());
            mVoice.setImageResource(resId);
        }

    }
}
