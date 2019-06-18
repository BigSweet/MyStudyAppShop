package activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/23
 */

public class AnimationActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView image;
    private Button scale;
    private Button rotate;
    private Button translate;
    private Button mix;
    private Button alpha;
    private Button continue_btn;
    private Button continue_btn2;
    private Button flash;
    private Button move;
    private Button change;
    private Button layout;
    private Button frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);
        image = (ImageView) findViewById(R.id.image);
        scale = (Button) findViewById(R.id.scale);
        rotate = (Button) findViewById(R.id.rotate);
        translate = (Button) findViewById(R.id.translate);
        alpha = (Button) findViewById(R.id.alpha);
        continue_btn = (Button) findViewById(R.id.continue_btn);
        continue_btn2 = (Button) findViewById(R.id.continue_btn2);
        flash = (Button) findViewById(R.id.flash);
        move = (Button) findViewById(R.id.move);
        change = (Button) findViewById(R.id.change);
        layout = (Button) findViewById(R.id.layout);
        frame = (Button) findViewById(R.id.frame);
        scale.setOnClickListener(this);
        rotate.setOnClickListener(this);
        translate.setOnClickListener(this);
        alpha.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        continue_btn2.setOnClickListener(this);
        flash.setOnClickListener(this);
        move.setOnClickListener(this);
        change.setOnClickListener(this);
        layout.setOnClickListener(this);
        frame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        Animation loadAnimation;
        switch (view.getId()) {
            case R.id.scale: {
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
                image.startAnimation(loadAnimation);
                break;
            }

            case R.id.rotate: {
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                image.startAnimation(loadAnimation);
                break;
            }

            case R.id.translate: {

                loadAnimation = AnimationUtils
                        .loadAnimation(this, R.anim.translate);
                image.startAnimation(loadAnimation);
                break;
            }

            case R.id.continue_btn: {
                loadAnimation = AnimationUtils
                        .loadAnimation(this, R.anim.translate);
                image.startAnimation(loadAnimation);
                final Animation loadAnimation2 = AnimationUtils.loadAnimation(this,
                        R.anim.rotate);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // TODO Auto-generated method stub
                        image.startAnimation(loadAnimation2);
                    }
                });
                break;
            }

            case R.id.continue_btn2: {
                loadAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.continue_anim);
                image.startAnimation(loadAnimation);
                break;
            }

            case R.id.alpha: {
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                image.startAnimation(loadAnimation);
                break;
            }

            case R.id.move: {
              /*  TranslateAnimation translate = new TranslateAnimation(0, 550,
                        0, 0);
                translate.setDuration(1000);
//                translate.setRepeatCount(Animation.INFINITE);
//                translate.setRepeatMode(Animation.REVERSE);
                translate.setInterpolator(new AnticipateOvershootInterpolator());
                image.startAnimation(translate);*/
                image.animate().translationX(500).setInterpolator(new AnticipateInterpolator()).start();
                break;
            }

            case R.id.flash: {

                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                alphaAnimation.setDuration(100);
                alphaAnimation.setRepeatCount(10);
                //倒序重复REVERSE  正序重复RESTART
                alphaAnimation.setRepeatMode(Animation.REVERSE);
                image.startAnimation(alphaAnimation);

                break;
            }

            case R.id.change: {
                Intent intent = new Intent(AnimationActivity.this, NineLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
            }

            case R.id.layout: {
                Intent intent = new Intent(AnimationActivity.this, NineLockActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.frame: {
                image.setImageResource(R.drawable.anim_list);
                AnimationDrawable animationDrawable = (AnimationDrawable) image.getDrawable();
                animationDrawable.start();
                break;

            }

        }
    }

}
