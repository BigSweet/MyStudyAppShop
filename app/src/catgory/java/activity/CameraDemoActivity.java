package activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.m.permission.MPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/27
 */

public class CameraDemoActivity extends FragmentActivity {
    ImageView mImageView;
    Bitmap head;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;//拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;//相册
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerdemo);
        mImageView = (ImageView) findViewById(R.id.photo_img);
    }

    public void takePhoto(View view) {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            requestCameraSuccess();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestCameraFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getPhotoFromPhone(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            requestContactSuccess();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestContactFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(targetUri, 200);// 裁剪图片
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(data.getData(), 200);// 裁剪图片
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null && !"".equals(data)) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        mImageView.setImageBitmap(head);
                        /**
                         * 上传服务器代码
                         * 我先注释有需要的替换成自己的接口就可以了
                         */
                 /*       RequestBody requestFile =
                                RequestBody.create(MediaType.parse("image/png"), cropFilePath);
                        // MultipartBody.Part is used to send also the actual file name
                        MultipartBody.Part Part =
                                MultipartBody.Part.createFormData("avatar", cropFilePath.getName(), requestFile);
                        Upload(Part);*/
                    }
                }
                break;
            default:
                break;

        }
    }


    private Uri targetUri;//拍照时 指定的存储路径
    File tempFile;
    File cropFilePath;
    public static final String photopath = Environment.getExternalStorageDirectory() + "/photodemo";


    public void requestCameraSuccess() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Toast.makeText(this, "sd卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        if (targetUri == null) {
            File dirfile = new File(photopath);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            tempFile = new File(photopath, getPhotoFileName());
           /* if (!tempFile.exists()) {
                try {
                    tempFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            targetUri = FileProvider.getUriForFile(this,"com.demo.swt.mystudyappshop", tempFile);
//            targetUri = Uri.fromFile(tempFile);
        }
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
        startActivityForResult(intent2, PHOTO_REQUEST_TAKEPHOTO);// 采用ForResult打开
    }

    public void requestCameraFailed() {
        Toast.makeText(this, "没有摄像机权限", Toast.LENGTH_SHORT).show();
    }

    public void requestContactSuccess() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Toast.makeText(this, "sd卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        // 设置文件类型
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    public void requestContactFailed() {
        Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private String getCroPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'CROIMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        cropFilePath = new File(photopath, getCroPhotoFileName());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFilePath));
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
