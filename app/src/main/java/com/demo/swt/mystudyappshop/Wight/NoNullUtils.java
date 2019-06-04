package com.demo.swt.mystudyappshop.Wight;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by pc on 2016/11/29.
 */

public class NoNullUtils {


    /**
     * 简单设置默认文字
     *
     * @param t
     * @param s
     */
    public static void setHintText(TextView t, String s) {
        if (null != t) {
            if (null == s) {
                s = "";
            }
            t.setHint(s);
        }
    }

    /**
     * 简单设置文字
     *
     * @param t
     * @param s
     */
    public static void setText(TextView t, String s) {
        if (null != t) {
            if (null == s) {
                s = "";
            }
            t.setText(toDBC(s.trim()));
        }
    }

    public static void setText(TextView t, CharSequence s) {
        if (null != t) {
            if (null == s) {
                s = "";
            }
            t.setText(toDBC(s.toString().trim()));
        }
    }

    public static void setAlpha(View v, float alpha) {
        if (null != v) {
            if (alpha < 0) alpha = 0;
            if (alpha > 1) alpha = 1;
            v.setAlpha(alpha);
        }
    }

    /**
     * 简单设置文字
     *
     * @param t
     * @param s
     */
    public static void setTextSpannableString(TextView t, SpannableString s) {
        if (null != t) {
            if (null == s) {
                s = new SpannableString("");
            }
            t.setText(s);
        }
    }



    /**
     * 设置背景
     *
     * @param v
     * @param res
     */
    public static void setBackground(View v, int res) {
        if (null != v) {
            v.setBackgroundResource(res);
        }
    }

    /**
     * 设置背景
     *
     * @param v
     * @param res
     */
    public static void setBackgroundDrawable(Context context, View v, int res) {
        if (null != context && null != v) {
            if (res > 0) {
                v.setBackgroundDrawable(context.getResources().getDrawable(res));
            } else {
                v.setBackgroundDrawable(null);
            }
        }
    }

    public static void setBackgroundColor(View v, int res) {
        if (null != v) {
            v.setBackgroundColor(res);
        }
    }

    /**
     * 设置颜色
     *
     * @param t
     * @param color
     */
    public static void setTextColor(TextView t, int color) {
        if (null != t) {
            t.setTextColor(color);
        }
    }

    /**
     * 设置颜色
     *
     * @param t
     * @param color
     */
    public static void setHintTextColor(TextView t, int color) {
        if (null != t) {
            t.setHintTextColor(color);
        }
    }

    /**
     * 设置颜色
     *
     * @param t
     * @param color
     */
    protected void setTextColorById(Context context, TextView t, int color) {
        if (null != t && null != context) {
            t.setTextColor(context.getResources().getColor(color));
        }
    }

    /**
     * 设置字体大小
     *
     * @param t
     * @param sp
     */
    public static void setTextSize(TextView t, int sp) {
        if (null != t && sp > 0) {
            t.setTextSize(sp, TypedValue.COMPLEX_UNIT_SP);
        }
    }

    /**
     * 设置imageres
     *
     * @param img
     * @param res
     */
    public static void setImageResource(ImageView img, Integer res) {
        if (null != img && null != res) {
            img.setImageResource(res);
        }
    }

    /**
     * 设置点击事件
     *
     * @param v
     * @param l
     */
    public static void setOnClickListener(View v, View.OnClickListener l) {
        if (null != v) {
            v.setOnClickListener(l);
        }
    }


    /**
     * 设置touch事件
     *
     * @param v
     * @param l
     */
    public static void setOnTouchListener(View v, View.OnTouchListener l) {
        if (null != v) {
            v.setOnTouchListener(l);
        }
    }

    /**
     * 设置长按事件
     *
     * @param v
     * @param l
     */
    public static void setOnLongClickListener(View v, View.OnLongClickListener l) {
        if (null != v) {
            v.setOnLongClickListener(l);
        }
    }

