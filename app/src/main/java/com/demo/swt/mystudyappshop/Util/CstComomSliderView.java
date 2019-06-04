package com.demo.swt.mystudyappshop.Util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.Interface.ISlideModel;


public class CstComomSliderView<T extends ISlideModel> extends CstAutoSlideBaseView<T> {
    public CstComomSliderView(Context context) {
        super(context);
    }

    public CstComomSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstComomSliderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getImageHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getImageWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public CstAutoSlideViewAdapter getAdapter() {
        return new CommonrViewAdapter();
    }

    class CommonrViewAdapter extends CstAutoSlideViewAdapter {
        @Override
        protected View getView(int position) {
            if (null != context) {
                final ImageView img = new ImageView(context);
                img.setScaleType(ImageView.ScaleType.FIT_XY);

                if (getItem(position) != null){
                    String imgUrl = getItem(position).getImgUrl();
                    if (!TextUtils.isEmpty(imgUrl)) {
                        LoadImgUtils.loadImgBig(img, imgUrl);
                    }
                }else {
                    LoadImgUtils.loadImgBig(img, null);
                }

                return img;

            }
            return null;
        }
    }
}
