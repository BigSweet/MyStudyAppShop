package com.demo.swt.mystudyappshop.Util;

import android.text.TextUtils;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.MyApplication;
import com.squareup.picasso.Picasso;

/**
 * 介绍：
 * 作者：xjzhao
 * 邮箱：mr.feeling.heart@gmail.com
 * 时间: 2016-09-29  下午3:56
 */

public class LoadUtils {

    /**
     * 加载图片
     * @param img
     * @param url

     */
    public static void loadImage(final ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                if (LoadImgUtils.isService(url)){
                    loadServiceImg(img, url);
                }else {
                    loadLocalImg(img, url);
                }
            }
        }
    }

    //加载本地图片
    private static void loadLocalImg(final ImageView img, String url){
        int[] params = new int[2];
        BitmapFileUtil.getLocalHeightAndWith(url, params);
        if (isNeedScale(params[0], params[1])){
            loadImg(img, url, true, params[0], params[1]);
        }else {
            loadImg(img, url, false, params[0], params[1]);
        }
    }


    public  static  void loadSingleImg(ImageView img, String url){
        loadImg(img, url, false, 0, 0);
    }
    //加载服务端图片
    private static  void loadServiceImg(ImageView img, String url){
        int[] params = LoadImgUtils.getParams(url);
        if (null != params && params.length == 2){
            if (isNeedScale(params[0], params[1])){
                loadImg(img, LoadImgUtils.getUrl(url, LoadImgUtils.TYPE_BIG), true, params[0], params[1]);
            }else {
                loadImg(img, LoadImgUtils.getUrl(url, LoadImgUtils.TYPE_BIG), false, params[0], params[1]);
            }
        }else {
            loadImg(img, url, false, 0, 0);
//            loadImg(img, LoadImgUtils.getUrl(url, LoadImgUtils.TYPE_BIG), false, 0, 0);
        }
    }

    private static  void loadImg(final ImageView img, String url, boolean isNeedScale, final int w, final int h){
        if (isNeedScale){
        /*    LoadImgUtils.getIonInstance().build(img)
                    .deepZoom()
                    .load(url);*/
            Picasso.with(MyApplication.getmContext()).load(url).into(img);
        }else {
//            img.setMaxScale(ImageViewAttacher.DEFAULT_MAX_SCALE);
//            img.setMinScale(ImageViewAttacher.DEFAULT_MIN_SCALE);
          /*  LoadImgUtils.getIonInstance().build(img)
                    .resizeWidth(w > Constant.SCREEN_WIDTH ? Constant.SCREEN_WIDTH:w)
                    .load(url);*/
            Picasso.with(MyApplication.getmContext()).load(url).into(img);
        }
    }

    /**
     * 超长图自动放大
     * @param img
     * @param w
     * @param h
     */
    private static  void scaleImg(final ImageView img, int w, int h){
        if (null != img){
            float scale = Constant.SCREEN_WIDTH * 1f / ((Constant.SCREEN_HEIGHT - Constant.STATUSBAR_HEIGHT) * 1f / h * w);
//            img.setMaxScale(scale * 2);
//            img.setMinScale(scale);
//            img.zoomTo(scale, Constant.SCREEN_WIDTH / 2, 0);
        }
    }

    /**
     * 是否为长图,缩自动放大
     * @param w
     * @param h
     * @return
     */
    private static boolean isNeedScale(int w, int h){
        if (0 != w && 0 != h){
            int scaleW = (int) (Constant.SCREEN_HEIGHT * 1f / h * w);
            int smallW = Constant.SCREEN_WIDTH / 3;
            int switchH = (int) ((Constant.SCREEN_HEIGHT - Constant.STATUSBAR_HEIGHT) * 1.5f);
            if ((h > switchH && scaleW < smallW)                                 //图片实际高度大于屏幕高度的1.5倍且按缩放一屏后的实际宽度小于屏幕宽度三分之一
                    || (h > (Constant.SCREEN_HEIGHT - Constant.STATUSBAR_HEIGHT) && h <= switchH && w < smallW)){   //图片实际高度大于屏幕高度,小于屏幕高度的1.5倍,实际宽度小于屏幕宽度的三分之一
                return true;
            }
        }
        return false;
    }
}
