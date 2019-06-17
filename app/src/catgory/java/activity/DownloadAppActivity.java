package activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.demo.swt.mystudyappshop.Activity.DownApkIntentService;
import com.demo.swt.mystudyappshop.Activity.DownLoaderManger;
import com.demo.swt.mystudyappshop.Activity.FileInfo;
import com.demo.swt.mystudyappshop.Activity.OnProgressListener;
import com.demo.swt.mystudyappshop.Activity.SharedPreferenceUtil;
import com.demo.swt.mystudyappshop.R;

/**
 * Created by 37038 on 2017/6/7.
 */

public class DownloadAppActivity extends FragmentActivity implements OnProgressListener {

    private static final String APK_URL = "http://101.28.249.94/apk.r1.market.hiapk.com/data/upload/apkres/2017/4_11/15/com.baidu.searchbox_034250.apk";
    private Button mDownBtn;
    private Button mStopBtn;
    private DownLoaderManger downLoader = null;
    private FileInfo info;
    Intent intent;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dowload_app);
       /* initNotification();
        final Button start = (Button) findViewById(R.id.start);//开始下载
        final Button restart = (Button) findViewById(R.id.restart);//重新下载
        mProgressBar = (ProgressBar) findViewById(R.id.app_progress);

        final DbHelper helper = new DbHelper(this);
        downLoader = DownLoaderManger.getInstance(helper, this);
        info = new FileInfo("Kuaiya482.apk", APK_URL);
        downLoader.addTask(info);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downLoader.getCurrentState(info.getUrl())) {
                    downLoader.stop(info.getUrl());
                    start.setText("开始下载");
                } else {
                    downLoader.start(info.getUrl(), DownloadAppActivity.this);
                    start.setText("暂停下载");
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoader.restart(info.getUrl(), DownloadAppActivity.this);
                start.setText("暂停下载");
            }
        });
*/

        if (SharedPreferenceUtil.get("info") == null) {
            final FileInfo fileInfo = new FileInfo();
            fileInfo.setUrl("https://github.com/BigSweet/urlResposity/blob/master/app-debug.apk");
            fileInfo.setFileName("sss.apk");
            SharedPreferenceUtil.save("info", fileInfo);
        }

        mDownBtn = (Button) findViewById(R.id.down_btn);
        mDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DownloadAppActivity.this, DownApkIntentService.class);
                Bundle bundle = new Bundle();
                FileInfo Info = (FileInfo) SharedPreferenceUtil.get("info");
                bundle.putSerializable("fileinfo", Info);
                intent.putExtras(bundle);
                if (DownApkIntentService.getStop()) {
                    mDownBtn.setText("暂停下载");
                    DownApkIntentService.setStop(false);
                    startService(intent);
                } else {
                    mDownBtn.setText("开始下载");
                    DownApkIntentService.setStop(true);
                    stopService(intent);
                }


            }
        });

    }

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;

    private void initNotification() {
        this.mNotificationManager = (NotificationManager) DownloadAppActivity.this.getSystemService(NOTIFICATION_SERVICE);
        this.builder = new NotificationCompat.Builder(DownloadAppActivity.this).setSmallIcon(R.mipmap.icon).setContentText("正在下载").setContentTitle("应用更新");
        this.builder.setProgress(100, 0, false);
        this.mNotificationManager.notify(0, this.builder.build());
    }

    @Override
    public void updateProgress(int max, int progress) {
        builder.setProgress(max, progress, false);
        mNotificationManager.notify(0, builder.build());
        mProgressBar.setMax(max);
        mProgressBar.setProgress(progress);
    }
}
