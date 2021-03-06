package fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.BasePackage.SWBaseFragment;
import com.demo.swt.mystudyappshop.MyApplication;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DisplayUtils;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.Wight.SWVideoView;
import com.demo.swt.mystudyappshop.bean.CartoonListBean;
import com.demo.swt.mystudyappshop.bean.PlayBean;
import com.demo.swt.mystudyappshop.retrofit.BaseObserver;
import com.demo.swt.mystudyappshop.retrofit.RetrofitManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/25
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class PlayViewFragment extends SWBaseFragment implements View.OnClickListener {

    private ImageView btnNext;
    private ImageView btn_notscreen;
    private SWVideoView mVideo;
    private ImageView btnPlay;
    private ImageView btnScreen;
    private TextView playTxt;
    private TextView toalTxt;
    private SeekBar vudioSeekBar;
    private TextView vodioName1;
    private LinearLayout bottomView;
    private LinearLayout topView;
    private RelativeLayout vudioLayout;
    public ProgressBar dialog;
    private static final String TAG = "SPSuperPeterAnimationActivity";
    public static final String KEY_INTENT_CATEGORY_ID = "IntnetCategoryIdKey";
    private String resource_id;
    // 视频播放时间
    private int playTime;
    int mIndex = 0;
    // 自动隐藏顶部和底部View的时间
    private static final int HIDE_TIME = 6000;
    protected boolean mChangeVolume = false;//是否改变音量
    private float mLastMotionX;
    private float mLastMotionY;
    private int startX;
    private int startY;
    private int threshold;
    private boolean isClick = true;
    private ImageView BigIMg;
    private AudioManager mAudioManager;
    protected int mScreenWidth; //屏幕宽度
    protected int mScreenHeight; //屏幕高度
    protected int mSeekEndOffset; //手动滑动的起始偏移位置
    final IntentFilter filter = new IntentFilter();
    //    ArrayList<SPFirstEntity> spAmountList = new ArrayList();
//    SPFirstEntity amountItem = new SPFirstEntity();
    CartoonListBean mItem;
    List<CartoonListBean> mList = new ArrayList<>();
    protected TextView mBrightnessDialogTv;
    protected Dialog mBrightnessDialog;
    protected float mBrightnessData = -1; //亮度

    protected int mThreshold = 80; //手势偏差值
    protected boolean mFirstTouch = false;//是否首次触摸
    protected boolean mBrightness = false;//是否改变亮度
    protected int mGestureDownVolume; //手势调节音量的大小
    protected ProgressBar mDialogVolumeProgressBar;
    protected Dialog mVolumeDialog;
    protected Drawable mVolumeProgressDrawable;
    private boolean showDanmaku;

    private DanmakuView danmakuView;

    private DanmakuContext danmakuContext;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {

        btnNext = findViewById(R.id.btn_next);
        btn_notscreen = findViewById(R.id.btn_notscreen);
        btn_notscreen.setOnClickListener(this);
        mVideo = findViewById(R.id.videoView);
        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        btnScreen = findViewById(R.id.btn_screen);
        playTxt = findViewById(R.id.time_play_txt);
        toalTxt = findViewById(R.id.time_toal_txt);
        vudioSeekBar = findViewById(R.id.vudioSeekBar);
        vodioName1 = findViewById(R.id.vodioName1);
        bottomView = findViewById(R.id.bottom_view);
        topView = findViewById(R.id.top_view);
        vudioLayout = findViewById(R.id.vudio_layout);
        dialog = findViewById(R.id.vodioBar);
        BigIMg = findViewById(R.id.big_img_play);
        BigIMg.setOnClickListener(this);
        initView();
        mSeekEndOffset = DisplayUtils.dip2px(50);
        mAudioManager = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mScreenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;
        mHandler.postDelayed(hideRunnable, HIDE_TIME);

        danmakuView =  findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);

        final  LinearLayout operationLayout = (LinearLayout) findViewById(R.id.operation_layout);
        final Button send = (Button) findViewById(R.id.send);
        final EditText editText = (EditText) findViewById(R.id.edit_text);
        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operationLayout.getVisibility() == View.GONE) {
                    operationLayout.setVisibility(View.VISIBLE);
                } else {
                    operationLayout.setVisibility(View.GONE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addDanmaku(content, true);
                    editText.setText("");
                }
            }
        });
        getActivity().getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    getActivity().onWindowFocusChanged(true);
                }
            }
        });
    }

    /**
     * 向弹幕View中添加一条弹幕
     * @param content
     *          弹幕的具体内容
     * @param  withBorder
     *          弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_super_peter_animation;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }


/*    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                if (mVideo.isPlaying()) {
                    mVideo.pause();
                    btnPlay.setImageResource(R.drawable.btn_peter_play);
                }
            } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                mVideo.start();
                btnPlay.setImageResource(R.drawable.btn_peter_pause);
            }
        }
    };*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getArguments().getString()
        RetrofitManager.getInstance().getPlayData().subscribe(new BaseObserver<PlayBean>() {
            @Override
            public void onHandleSuccess(PlayBean playBean) {
                mList.addAll(playBean.getCartoon_list());
                if (!TextUtils.isEmpty(playBean.getCartoon_total() + "")) {
                }
                mItem = mList.get(0);
                if (mList != null && mList.size() > 0) {
                    for (int x = 0; x < mList.size(); x++) {
                        CartoonListBean item = mList.get(x);
                        if (resource_id != null && item.getResource_id() != null && item.getResource_id().equals(resource_id)) {
                            mItem = item;
                            break;
                        }
                    }
                }
                showDefaultData(0, mItem);
            }

        });
    }

    private void initView() {
        removeDivider();
        removeTopbanner();

        threshold = DisplayUtils.dip2px(18);

        vudioSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

        filter.addAction(Intent.ACTION_SCREEN_OFF);/*屏幕灭屏广播*/
        filter.addAction(Intent.ACTION_SCREEN_ON);/*屏幕亮屏广播*/
