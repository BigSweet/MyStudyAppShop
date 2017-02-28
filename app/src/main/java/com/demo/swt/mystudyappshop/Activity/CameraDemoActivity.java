package com.demo.swt.mystudyappshop.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/27
 */

public class CameraDemoActivity extends FragmentActivity {
    private static final int REQ1 = 1;
    private static final int REQ2 = 2;
    private static final int REQ3 = 3;
    private ImageView imageView;
    private String filepath;
    FileInputStream fis = null;
//    private Button sysBt, cstbt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerdemo);
        imageView = (ImageView) findViewById(R.id.img);
//        sysBt = (Button) findViewById(R.id.sysbt);
//        cstbt = (Button) findViewById(cstbt);
        filepath = Environment.getExternalStorageDirectory().getPath();
        filepath = filepath + "/" + "temp.png";

//        Bitmap bitmap = BitmapFactory.decodeFile(path);
    }


    public void openlittlecamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ1);
    }

    public void opencstcamera(View view) {
        startActivityForResult(new Intent(this, CustomCameraActivity.class), REQ3);
    }


    public void opensyscamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(filepath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQ2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQ1) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);
            }
            if (requestCode == REQ2) {
                try {
                    fis = new FileInputStream(filepath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        if (resultCode == REQ3) {
//            cstbt.setVisibility(View.GONE);
//            sysBt.setVisibility(View.GONE);
            Bundle bundle = data.getExtras();
            String path = bundle.getString("img");
            try {
                FileInputStream fileInputStream = new FileInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                Matrix matrix = new Matrix();
                matrix.setRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
