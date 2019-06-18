package activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.CameraLiveWallpaper;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/6/27
 */
public class TranActivity extends AppCompatActivity {


    private static final int PERMISSIONS_REQUEST_CAMERA = 454;
    private Context mContext;
    static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tran_acitivty);
        mContext = this;
        findViewById(R.id.text)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkSelfPermission();
                    }
                });

    }


    /**
     * 检查权限
     */
    void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(mContext, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSION_CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        } else {
//            setTransparentWallpaper();
            startWallpaper();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    setTransparentWallpaper();
                    startWallpaper();

                } else {
                    Toast.makeText(mContext, getString(R.string._lease_open_permissions), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    /**
     * 选择壁纸
     */
    void startWallpaper() {
        final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent chooser = Intent.createChooser(pickWallpaper, getString(R.string.choose_wallpaper));
        startActivity(chooser);
    }

    /**
     * 不需要手动启动服务
     */
    void setTransparentWallpaper() {
        startService(new Intent(mContext, CameraLiveWallpaper.class));
    }
}
