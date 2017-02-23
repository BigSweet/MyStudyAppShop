package com.demo.swt.mystudyappshop.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/23
 */

public class AnimationActivity extends FragmentActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        imageView = (ImageView) findViewById(R.id.img_tans);
        ObjectAnimator.ofFloat(imageView, "translationX", 0F, 200F).setDuration(2000).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 0F, 200F);
        ObjectAnimator objectAnimato2r = ObjectAnimator.ofFloat(imageView, "translationY", 0F, 200F);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(imageView, "rotation", 0F, 360F);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(objectAnimator, objectAnimato2r, objectAnimator3);
        set.setDuration(2000).start();
    }


    public void anim(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
