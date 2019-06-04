package com.demo.swt.mystudyappshop.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.demo.swt.mystudyappshop.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/28
 */

public class CustomCameraActivity extends FragmentActivity implements SurfaceHolder.Callback {
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String filepath = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
            File tempfile = new File(filepath);
            try {
                FileOutputStream fos = new FileOutputStream(tempfile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent();
                intent.putExtra("img", tempfile.getAbsolutePath());
                setResult(3, intent);
                finish();
//                startActivityForResult(intent,3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cstcamera);
        mSurfaceView = (SurfaceView) findViewById(R.id.sfview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
    }

    public void opencamrea(View view) {

        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> SupportedPreviewSizes = parameters.getSupportedPreviewSizes();// 获取支持预览照片的尺寸
        int index1 = getPictureSize(SupportedPreviewSizes);
        Camera.Size previewSize = SupportedPreviewSizes.get(index1);// 从List取出Size
        parameters.setPreviewSize(previewSize.width, previewSize.height);//
// 设置预览照片的大小
        List<Camera.Size> supportedPictureSizes =
                parameters.getSupportedPictureSizes();// 获取支持保存图片的尺寸
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        int index2 = getPictureSize(supportedPictureSizes);
        Camera.Size pictureSize = supportedPictureSizes.get(index2);// 从List取出Size

        parameters.setPictureSize(pictureSize.width, pictureSize.height);//
// 设置照片的大小
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    private int getPictureSize(List<Camera.Size> sizes) {


        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
// 屏幕的宽度

        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        int index = -1;


        for (int i = 0; i < sizes.size(); i++) {
            if (Math.abs(screenWidth - sizes.get(i).width) == 0) {
                index = i;
                break;
            }
        }
// 当未找到与手机分辨率相等的数值,取列表中间的分辨率
        if (index == -1) {
            index = sizes.size() / 2;
        }


        return index;
    }

    /**
     * 获得camera对象
     *
     * @return
     */
    public Camera getCamera() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    public void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            //相机默认是横屏，把它改成竖屏
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放相机资源
     */
    public void releaseCamrea() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamrea();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mSurfaceHolder != null) {
                setStartPreview(mCamera, mSurfaceHolder);
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamrea();
    }
}
