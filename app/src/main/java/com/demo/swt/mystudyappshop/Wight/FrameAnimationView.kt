package com.demo.swt.mystudyappshop.Wight

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.demo.swt.mystudyappshop.R
import com.spero.vision.ktx.hide

class FrameAnimationView : AppCompatImageView {
    var animViewRes = -1

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs, defStyleAttr)
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AnimView, defStyleAttr, 0)
        animViewRes = a.getResourceId(R.styleable.AnimView_anim_view_res, R.drawable.anim_drop_loading)
        a.recycle()
        hide(false)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isAttachedToWindow && visibility == View.VISIBLE) {
            start()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (visibility == View.VISIBLE) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != View.VISIBLE) {
            stop()
        } else {
            if (isAttachedToWindow) {
                start()
            }
        }
    }

    fun start() {
        if (drawable == null) {
            setImageResource(animViewRes)
        }
        val animationDrawable = drawable
        if (animationDrawable is AnimationDrawable && !animationDrawable.isRunning) {
            animationDrawable.start()
        }
    }

    fun stop() {
        (drawable as? AnimationDrawable)?.stop()
    }
}