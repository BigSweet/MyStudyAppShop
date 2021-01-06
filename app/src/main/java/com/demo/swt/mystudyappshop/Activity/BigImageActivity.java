package com.demo.swt.mystudyappshop.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.demo.swt.mystudyappshop.MyApplication;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.glideprogress.CircleProgressView;
import com.demo.swt.mystudyappshop.Util.glideprogress.ProgressInterceptor;
import com.demo.swt.mystudyappshop.Util.glideprogress.ProgressListener;
import com.demo.swt.mystudyappshop.Wight.NoPreloadViewPager;
import com.shizhefei.view.largeimage.LargeImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import widght.GlideApp;

/**
 * Created by pc on 2016/12/5.
 */

public class BigImageActivity extends FragmentActivity {

    private ArrayList<String> tulist = new ArrayList<>();
    private NoPreloadViewPager noPreloadViewPager;
    private int pos;
    Context context;
    Bitmap savebitmap;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 1) {
                if (saveImageToGallery(MyApplication.getmContext(), savebitmap)) {
                    Toast.makeText(MyApplication.getmContext(), "保存成功", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigimagee);
        context = BigImageActivity.this;
        noPreloadViewPager = findViewById(R.id.nopreload);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tulist = bundle.getStringArrayList("tulist");
        pos = bundle.getInt("pos");

        noPreloadViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return tulist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = View.inflate(getApplicationContext(), R.layout.big_image_viewpage_layout, null);
                final LargeImageView imageView = view.findViewById(R.id.big_img_pre);
                final CircleProgressView progressview = view.findViewById(R.id.cir_progress);
                container.addView(view);
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BigImageActivity.this);
                        builder.setMessage("保存图片到本地");
                        //    设置一个PositiveButton
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        savebitmap = returnBitMap(tulist.get(position));
                                        if (savebitmap != null) {
                                            mHandler.sendEmptyMessage(1);
                                        }
                                    }
                                }).start();

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        return true;
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                ProgressInterceptor.addListener(tulist.get(position), new ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        progressview.setProgress(progress);
                    }
                });

                SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ProgressInterceptor.removeListener(tulist.get(position));
                        progressview.setVisibility(View.GONE);
                        imageView.setImage((resource));
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressview.setVisibility(View.VISIBLE);
                        progressview.setProgress(0);
                    }
                };

                GlideApp.with(BigImageActivity.this)
                        .load(tulist.get(position))
                        .into(simpleTarget);
                return view;
            }

        });
        noPreloadViewPager.setCurrentItem(pos);
    }

    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sweet";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        noPreloadViewPager.removeAllViews();
    }
}
