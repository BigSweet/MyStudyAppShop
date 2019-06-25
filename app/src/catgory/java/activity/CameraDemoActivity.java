package activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.demo.swt.mystudyappshop.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
                if (resultCode == RESULT_OK && null != data) {// 裁剪返回
                    if (cropFilePath != null && cropFilePath.length() != 0) {
                        Bitmap bitmap = BitmapFactory.decodeFile(cropFilePath);
                        //给头像设置图片源
                        mImageView.setImageBitmap(bitmap);
                    }
                }
                break;
            default:
                break;

        }
    }


    private Uri targetUri;//拍照时 指定的存储路径
    File tempFile;
    String cropFilePath;
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
            targetUri = FileProvider.getUriForFile(this, "com.demo.swt.mystudyappshop", tempFile);
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


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private String getCroPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'CROIMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date);
    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        String address = getCroPhotoFileName();
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        //这里要确保photopath存在 不然会出现保存文件失败
        cropFilePath = photopath + "/" + address + ".jpg";
        File fileDir = new File(photopath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        Uri imageUri = Uri.fromFile(new File(cropFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", false);
        intent.putExtra("fileurl", cropFilePath);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
