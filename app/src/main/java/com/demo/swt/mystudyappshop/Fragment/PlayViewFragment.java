package com.demo.swt.mystudyappshop.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.BasePackage.SWBaseFragment;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DisplayUtils;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/4/25
 */
public class PlayViewFragment extends SWBaseFragment implements View.OnClickListener {

    ImageView btnNext;
    ImageView btn_notscreen;
    SWVideoView mVideo;
    ImageView btnPlay;
    ImageView btnScreen;
    TextView playTxt;
    TextView toalTxt;
    SeekBar vudioSeekBar;
    TextView vodioName;
    TextView vodioName1;
    TextView numTxt;
    LinearLayout bottomView;
    LinearLayout topView;
    RelativeLayout vudioLayout;
    RelativeLayout centerLayout;
    public ProgressBar dialog;
    private static final String TAG = "SPSuperPeterAnimationActivity";
    public static final String KEY_INTENT_CATEGORY_ID = "IntnetCategoryIdKey";
    private String resource_id;

    // 视频播放时间
    private int playTime;

    int mIndex = 0;


    // 自动隐藏顶部和底部View的时间
    private static final int HIDE_TIME = 6000;

    private float mLastMotionX;
    private float mLastMotionY;
    private int startX;
    private int startY;
    private int threshold;
    private boolean isClick = true;

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
        vodioName = findViewById(R.id.vodioName);
        vodioName1 = findViewById(R.id.vodioName1);
        numTxt = findViewById(R.id.toal_num_txt);
        bottomView = findViewById(R.id.bottom_view);
        topView = findViewById(R.id.top_view);
        vudioLayout = findViewById(R.id.vudio_layout);
        centerLayout = findViewById(R.id.center_layout);
        dialog = findViewById(R.id.vodioBar);


        initView();
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




    BroadcastReceiver receiver = new BroadcastReceiver() {
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
    };
    final IntentFilter filter = new IntentFilter();

    //    ArrayList<SPFirstEntity> spAmountList = new ArrayList();
//    SPFirstEntity amountItem = new SPFirstEntity();
    CartoonListBean mItem;
    List<CartoonListBean> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getArguments().getString()
        RetrofitManager.getInstance().getPlayData().subscribe(new BaseObserver<PlayBean>() {
            @Override
            public void onHandleSuccess(PlayBean playBean) {
                mList.addAll(playBean.getCartoon_list());
                if (!TextUtils.isEmpty(playBean.getCartoon_total() + "")) {
                    numTxt.setText(playBean.getCartoon_total() + "集全");
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
        getActivity().registerReceiver(receiver, filter);
    }





    /**
     * 显示默认Data
     */

    private void showDefaultData(final int index, CartoonListBean peterItem) {
        dialog.setVisibility(View.VISIBLE);
        this.mIndex = index;
        vodioName.setText(peterItem.getCartoon_desc());
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
                final float x = event.getX();
                final float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastMotionX = x;
                        mLastMotionY = y;
                        startX = (int) x;
                        startY = (int) y;
                        break;
                    case MotionEvent.ACTION_MOVE:
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
            if (fromUser) {
                int time = progress * mVideo.getDuration() / 100;
                mVideo.seekTo(time);
                dialog.setVisibility(View.GONE);
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    mHandler.removeCallbacks(hideRunnable);
                    mHandler.postDelayed(hideRunnable, HIDE_TIME);
                }
            }
        }
    };


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


    /**
     * 横屏
     */

    private void landscapeView() {
//        topLayout.setVisibility(View.GONE);
        vodioName.setVisibility(View.GONE);
        centerLayout.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
        bottomView.setVisibility(View.VISIBLE);
        topView.setVisibility(View.VISIBLE);
        mVideo.getLayoutParams().height = DisplayUtils.getScreenHeight();
        mVideo.getLayoutParams().width = DisplayUtils.getScreenWidth();

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {//全屏
            landscapeView();
            isRunnable = true;
            showOrHide();
        }
        super.onConfigurationChanged(newConfig);
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                onBackPressed();
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }*/

    private void showOrHide() {
        if (topView.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.option_leave_from_top);
            topView.setAnimation(animation);
            topView.clearAnimation();
            topView.setVisibility(View.GONE);

            Animation animation1 = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.option_leave_from_bottom);
            bottomView.startAnimation(animation1);
            bottomView.clearAnimation();
            bottomView.setVisibility(View.GONE);

            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else {
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

    boolean isRunnable = false;
    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunnable) {
                showOrHide();
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (receiver != null) {
            try {
                getActivity().unregisterReceiver(receiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mVideo.isPlaying()) {
                    mVideo.pause();
                    btnPlay.setImageResource(R.drawable.btn_peter_play);
                } else {
                    mVideo.start();
                    btnPlay.setImageResource(R.drawable.btn_peter_pause);
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
