package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 场景                    大图模式参数             大图模式style            小图模式参数                 小图模式style                type
 * <p/>
 * 大图阅览                  原尺寸                    /                    原尺寸(质量压缩50%)         @!compress                     1
 * 头像                     180*180                @!avatar_norm           90*90                    @!avatar_compress              2
 * 轮播                     750*250                @!relay_norm            325*125                  @!relay_compress               3
 * 缩略(1张)                 750*500               @!breviary_1_norm       325*250                  @!breviary_1_compress          4
 * 缩略(2张)                 400*400               @!breviary_2_norm       200*200                  @!breviary_2_compress          5
 * 缩略(4张)                 400*270               @!breviary_4_norm       200*135                  @!breviary_4_compress          6
 * 缩略(3/5/6/7/9张)         300*300               @!breviary_multi_norm   150*150                  @!breviary_multi_compress      7
 * IM                       原尺寸(质量压缩80%)      @!im_norm               原尺寸(质量压缩50%)        @!compress                     8
 * <p/>
 * <p/>
 * 加载图片工具类
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public class LoadImgUtils {
    public final static int TYPE_BIG = 1;     //大图
    public final static int TYPE_AVATAR = 2;  //头像
    public final static int TYPE_RELAY = 3;   //轮播
    public final static int TYPE_B_1 = 4;     //缩略图一张
    public final static int TYPE_B_2 = 5;     //缩略图两张
    public final static int TYPE_B_4 = 6;     //缩略图四张
    public final static int TYPE_B_MULTI = 7; //缩略图多张
    public final static int TYPE_IM = 8;      //IM

    //大图
    private final static String STYLE_NORMAL = "";
    private final static String STYLE_COMPRESS = "@!compress";

    //头像
    private final static String STYLE_AVATAR_NORMAL = "@!avatar_norm";
    private final static String STYLE_AVATAR_COMPRESS = "@!avatar_compress";

    //轮播
    private final static String STYLE_RELAY_NORMAL = "@!relay_norm";
    private final static String STYLE_RELAY_COMPRESS = "@!relay_compress";

    //缩略图一张
    private final static String STYLE_B_1_NORMAL = "@!breviary_1_norm";
    private final static String STYLE_B_1_COMPRESS = "@!breviary_1_compress";

    //缩略图两张
    private final static String STYLE_B_2_NORMAL = "@!breviary_2_norm";
    private final static String STYLE_B_2_COMPRESS = "@!breviary_2_compress";

    //缩略图四张
    private final static String STYLE_B_4_NORMAL = "@!breviary_4_norm";
    private final static String STYLE_B_4_COMPRESS = "@!breviary_4_compress";

    //缩略图多张
    private final static String STYLE_B_MULTI_NORMAL = "@!breviary_multi_norm";
    private final static String STYLE_B_MULTI_COMPRESS = "@!breviary_multi_compress";

    //IM
    private final static String STYLE_IM_NORMAL = "@!im_norm";
    private final static String STYLE_IM_COMPRESS = "@!compress";


    private static Context context;
    private static List<Integer> avatars = new ArrayList<>();
    private static int avatarsSize;

    private static Ion ionInstance;

 /*   static {
        avatars.add(R.drawable.avatar_01);
        avatars.add(R.drawable.avatar_02);
        avatars.add(R.drawable.avatar_03);
        avatars.add(R.drawable.avatar_04);
        avatarsSize = avatars.size();
    }*/

    public static void setContext(Context Context) {
        LoadImgUtils.context = Context;
        Fresco.initialize(context);
    }

    public static Ion getIonInstance() {
        if (null == ionInstance){
            ionInstance = Ion.getInstance(context, "img");
            if (android.os.Build.VERSION.SDK_INT < 21) {
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};
                SSLContext sc = null;
                try {
                    sc = SSLContext.getInstance("TLS");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    sc.setDefault(sc);

                } catch (Exception e) {
                }
                ionInstance.getHttpClient().getSSLSocketMiddleware().setTrustManagers(trustAllCerts);
                ionInstance.getHttpClient().getSSLSocketMiddleware().setSSLContext(sc);

                ionInstance.getHttpClient().getSSLSocketMiddleware().setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            }
        }
        return ionInstance;
    }

    /**
     * 是否服务器上的图片
     *
     * @param url
     * @return
     */
    public static boolean isService(String url) {
        return null != url && (url.startsWith("http://") || url.startsWith("https://"));
    }

    /**
     * 加载大图(图片阅读)
     *
     * @param img
     * @param url
     */
    public static void loadImgBig(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_BIG));
        } else {
            loadImageWithThumb(img, url);
        }
    }





    /**
     * 加载轮播图
     *
     * @param img
     * @param url
     */
    public static void loadImgRelay(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_RELAY));
        } else {
            loadImageWithThumb(img, url);
        }
    }

    /**
     * 加载缩略图，即列表上的图（一张）
     *
     * @param img
     * @param url
     */
    public static void loadImgbreviary1(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_B_1));
        } else {
            loadImageWithThumb(img, url);
        }
    }

    /**
     * 加载缩略图，即列表上的图（两张）
     *
     * @param img
     * @param url
     */
    public static void loadImgbreviary2(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_B_2));
        } else {
            loadImageWithThumb(img, url);
        }
    }

    /**
     * 加载缩略图，即列表上的图（四张）
     *
     * @param img
     * @param url
     */
    public static void loadImgbreviary4(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_B_4));
        } else {
            loadImageWithThumb(img, url);
        }
    }

    /**
     * 加载缩略图，即列表上的图（3/5/6/7/8/9张）
     *
     * @param img
     * @param url
     */
    public static void loadImgbreviaryMulti(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_B_MULTI));
        } else {
            loadImageWithThumb(img, url);
        }
    }

    public static void loadImgbreviaryMulti(ImageView img, int defaultRid, String url) {
        loadImage(img, defaultRid, getUrl(url, TYPE_B_MULTI));
    }

    /**
     * 加载IM聊天窗口内的图
     *
     * @param img
     * @param url
     */
    public static void loadImgIm(ImageView img, String url) {
        if (isService(url)) {
            loadImageWithThumb(img, getUrl(url, TYPE_IM));
        } else {
            loadImageWithThumb(img, url);
        }
    }




    /**
     *scaleType 为fitCenter加载图像
     *
     * @param img
     * @param url
     */
    public static void loadImageFitCenter(ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    public static void loadImageFitCenter(ImageView img, String url, int defaultIcon) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .fitCenter()
                        .placeholder(defaultIcon)
                        .error(defaultIcon)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(defaultIcon);
            }
        }

    }

    /**
     * 加载本地图片
     *
     * @param img
     * @param url
     */
    //注解add by zhangxutong  for DataBinding load img by net
    public static void loadImage(ImageView img, String url) {//统一调用缩略图
        loadImgbreviaryMulti(img, url);//加载缩略图
    }

    public static void loadImageWithThumb(ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    public static void loadImageWithResize(ImageView img, String url, int h, int w) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .resize(h, w)
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }



    /**
     * 按照fitxy 来加载图片
     *
     * @param img
     * @param url
     */
    public static void loadImageAsFitXY(ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .fitXY()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }


    /**
     * 按照fitxy 来加载图片
     *
     * @param img
     * @param url
     */
    public static void loadImagWithFitXYBigmp(final ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(context)
                        .load(url)
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                if (null != result) {
                                    img.setImageBitmap(result);
                                } else {
                                    img.setImageResource(R.drawable.ic_launcher);
                                }
                            }
                        });
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 按照fitxy 来加载图片
     *
     * @param img
     * @param url
     */
    public static void loadImageAsCenterInside(ImageView img, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerInside()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 传入加载或者占位资源
     *
     * @param img
     * @param defaultRid
     * @param url
     */
    public static void loadImage(ImageView img, int defaultRid, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .error(defaultRid)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(defaultRid);
            }
        }
    }


    /**
     * 加载本地图片 加上尺寸限制
     *
     * @param img
     * @param url
     */
    public static void loadImageByType(ImageView img, String url, int type) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                url = getUrlByType(url, type);
                getIonInstance().build(img)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }


    /**
     * 传入加载或者占位资源 加上尺寸限制
     *
     * @param img
     * @param defaultRid
     * @param url
     */
    public static void loadImageByType(ImageView img, int defaultRid, String url, int type) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                url = getUrlByType(url, type);
                getIonInstance().build(img)
                        .centerCrop()
                        .placeholder(defaultRid)
                        .error(defaultRid)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(defaultRid);
            }
        }
    }



    /**
     * 1、用户头像：@!style174_174
     * 2、帖子列表：一张图（@!style450_800）;多张（@!style240_240）
     * 3、商品列表：@!style400_400
     * 4、俺来卖列表：@!style300_400
     */
    public static final int TYPE_174_174 = 1;
    public static final int TYPE_240_240 = 2;
    public static final int TYPE_400_400 = 3;
    public static final int TYPE_300_400 = 4;
    public static final int TYPE_450_800 = 5;
    public static final int TYPE_NORMAL = 6;

    /**
     * 根据type在url后面拼接尺寸
     *
     * @param url
     * @param type
     * @return
     */
    private static String getUrlByType(String url, int type) {
        //如果是res开头，则啥都不干，（由于后台无能 我只能写下这个if） 稍后应该删除
        if (url.startsWith("http://res.")) {
            return url;
        }
        if (!url.contains("pic")) {
            return url;
        }
        switch (type) {
            case TYPE_174_174:
                url = url + "@!style174_174";
                break;
            case TYPE_240_240:
                url = url + "@!style240_240";
                break;
            case TYPE_400_400:
                url = url + "@!style400_400";
                break;
            case TYPE_300_400:
                url = url + "@!style300_400";
                break;
            case TYPE_450_800:
                url = url + "@!style450_800";
                break;
            case TYPE_NORMAL:
            default:
                break;
        }
        return url;
    }


    /**
     * 加载本地大图加载
     *
     * @param img
     * @param url
     */
    public static void loadImageDeepZoom(ImageView img, final String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .deepZoom()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 加载本地大图加载
     *
     * @param img
     * @param url
     */
    public static void loadImageDeepZoom(ImageView img, int defaultRid, String url) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .deepZoom()
                        .placeholder(defaultRid)
                        .error(defaultRid)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(defaultRid);
            }
        }
    }


    /**
     * 加载本地图片
     *
     * @param img
     * @param url
     */
    public static void loadImage(final ImageView img, final String url, final int level) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 加载本地大图加载
     *
     * @param img
     * @param url
     */
    public static void loadImageDeepZoom(final ImageView img, final String url, final int level) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(img)
                        .centerCrop()
                        .deepZoom()
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .fadeIn(false)
                        .load(url);
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 加载图片(设置固定高度)
     *
     * @param img
     * @param url
     */
    public static void loadBitmap(final ImageView img, final String url) {
        if (null != img) {
            img.setImageResource(R.mipmap.ic_launcher);
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(context)
                        .load(url)
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                if (null != result) {
                                    img.setImageBitmap(result);
                                } else {
                                    img.setImageResource(R.drawable.ic_launcher);
                                }
                            }
                        });
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }

    /**
     * 加载图片(设置固定高度)
     *
     * @param img
     * @param url
     */
    public static void loadBitmap(final ImageView img, final String url, final int level) {
        if (null != img) {
            img.setImageResource(R.drawable.ic_launcher);
            if (!TextUtils.isEmpty(url)) {
                getIonInstance().build(context)
                        .load(url)
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                if (null != result) {
                                    img.setImageBitmap(result);
                                } else {
                                    img.setImageResource(R.drawable.ic_launcher);
                                }
                            }
                        });
            } else {
                img.setImageResource(R.drawable.ic_launcher);
            }
        }
    }


    /**
     * frsco 加载本地resouce
     *
     * @param view
     * @param resId
     */
    public static void loadImage(SimpleDraweeView view, int resId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        view.setImageURI(uri);
    }



    // 要求在异步线程里做
    public static Bitmap loadSyncH5Image(Context context, String imgUrl) {
        try {
            return getIonInstance().build(context).load(imgUrl).asBitmap().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static String getUrl(String url, int type) {
        // FIXME: 2016/10/31 七牛云图片

        if (!url.contains("pic")) {
            return url;
        }

        ImgConfig.State state = ImgConfig.getImConfigState();
        if (state == ImgConfig.State.AUTO) {
            switch (type) {
                case TYPE_BIG:
                    return getAutoUrl(url, STYLE_NORMAL, STYLE_COMPRESS);
                case TYPE_AVATAR:
                    return getAutoUrl(url, STYLE_AVATAR_NORMAL, STYLE_AVATAR_COMPRESS);
                case TYPE_RELAY:
                    return getAutoUrl(url, STYLE_RELAY_NORMAL, STYLE_RELAY_COMPRESS);
                case TYPE_B_1:
                    return getAutoUrl(url, STYLE_B_1_NORMAL, STYLE_B_1_COMPRESS);
                case TYPE_B_2:
                    return getAutoUrl(url, STYLE_B_2_NORMAL, STYLE_B_2_COMPRESS);
                case TYPE_B_4:
                    return getAutoUrl(url, STYLE_B_4_NORMAL, STYLE_B_4_COMPRESS);
                case TYPE_B_MULTI:
                    return getAutoUrl(url, STYLE_B_MULTI_NORMAL, STYLE_B_MULTI_COMPRESS);
                case TYPE_IM:
                    return getAutoUrl(url, STYLE_IM_NORMAL, STYLE_IM_COMPRESS);

            }
        } else if (state == ImgConfig.State.SMALL) {
            switch (type) {
                case TYPE_BIG:
                    return getUrl(url, STYLE_COMPRESS);
                case TYPE_AVATAR:
                    return getUrl(url, STYLE_AVATAR_COMPRESS);
                case TYPE_RELAY:
                    return getUrl(url, STYLE_RELAY_COMPRESS);
                case TYPE_B_1:
                    return getUrl(url, STYLE_B_1_COMPRESS);
                case TYPE_B_2:
                    return getUrl(url, STYLE_B_2_COMPRESS);
                case TYPE_B_4:
                    return getUrl(url, STYLE_B_4_COMPRESS);
                case TYPE_B_MULTI:
                    return getUrl(url, STYLE_B_MULTI_COMPRESS);
                case TYPE_IM:
                    return getUrl(url, STYLE_IM_COMPRESS);
            }
        } else if (state == ImgConfig.State.BIG) {
            switch (type) {
                case TYPE_BIG:
                    return getUrl(url, STYLE_NORMAL);
                case TYPE_AVATAR:
                    return getUrl(url, STYLE_AVATAR_NORMAL);
                case TYPE_RELAY:
                    return getUrl(url, STYLE_RELAY_NORMAL);
                case TYPE_B_1:
                    return getUrl(url, STYLE_B_1_NORMAL);
                case TYPE_B_2:
                    return getUrl(url, STYLE_B_2_NORMAL);
                case TYPE_B_4:
                    return getUrl(url, STYLE_B_4_NORMAL);
                case TYPE_B_MULTI:
                    return getUrl(url, STYLE_B_MULTI_NORMAL);
                case TYPE_IM:
                    return getUrl(url, STYLE_IM_NORMAL);
            }
        }
        return url;
    }

    private static String getUrl(String url, String suffix) {
        return url + suffix;
    }
    private static String getAutoUrl(String url, String normal, String compress) {
        if (Constant.isWifi) {
            return url + normal;
        } else {
            return url + compress;
        }
    }



}
