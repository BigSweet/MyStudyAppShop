package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.AudioManager;
import com.demo.swt.mystudyappshop.Util.Dilogmanager;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/19
 */

public class ChatButton extends AppCompatButton implements AudioManager.AudioStateListener {
    private static final int DISTANCE_CANCLE_Y = 50;
    private static final int STATE_NOMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_CANLE = 3;
    private int CUR_STATE = STATE_NOMAL;
    private boolean isRecoding = false;
    private Dilogmanager dilogmanager;
    private AudioManager mAudioManager;
    private boolean mReady;

    public ChatButton(Context context) {
        this(context, null);
    }

    public ChatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        dilogmanager = new Dilogmanager(context);


        String dir = Environment.getExternalStorageDirectory() + "";
        mAudioManager = AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStateListener(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManager.prepareAudio();
                return true;
            }
        });
    }

    //录音完成后的回调
    public interface AudioFinishRecorderListener {
        void onFinsh(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setonAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        mListener = listener;
    }

    Runnable mGetVoiceRunnabel = new Runnable() {
        @Override
        public void run() {
            while (isRecoding) {
                try {
                    Thread.sleep(100);
                    mTime = mTime + 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private float mTime;
    private final static int MSG_AUDIO_PREPARE = 0X110;
    private final static int MSG_VOICE_CHANGED = 0X111;
    private final static int MSG_DILOG_DISMISS = 0X112;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARE:
                    dilogmanager.showRecordingDialog();
                    new Thread(mGetVoiceRunnabel).start();
                    break;
                case MSG_VOICE_CHANGED:
                    dilogmanager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DILOG_DISMISS:
                    dilogmanager.dissMissDialog();
                    break;
            }
        }
    };

    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                chanerState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecoding) {
                    if (wantTocancle(x, y)) {
                        chanerState(STATE_WANT_CANLE);
                    } else {
                        chanerState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }

                if (!isRecoding || mTime < 0.6f) {
                    dilogmanager.toShort();
//                    mAudioManager.cancle();
                    mHandler.sendEmptyMessageDelayed(MSG_DILOG_DISMISS, 1300);
                } else if (CUR_STATE == STATE_RECORDING) {
                    dilogmanager.dissMissDialog();
                    mAudioManager.release();
                    if (mListener != null) {
                        mListener.onFinsh(mTime, mAudioManager.getCurrentFilePath());
                    }
                }
                if (CUR_STATE == STATE_WANT_CANLE) {
                    dilogmanager.dissMissDialog();
                    mAudioManager.cancle();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        isRecoding = false;
        mTime = 0;
        mReady = false;
        chanerState(STATE_NOMAL);


    }

    private boolean wantTocancle(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }

        if (y < -DISTANCE_CANCLE_Y || y > getHeight() + DISTANCE_CANCLE_Y) {
            return true;
        }

        return false;
    }

    private void chanerState(int state) {
        if (CUR_STATE != state) {
            CUR_STATE = state;

            switch (CUR_STATE) {
                case STATE_NOMAL:
                    setBackgroundResource(R.drawable.recoding_button_state_nomal);
                    setText(R.string.str_recoding_nomal);
//                    dilogmanager.recording();
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.recoding_button_state_recodingl);
                    setText(R.string.str_recoding_recoding);
                    isRecoding = true;
                    if (isRecoding) {
                        dilogmanager.recording();
                    }
                    break;
                case STATE_WANT_CANLE:
                    setBackgroundResource(R.drawable.recoding_button_state_recodingl);
                    setText(R.string.str_recoding_cancle);
                    dilogmanager.wantToCancel();
                    break;
            }
        }
    }


}
