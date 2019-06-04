package com.demo.swt.mystudyappshop.Wight;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：xjzhao
 * 时间：2015-11-26 下午2:39
 */
public class NumberUtils {

    /**
     * 获取转换后的显示：万、百万、千万和亿
     *
     * @param num
     * @return
     */
    public static String getFormatNumber(long num) {
        StringBuilder sbNum = new StringBuilder();
        String suffix = "";
        String plus = "";
        float n = 0;
        if (num >= 100000000) {
            n = num / 100000000f;
            suffix = "亿";
        } else if (num >= 10000000) {
            n = num / 10000000f;
            suffix = "千万";
        } else if (num >= 1000000) {
            n = num / 1000000f;
            suffix = "百万";
        } else if (num >= 10000) {
            n = num / 10000f;
            suffix = "万";
        } else {
            return num + suffix;
        }

        String strn = String.valueOf(n);
        if (!strn.endsWith("0")){
            plus += "+";
        }
        n = getNewNum(n);
        if (String.valueOf(n).endsWith(".0")){
            sbNum.append(String.valueOf(n).replace(".0", "")).append(suffix).append(plus);
        }else {
            sbNum.append(n).append(suffix).append(plus);
        }
        return sbNum.toString();
    }
    private static float getNewNum(float n) {
        return (float) (Math.round(n * 10)) / 10;
    }


    /**
     * 是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(!TextUtils.isEmpty(str)){
            Pattern pattern = Pattern.compile("[.0-9]*");
            Matcher isNum = pattern.matcher(str);
            if( !isNum.matches() ){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 是否整数
     * @param str
     * @return
     */
    public static boolean isInt(String str){
        if(!TextUtils.isEmpty(str)){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if( !isNum.matches() ){
                return false;
            }
            return true;
        }
        return false;
    }


    public static int getInt(String str){
        if (isInt(str)){
            long s = Long.valueOf(str);
            if (s < Integer.MAX_VALUE){
                return Integer.valueOf(str);
            }
        }
        return 0;
    }
}
