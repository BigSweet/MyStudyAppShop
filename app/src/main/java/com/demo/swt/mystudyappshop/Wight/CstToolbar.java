package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.demo.swt.mystudyappshop.R;

/**
 * Created by pc on 2016/11/29.
 */

public class CstToolbar extends Toolbar {

    private LayoutInflater inflater;
    private TextView mTextTitle;
    private EditText mSearchView;
    private ImageButton mRightImageButton;
    private View mview;
    private ImageView mLeftView;
    private ImageView mRightView;


    public CstToolbar(Context context) {
        this(context, null);
    }

    public CstToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        initview();
    }

    private void initview() {
        if (mview == null) {
            mview = inflater.inflate(R.layout.toolbar, null);
            mTextTitle = (TextView) mview.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mview.findViewById(R.id.toolbar_searchview);
            mLeftView = (ImageView) mview.findViewById(R.id.toolbar_left);
            mRightView = (ImageView) mview.findViewById(R.id.toolbar_right);
          //  mRightImageButton = (ImageButton) mview.findViewById(R.id.toolbar_rightButton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mview, lp);
        }

    }

    public void setLeft(final Integer imgResId, final OnClickListener l) {
        setLeftVisible(true);
        setImgRes(mLeftView, imgResId);
        setOnClickListener(mLeftView, l);
    }

    private void setOnClickListener(final View v, final OnClickListener l) {
        if (null != v) {
            v.setOnClickListener(l);
        }
    }


    private void setImgRes(final ImageView img, final Integer imgResId) {
        if (null != img) {
            if (null != imgResId) {
                img.setImageResource(imgResId);
//                int imgH = getViewWidthOrHeight(img, false);
//                int h = height - DisplayUtils.dip2px(context, 30);
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setLeftVisible(final boolean visible) {
        setVisible(mLeftView, visible);
    }

    private void setVisible(final View v, final boolean visible) {
        if (null != v) {
            if (visible) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }


    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initview();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    private void showTitleView() {
        NoNullUtils.setVisible(mTextTitle, true);
    }

    private void hideTitleView() {
        NoNullUtils.setVisible(mTextTitle, false);
    }

    private void showSearchView() {
        NoNullUtils.setVisible(mSearchView, true);
    }

    private void hideSearchView() {
        NoNullUtils.setVisible(mSearchView, false);
    }


}
