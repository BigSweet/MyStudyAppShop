package com.demo.swt.mystudyappshop.Activity;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.MyApplication;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/11/2
 */
public class DownApkIntentService extends IntentService {
    public static String FILE_PATH = Environment.getExternalStorageDirectory() + "/azhong";//文件下载保存路径
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private int currnProgress;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    int arg1 = msg.arg1;
                    int arg2 = msg.arg2;
                    DownApkIntentService.this.builder.setProgress(arg1, arg2, false);
                    DownApkIntentService.this.mNotificationManager.notify(0, DownApkIntentService.this.builder.build());
                    break;
                case 2:
                    DownApkIntentService.this.mNotificationManager.cancel(0);
                    DownApkIntentService.this.installApk();
                    Log.i("sss", "下载完成");
                    break;
                case 3:
                    DownApkIntentService.this.initNotification();
            }

        }
    };

    public DownApkIntentService() {
        super("DownApkIntentService");
    }


    public static void setStop(boolean isstop) {
        isStop = isstop;
    }

    public static boolean getStop() {
        return isStop;
    }

    static boolean isStop = true;
    FileInfo info;

    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        this.info = (FileInfo) extras.getSerializable("fileinfo");
        if (this.isTrue()) {
            Message message = new Message();
            message.what = 3;
            this.handler.sendMessage(message);
            this.downloadApk(info);
        }
    }

    private int finished = 0;//当前已下载完成的进度

    private void downloadApk(FileInfo info) {
        getLength();
        HttpURLConnection connection = null;
        RandomAccessFile rwd = null;
        try {
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            //从上次下载完成的地方下载
            int start = info.getFinished();
            if (start >= info.getLength()) {
                Toast.makeText(MyApplication.getmContext(), "下载完成", Toast.LENGTH_LONG).show();
                installApk();
            } else {
                //设置下载位置(从服务器上取要下载文件的某一段)
                connection.setRequestProperty("Range", "bytes=" + start + "-" + info.getLength());//设置下载范围
                //设置文件写入位置
                File file = new File(DownApkIntentService.FILE_PATH, info.getFileName());
                rwd = new RandomAccessFile(file, "rwd");
                //从文件的某一位置开始写入
                rwd.seek(start);
                finished += info.getFinished();
                LogUtils.d(info.getLength() + "");
                if (connection.getResponseCode() == 206) {//文件部分下载，返回码为206
                    InputStream is = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        //写入文件
                        rwd.write(buffer, 0, len);
                        finished += len;
                        info.setFinished(finished);
                        //更新界面显示
                        Message msg = new Message();
//                        int progress = (int) ((float) finished * 100.0F / (float) info.getLength());
                        msg.what = 1;
                        msg.arg1 = info.getLength();
                        msg.arg2 = finished;
                        handler.sendMessage(msg);
                        //停止下载
                        if (isStop) {
                            info.setDownLoading(false);
                            //保存此次下载的进度
                            SharedPreferenceUtil.save("info", info);
                            return;
                        }
                    }
                    //下载完成
//                    info.setDownLoading(false);
                    installApk();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (rwd != null) {
                    rwd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isTrue() {
        if (TextUtils.isEmpty(info.getUrl())) {
            Log.i("DownApkIntentService", "url is null");
            return false;
        } else if (TextUtils.isEmpty(info.getFileName())) {
            Log.i("DownApkIntentService", "path is null");
            return false;
        } else {
            return true;
        }
    }

    private void initNotification() {
        this.mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        this.builder = (new NotificationCompat.Builder(this)).setSmallIcon(R.drawable.icon_love).setContentText("正在下载").setContentTitle("应用更新");
        this.builder.setProgress(100, 0, false);
        this.mNotificationManager.notify(0, this.builder.build());
    }

    private void getLength() {
        HttpURLConnection connection = null;
        try {
            //连接网络
            URL url = new URL(info.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            int length = -1;
            if (connection.getResponseCode() == 200) {//网络连接成功
                //获得文件长度
                length = connection.getContentLength();
            }
            if (length <= 0) {
                //连接失败
                return;
            }
            //创建文件保存路径
            File dir = new File(DownLoaderManger.FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            info.setLength(length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void installApk() {
        String apkFile = DownApkIntentService.FILE_PATH + "/" + info.getFileName();
        File apkfile = new File(apkFile);
        if (apkfile.exists()) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setFlags(268435456);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri apkUri = FileProvider.getUriForFile(this, "swt.apk.provider", apkfile);
                i.addFlags(1);
                i.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            }

            this.startActivity(i);
        }
    }
}