//        getActivity().registerReceiver(receiver, filter);
    }


    /**
     * 显示默认Data
     */

    private void showDefaultData(final int index, CartoonListBean peterItem) {
        dialog.setVisibility(View.VISIBLE);
        this.mIndex = index;
        vodioName1.setText(peterItem.getCartoon_desc());
        btnPlay.setImageResource(R.drawable.btn_peter_pause);
//        Uri uri = Uri.parse(peterItem.cartoon_mp4);
//        if (DPIUtils.getScreenWidth() * DPIUtils.getScreenHeight()) {
//
//        }
        mVideo.setVideoPath(peterItem.getCartoon_mp4());
        mVideo.requestFocus();
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideo.start();
                if (dialog.getVisibility() == View.VISIBLE) {
                    dialog.setVisibility(View.GONE);
                    mVideo.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
                }
                if (playTime != 0) {
                    mVideo.seekTo(playTime);
                }
                toalTxt.setText(formatTime(mVideo.getDuration()));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                    }
                }, 0, 1000);
            }
        });

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnPlay.setImageResource(R.drawable.btn_peter_play);
                playTxt.setText("00:00");
                vudioSeekBar.setProgress(0);
                if (mIndex + 1 == mList.size()) {
                } else {
                    mItem = mList.get(mIndex + 1);
                    showDefaultData(mIndex + 1, mItem);
                }
            }
        });

        mVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String err = "未知错误";
                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                        break;
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        err = "媒体服务终止";
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastMotionX = x;
                        mLastMotionY = y;
                        startX = (int) x;
                        startY = (int) y;
                        mFirstTouch = true;
                        mChangeVolume = false;
                        mBrightness = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float deltaX = x - mLastMotionX;
                        float deltaY = y - mLastMotionY;
                        float absDeltaX = Math.abs(deltaX);
                        float absDeltaY = Math.abs(deltaY);
                        if (!mChangeVolume && !mBrightness) {
                            if (absDeltaX > mThreshold || absDeltaY > mThreshold) {
//                            cancelProgressTimer();
                                if (absDeltaX >= mThreshold) {
                                } else {
                                    int screenHeight = DisplayUtils.getScreenHeight(getActivity());
                                    boolean noEnd = Math.abs(screenHeight - mLastMotionY) > mSeekEndOffset;
                                    if (mFirstTouch) {
                                        mBrightness = (mLastMotionX < mScreenWidth * 0.5f) && noEnd;
                                        mFirstTouch = false;
                                    }
                                    if (!mBrightness) {
                                        mChangeVolume = noEnd;
                                        mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    }
//                                mShowVKey = !noEnd;
                                }
                            }
                        }

                        if (mChangeVolume) {
                            deltaY = -deltaY;
                            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                            int deltaV = (int) (max * deltaY * 3 / mScreenWidth);
                            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                            int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenWidth);
                            Log.d("swt", "mGestureDownVolume" + mGestureDownVolume + "volumePercent" + volumePercent + "mScreenHeight" + mScreenWidth + "deltay" + deltaY);
                            showVolumeDialog(-deltaY, volumePercent);
                        } else if (mBrightness) {
                            if (Math.abs(deltaY) > mThreshold) {
                                float percent = (-deltaY / mScreenHeight);
                                onBrightnessSlide(percent);
                                mLastMotionY = y;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP://触摸屏幕
                        if (Math.abs(x - startX) > threshold
                                || Math.abs(y - startY) > threshold) {
                            isClick = false;
                        }
                        mLastMotionX = 0;
                        mLastMotionY = 0;
                        startX = (int) 0;
                        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && isClick) {
                            showOrHide();
                        }
                        isClick = true;
                        dismissVolumeDialog();
                        dismissBrightnessDialog();
                        break;

                    default:
                        break;
                }
                return true;
            }


        });
        mVideo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    dialog.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    if (mp.isPlaying()) {
                        dialog.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */

    private void onBrightnessSlide(float percent) {
        mBrightnessData = getActivity().getWindow().getAttributes().screenBrightness;
        if (mBrightnessData <= 0.00f) {
            mBrightnessData = 0.50f;
        } else if (mBrightnessData < 0.01f) {
            mBrightnessData = 0.01f;
        }
        WindowManager.LayoutParams lpa = getActivity().getWindow().getAttributes();
        lpa.screenBrightness = mBrightnessData + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        showBrightnessDialog(lpa.screenBrightness);
        getActivity().getWindow().setAttributes(lpa);
    }

    protected void dismissBrightnessDialog() {
        if (mBrightnessDialog != null) {
            mBrightnessDialog.dismiss();
            mBrightnessDialog = null;
        }
    }

    protected void showBrightnessDialog(float percent) {
        if (mBrightnessDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.video_brightness, null);
            mBrightnessDialogTv = (TextView) localView.findViewById(R.id.app_video_brightness);
            mBrightnessDialog = new Dialog(getContext(), R.style.video_style_dialog_progress);
            mBrightnessDialog.setContentView(localView);
            mBrightnessDialog.getWindow().addFlags(8);
            mBrightnessDialog.getWindow().addFlags(32);
            mBrightnessDialog.getWindow().addFlags(16);
            mBrightnessDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mBrightnessDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.RIGHT;
//            localLayoutParams.width = getWidth();
//            localLayoutParams.height = getHeight();
            int location[] = new int[2];
//            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mBrightnessDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (mBrightnessDialogTv != null)
            mBrightnessDialogTv.setText((int) (percent * 100) + "%");
    }

    private void dismissVolumeDialog() {
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
            mVolumeDialog = null;
        }
    }


    private void showVolumeDialog(float v, int volumePercent) {
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(getContext()).inflate(R.layout.video_volume_dialog, null);
            mDialogVolumeProgressBar = ((ProgressBar) localView.findViewById(R.id.volume_progressbar));
            if (mVolumeProgressDrawable != null) {
                mDialogVolumeProgressBar.setProgressDrawable(mVolumeProgressDrawable);
            }
            mVolumeDialog = new Dialog(getContext(), R.style.video_style_dialog_progress);
            mVolumeDialog.setContentView(localView);
            mVolumeDialog.getWindow().addFlags(8);
            mVolumeDialog.getWindow().addFlags(32);
            mVolumeDialog.getWindow().addFlags(16);
            mVolumeDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mVolumeDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.LEFT;
//            localLayoutParams.width = getWidth();
//            localLayoutParams.height = getHeight();
            int location[] = new int[2];

//            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mVolumeDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }

        mDialogVolumeProgressBar.setProgress(volumePercent);
    }

    @SuppressLint("SimpleDateFormat")
    private String formatTime(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mVideo.getCurrentPosition() > 0) {
                        mCurrentPosition = mVideo.getCurrentPosition();
                        playTxt.setText(formatTime(mVideo.getCurrentPosition()));
                        int progress = mVideo.getCurrentPosition() * 100 / mVideo.getDuration();
//                        if (mVideo.isPlaying() && dialog.getVisibility() == View.VISIBLE) {
//                            dialog.setVisibility(View.GONE);
//                        }
                        vudioSeekBar.setProgress(progress);
                        if (mVideo.getCurrentPosition() > mVideo.getDuration() - 100) {
                            playTxt.setText("00:00");
                            vudioSeekBar.setProgress(0);
                        }
                        vudioSeekBar.setSecondaryProgress(mVideo.getBufferPercentage());
                    } else {
                        playTxt.setText("00:00");
                        vudioSeekBar.setProgress(0);
                    }
                    break;
                case 2:
                    showOrHide();
                    break;
                default:
                    break;
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            dialog.setVisibility(View.VISIBLE);

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            int time = progress * mVideo.getDuration() / 100;
            if (fromUser) {
                mVideo.seekTo(time);
                dialog.setVisibility(View.GONE);
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    mHandler.removeCallbacks(hideRunnable);
                    mHandler.postDelayed(hideRunnable, HIDE_TIME);
                }
            }
        }
    };


    protected long mCurrentPosition; //当前的播放位置


    @Override
    public void onPause() {
        super.onPause();
        mCurrentPosition = mVideo.getCurrentPosition();
        mVideo.pause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCurrentPosition > 0 && mVideo != null) {
            mVideo.seekTo((int) mCurrentPosition);
            mVideo.start();
        }
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    /*

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mIndex != position) {
            SPSuperPeterItem peterItem = mList.get(position);
            showDefaultData(position, peterItem);
        } else {
            Toast.makeText(getApplicationContext(), "正在播放该视频，请选择其他视频", Toast.LENGTH_SHORT).show();
        }
    }*/


 /*   */

    /**
     * 横屏
     */

    private void landscapeView() {
//        topLayout.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
        bottomView.setVisibility(View.VISIBLE);
        topView.setVisibility(View.VISIBLE);
        mVideo.getLayoutParams().height = mScreenWidth;
        mVideo.getLayoutParams().width = mScreenHeight;

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {//全屏
            landscapeView();
            showOrHide();
        }
        super.onConfigurationChanged(newConfig);
    }

    private void showOrHide() {
        if (topView.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(MyApplication.getmContext(),
                    R.anim.option_leave_from_top);
            topView.setAnimation(animation);
            topView.clearAnimation();
            topView.setVisibility(View.GONE);
            NoNullUtils.setVisible(BigIMg, false);
            Animation animation1 = AnimationUtils.loadAnimation(MyApplication.getmContext(),
                    R.anim.option_leave_from_bottom);
            bottomView.startAnimation(animation1);
            bottomView.clearAnimation();
            bottomView.setVisibility(View.GONE);

            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else {
            NoNullUtils.setVisible(BigIMg, true);
            topView.setVisibility(View.VISIBLE);
            topView.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.option_entry_from_top);
            topView.startAnimation(animation);
            bottomView.setVisibility(View.VISIBLE);
            bottomView.clearAnimation();

            Animation animation1 = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.option_entry_from_bottom);
            bottomView.startAnimation(animation1);
            mHandler.removeCallbacks(hideRunnable);
            mHandler.postDelayed(hideRunnable, HIDE_TIME);
        }
    }

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
                if (topView.getVisibility() == View.VISIBLE) {
                    showOrHide();
                }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(hideRunnable);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mVideo.isPlaying()) {
                    BigIMg.setImageResource(R.drawable.btn_peter_play);

                    mVideo.pause();
                    btnPlay.setImageResource(R.drawable.btn_peter_play);
                } else {
                    mVideo.start();
                    btnPlay.setImageResource(R.drawable.btn_peter_pause);
                    BigIMg.setImageResource(R.drawable.btn_peter_pause);

                }
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    mHandler.removeCallbacks(hideRunnable);
                    mHandler.postDelayed(hideRunnable, HIDE_TIME);
                }
                break;
            case R.id.big_img_play:
                if (mVideo.isPlaying()) {
                    mVideo.pause();
                    btnPlay.setImageResource(R.drawable.btn_peter_play);
                    BigIMg.setImageResource(R.drawable.btn_peter_play);
                } else {
                    mVideo.start();
                    btnPlay.setImageResource(R.drawable.btn_peter_pause);
                    BigIMg.setImageResource(R.drawable.btn_peter_pause);
                }
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    mHandler.removeCallbacks(hideRunnable);
                    mHandler.postDelayed(hideRunnable, HIDE_TIME);
                }
                break;
            case R.id.btn_notscreen:
                finishFragment();
                break;
        }
    }
}
