package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;

/**
 * Created by pc on 2016/11/29.
 */

public class SwtToast {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    private static Toast mCstToast;

    /**
     * 初始化CstToast
     */
    private static boolean initCstToast() {
        if (null == mCstToast) {
            mCstToast = new Toast(mContext);
        }
        return true;
    }


    /**
     * 只显示一排文字，这个方法中所有内容都不能传递为null
     *
     * @param toastTextStr
     */
    public synchronized static void show(final String toastTextStr) {
        if (initCstToast()) {
            LayoutInflater inflate = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.toast, null);
            TextView tv = (TextView) v.findViewById(R.id.message);
            tv.setText(toastTextStr);
            mCstToast.setView(v);
            mCstToast.setDuration(Toast.LENGTH_SHORT);
            mCstToast.setGravity(Gravity.CENTER, 0, 0);
            mCstToast.show();
        }
    }

    public synchronized static void show(final String toastTextStr, int time) {
        try {
            if (initCstToast()) {
                mCstToast.setDuration(time);
                mCstToast.setText(toastTextStr);
                mCstToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
