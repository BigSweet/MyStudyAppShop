package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.VideoLiveWallpaper;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/6/27
 */
public class VideoWapperActivity extends AppCompatActivity {


    private CheckBox mCbVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        mCbVoice = (CheckBox) findViewById(R.id.id_cb_voice);

        mCbVoice.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            // 静音
                            VideoLiveWallpaper.voiceSilence(getApplicationContext());
                        } else {
                            VideoLiveWallpaper.voiceNormal(getApplicationContext());
                        }
                    }
                });
    }

    public void setVideoToWallPaper(View view) {
        VideoLiveWallpaper.setToWallPaper(this);
    }
}