    /**
     * 设置padding
     *
     * @param v
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPadding(View v, int left, int top, int right, int bottom) {
        if (null != v) {
            v.setPadding(left, top, right, bottom);
        }
    }


    /**
     * 将所有文字转半角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        if (null != input) {
            char[] c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
            return new String(c);
        }
        return "";
    }

    /**
     * 设置TextView的Drawable
     *
     * @param context
     * @param t
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setTextViewDrawable(Context context, TextView t, int left, int top, int right, int bottom) {
        setTextViewDrawableLeft(context, t, left);
        setTextViewDrawableTop(context, t, top);
        setTextViewDrawableRight(context, t, right);
        setTextViewDrawableBottom(context, t, bottom);
    }

    /**
     * 设置TextView的左图
     *
     * @param context
     * @param t
     * @param redId
     */
    public static void setTextViewDrawableLeft(Context context, TextView t, int redId) {
        if (null != t && null != context) {
            Drawable drawable = context.getResources().getDrawable(redId);
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                t.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    /**
     * 设置TextView的右图
     *
     * @param context
     * @param t
     * @param redId
     */
    public static void setTextViewDrawableRight(Context context, TextView t, int redId) {
        if (null != t && null != context) {
            Drawable drawable = context.getResources().getDrawable(redId);
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                t.setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    /**
     * 设置TextView的右图
     *
     * @param context
     * @param t
     * @param redId
     */
    public static void setTextViewDrawableTop(Context context, TextView t, int redId) {
        if (null != t && null != context) {
            Drawable drawable = context.getResources().getDrawable(redId);
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                t.setCompoundDrawables(null, drawable, null, null);
            }
        }
    }

    /**
     * 设置TextView的下图
     *
     * @param context
     * @param t
     * @param redId
     */
    public static void setTextViewDrawableBottom(Context context, TextView t, int redId) {
        if (null != t && null != context) {
            Drawable drawable = context.getResources().getDrawable(redId);
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                t.setCompoundDrawables(null, null, null, drawable);
            }
        }
    }

    /**
     * 清除TextView的Drawable
     *
     * @param t
     */
    public static void removeTextViewDrawable(TextView t) {
        if (null != t) {
            t.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * 设置View可见性
     *
     * @param v
     * @param isVisible
     */
    public static void setVisible(View v, boolean isVisible) {
        if (null != v) {
            if (isVisible) {
                if (v.getVisibility() != View.VISIBLE) {
                    v.setVisibility(View.VISIBLE);
                }
            } else {
                if (v.getVisibility() != View.GONE) {
                    v.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 设置View可见性Invisible
     *
     * @param v
     */
    public static void setInVisible(View v) {
        if (null != v) {
            if (v.getVisibility() != View.INVISIBLE) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }


    public static boolean isEmpty(String s) {
        if (null != s) {
            return TextUtils.isEmpty(s.trim());
        } else {
            return true;
        }
    }

    /**
     * 设置View可见性
     *
     * @param v
     * @param value
     */
    public static void setVisible(View v, int value) {
        if (null != v) {
            v.setVisibility(value);
        }
    }

    /**
     * 是否可见
     *
     * @param v
     * @return
     */
    public static boolean isVisible(View v) {
        return null != v && v.getVisibility() == View.VISIBLE;
    }

    /**
     * 获取内容
     *
     * @param t
     * @return
     */
    public static String getText(TextView t) {
        CharSequence cs;
        if (null != t && null != (cs = t.getText())) {

            return String.valueOf(cs);
        }
        return null;
    }

    /**
     * 设置高度
     *
     * @param v
     * @param height
     */
    public static void setHeightByLp(View v, int height) {
        ViewGroup.LayoutParams lp;
        if (null != v && null != (lp = v.getLayoutParams())) {
            lp.height = height;
        }
    }

    /**
     * addTextChangedListener
     *
     * @param t
     * @param textWatcher
     */
    public static void addTextChangedListener(EditText t, TextWatcher textWatcher) {
        if (null != t) {
            t.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 设置动画
     *
     * @param v
     * @param anim
     */
    public static void startAnimation(View v, Animation anim) {
        if (null != v && null != anim) {
            v.clearAnimation();
            v.startAnimation(anim);
        }
    }

    public static Object getTag(View v) {
        if (null != v) {
            return v.getTag();
        }
        return null;
    }

    public static void setTag(View v, Object o) {
        if (null != v) {
            v.setTag(o);
        }
    }

    public static void showDialog(Dialog dialog) {
        if (null != dialog) {
            dialog.show();
        }
    }

    public static boolean isEqule(String s1, String s2) {
        if (null == s1) {
            return s2 == null;
        } else {
            return s1.equals(s2);
        }

    }

    public static void dismissDialog(Dialog dialog) {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public static void setChecked(CheckedTextView t, boolean checked) {
        if (null != t) {
            t.setChecked(checked);
        }
    }

    public static boolean isChecked(CheckedTextView t) {
        if (null != t) {
            return t.isChecked();
        }
        return false;
    }

    public static int getLength(String s) {
        return s != null ? s.length() : 0;
    }

    public static boolean isEmptyOrNull(List<?> list) {
        return null == list || list.isEmpty();
    }

    public static boolean isImg(String url) {
        String[] arr;
        String suffix;
        if (!TextUtils.isEmpty(url) && null != (arr = url.split("\\.")) && arr.length == 2 && null != (suffix = arr[1])) {
            suffix = suffix.toLowerCase();
            if (suffix.endsWith("jpg") || suffix.endsWith("jpeg") || suffix.endsWith("png") || suffix.endsWith("bmp")) {
                return true;
            }
        }
        return false;
    }

    public static String getImg(String url){
        int len;
        String lastSuffix;
        String[] arr;
        if (!TextUtils.isEmpty(url) && null != (arr = url.split("\\.")) && (len = arr.length) > 0 && null != (lastSuffix = arr[len - 1]) && (!lastSuffix.endsWith("jpg") && !lastSuffix.endsWith("jpeg") && !lastSuffix.endsWith("png") && !lastSuffix.endsWith("bmp"))) {
            return url + ".jpg";
        }
        return url;
    }

    /**
     * 获取或失去焦点
     *
     * @param f
     */
    public static void setFocusable(EditText t, boolean f) {
        if (null != t) {
            t.setFocusable(f);
            t.setFocusableInTouchMode(f);
            if (f) {
                t.requestFocus();
                String text;
                if (!TextUtils.isEmpty(text = NoNullUtils.getText(t))) {
                    t.setSelection(text.length());
                }
            } else {
                t.clearFocus();
            }
        }
    }



    private static int getWidth(Drawable drawable, int h) {
        if (null != drawable && h > 0) {
            int realH = drawable.getIntrinsicHeight();
            int realW = drawable.getIntrinsicWidth();
            if (realH > 0) {
                return realW * h / realH;
            }
        }
        return 0;
    }


    public static String getString(String s) {
        if (null == s) {
            return "";
        }
        return s;
    }

    /**
     * 获得当前进程名称
     *
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static boolean isFocused(EditText title) {
        if (null != title) {
            return title.isFocused();
        }
        return false;
    }
}
