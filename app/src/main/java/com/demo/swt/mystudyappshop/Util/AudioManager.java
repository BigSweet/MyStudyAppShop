package com.demo.swt.mystudyappshop.Util;

import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/20
 */

public class AudioManager {
    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;
    private boolean isprepare;

    private static AudioManager mInstance;

    private AudioManager(String dir) {
        mDir = dir;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }


    public interface AudioStateListener {
        void wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener listener) {
        mListener = listener;
    }

    public static AudioManager getInstance(String dir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager(dir);
                }
            }

        }
        return mInstance;

    }

    public void prepareAudio() {
        try {
            isprepare = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String fillName = generateFileName();
//           File  mRecAudioPath= Environment.getExternalStorageDirectory();

            File mRecAudioFile = File.createTempFile(fillName, ".amr", dir);
//            File file = new File(dir, fillName);
//            File    mRecAudioFile=File.createTempFile(dir,".amr",)
            mCurrentFilePath = mRecAudioFile.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            //设置mMediaRecorder音频源是麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            int versionSdk = Build.VERSION.SDK_INT; // 设备SDK版本（Android版本号）
//            if (versionSdk > 10) {
            //设置音频的格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//            } else {
            //设置音频的格式
//                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
//            }
            //设置音频的编码
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            //设置输出文件路劲
            mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isprepare = true;
            if (mListener != null) {
                mListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        String filename = UUID.randomUUID().toString();
        Log.i("swt", filename);
        return filename;
    }

    public int getVoiceLevel(int maxlevel) {
        int relLev=1;
        if (isprepare) {
            try {
                relLev =maxlevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return relLev;

    }

    public void release() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancle() {

        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
        }
        release();

    }


}
