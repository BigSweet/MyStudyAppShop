package com.demo.swt.mystudyappshop.Wight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DisplayUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/6.
 */

public class CstImage extends RelativeLayout {

    private final int MAX_NUM = 9;
    private final int SPECIFIC_NUM_1 = 1;
    private final int SPECIFIC_NUM_2 = 2;
    private final int SPECIFIC_NUM_4 = 4;
    private int rowNum;
    private int margin;
    private int leftMargin, rightMargin, topMargin, bottomMargin;
    private ScaleAnimation downAnim;
    private ScaleAnimation upAnim;
    private Handler handler = new Handler();
    private OnChlidItemClickListener onChlidItemClickListener;
    private boolean isFour; //是否4张

    private int imgMargin;
    private int imgMarginDp = 1;

    private float onRatio = 0.67f;
    private float twoRatio = 1f;
    private float fourRatio = 0.67f;
    private float otherRation = 1f;
    private int MAX_WIDTH;

    //图片下方是否需要添加介绍
    private boolean isNeedIntro;

    private int oneWidth, twoWidth, fourWidth, otherWidth;

    public CstImage(Context context) {
        super(context);
        init();
    }

    public CstImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CstImgLayout);
        margin = a.getDimensionPixelSize(R.styleable.CstImgLayout_all_margin, 0);
        final int dp2px15 = dip2px(15);
        if (margin > 0) {
            leftMargin = rightMargin = bottomMargin = topMargin = margin;
        } else {
            isNeedIntro = a.getBoolean(R.styleable.CstImgLayout_need_intro, false);
            leftMargin = a.getDimensionPixelSize(R.styleable.CstImgLayout_left_margin, dp2px15);
            rightMargin = a.getDimensionPixelSize(R.styleable.CstImgLayout_right_margin, dp2px15);
            bottomMargin = a.getDimensionPixelSize(R.styleable.CstImgLayout_bottom_margin, dp2px15);
            topMargin = a.getDimensionPixelSize(R.styleable.CstImgLayout_top_margin, dp2px15);
        }
        rowNum = a.getInteger(R.styleable.CstImgLayout_row_num, 3);
        realWidth = a.getDimensionPixelOffset(R.styleable.CstImgLayout_real_width, 0);

        a.recycle();
        init();
    }

    public CstImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CstImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int realWidth;

    private void init() {


        int sw = realWidth <= 0 ? getResources().getDisplayMetrics().widthPixels : realWidth;
        imgMargin = DisplayUtils.dip2px(imgMarginDp);
        MAX_WIDTH = oneWidth = sw - leftMargin - rightMargin;
        twoWidth = (sw - leftMargin - rightMargin - imgMargin) / 2;
        fourWidth = twoWidth;
        otherWidth = (sw - leftMargin - rightMargin - 2 * imgMargin) / 3;
        initView();
        downAnim = new ScaleAnimation(1f, 0.98f, 1f, 0.98f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnim = new ScaleAnimation(0.98f, 1f, 0.98f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnim.setFillAfter(true);
        downAnim.setDuration(100);
        downAnim.setInterpolator(new AccelerateInterpolator());
        upAnim.setFillAfter(true);
        upAnim.setDuration(100);
        upAnim.setInterpolator(new AccelerateInterpolator());
    }

    private void initView() {
        if (isNeedIntro) {
            for (int i = 0; i < MAX_NUM; i++) {
                RelativeLayout layout = new RelativeLayout(getContext());
                layout.setVisibility(GONE);
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setId(i);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layout.addView(imageView, lp);
                addView(layout);
            }
        } else {
            for (int i = 0; i < MAX_NUM; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setVisibility(GONE);
                imageView.setId(i);
                addView(imageView);
            }
        }

    }

    public void setOnChildItemClickListener(OnChlidItemClickListener l) {
        onChlidItemClickListener = l;
    }

    public interface OnChlidItemClickListener {
        void onClick(int position, List<String> imgs);
    }

    public void setImgs(List<String> imgs) {
        if (null != imgs && !imgs.isEmpty()) {
            if (imgs.size() > MAX_NUM) {
                imgs = new ArrayList<>(imgs.subList(0, MAX_NUM));
            }
            setVisibility(View.VISIBLE);
            int size = imgs.size();
            if (size == SPECIFIC_NUM_1) {
                setOne(imgs);
            } else if (size == SPECIFIC_NUM_2) {
                setTwo(imgs);
            } else if (size == SPECIFIC_NUM_4) {
                setFour(imgs);
            } else {
                setOther(imgs);
            }
        } else {
            setVisibility(View.GONE);
        }
        requestLayout();
    }

    //一张图
    private void setOne(List<String> imgs) {
        for (int i = 0; i < MAX_NUM; i++) {
            if (0 == i) {
                getChildAt(i).setVisibility(VISIBLE);
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }
        RelativeLayout.LayoutParams lp = (LayoutParams) getChildAt(0).getLayoutParams();
        lp.width = oneWidth;
        lp.height = (int) (lp.width * onRatio);
        lp.rightMargin = 0;
        setImg(imgs, 0, getChildAt(0));
    }

    //两张图
    private void setTwo(List<String> imgs) {
        for (int i = 0; i < MAX_NUM; i++) {
            if (0 == i || 1 == i) {
                getChildAt(i).setVisibility(VISIBLE);
                RelativeLayout.LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.width = twoWidth;
                lp.height = (int) (lp.width * twoRatio);
                if (1 == i) {
                    lp.leftMargin = imgMargin + twoWidth;
                }
                setImg(imgs, i, getChildAt(i));
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }

    }

    //四张图
    private void setFour(List<String> imgs) {
        isFour = true;
        for (int i = 0; i < MAX_NUM; i++) {
            if (0 == i || 1 == i || 3 == i || 4 == i) {
                getChildAt(i).setVisibility(VISIBLE);
                RelativeLayout.LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.width = fourWidth;
                lp.height = (int) (fourWidth * fourRatio);
                lp.rightMargin = imgMargin;
                int topId = i - rowNum;
                if (topId >= 0) {
                    lp.leftMargin = 0;
                    lp.topMargin = (imgMargin + lp.height) * (i / rowNum);
                    lp.leftMargin = (fourWidth + imgMargin) * (i % rowNum);
                } else if (i > 0) {
                    int leftId = getChildAt(i - 1).getId();
                    lp.leftMargin = (fourWidth + imgMargin) * (i % rowNum);
                    lp.addRule(RelativeLayout.ALIGN_TOP, leftId);
                    lp.addRule(RelativeLayout.ALIGN_BOTTOM, leftId);
                }
                int position = i;
                if (position > 1) {
                    position--;
                }
                setImg(imgs, position, getChildAt(i));
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }
    }

    //其他
    private void setOther(List<String> imgs) {
        int size = imgs.size();
        for (int i = 0; i < MAX_NUM; i++) {
            if (i < size) {
                getChildAt(i).setVisibility(VISIBLE);
                RelativeLayout.LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.width = otherWidth;
                lp.height = (int) (otherWidth * otherRation);
                if (i % rowNum == (rowNum - 1)) {
                    lp.rightMargin = 0;
                } else {
                    lp.rightMargin = imgMargin;
                }
                int topId = i - rowNum;
                if (topId >= 0) {
                    lp.leftMargin = 0;
                    lp.topMargin = (imgMargin + lp.height) * (i / rowNum);
                    lp.leftMargin = (otherWidth + imgMargin) * (i % rowNum);
                } else if (i > 0) {
                    int leftId = getChildAt(i - 1).getId();
                    lp.leftMargin = (otherWidth + imgMargin) * (i % rowNum);
                    lp.addRule(RelativeLayout.ALIGN_TOP, leftId);
                    lp.addRule(RelativeLayout.ALIGN_BOTTOM, leftId);
                }
                setImg(imgs, i, getChildAt(i));
            } else {
                getChildAt(i).setVisibility(GONE);
            }
        }
    }


    private void setImg(final List<String> imgs, final int position, final View view) {
        ImageView imageView = null;
        ViewGroup viewGroup;
        if (!isNeedIntro && view instanceof ImageView) {
            imageView = (ImageView) view;
        } else if (view instanceof ViewGroup && (viewGroup = (ViewGroup) view).getChildCount() > 0 && viewGroup.getChildAt(0) instanceof ImageView) {
            imageView = (ImageView) viewGroup.getChildAt(0);
        }

        final ImageView img = imageView;


            Picasso.with(getContext()).load(imgs.get(position)).into(img);

            //add by zhangxutong end ,feature :按尺寸加载图片
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.startAnimation(downAnim);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.startAnimation(upAnim);
                        }
                    }, 100);

                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.clearAnimation();
                            if (null != onChlidItemClickListener) {
                                onChlidItemClickListener.onClick(position, imgs);
                            }
                        }
                    }, 120);


                }
            });
        }





}
