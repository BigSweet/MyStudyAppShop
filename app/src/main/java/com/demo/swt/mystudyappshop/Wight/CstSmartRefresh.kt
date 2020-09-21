package com.demo.swt.mystudyappshop.Wight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.util.SmartUtil

/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 2020/9/18
 */
internal class CstSmartRefresh : SmartRefreshLayout {
    var upListener: (() -> Unit)? = null
    override fun setViceState(state: RefreshState?) {
        super.setViceState(state)
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_UP) {
            upListener?.invoke()
        }
        return super.dispatchTouchEvent(e)
    }

    override fun autoRefresh(delayed: Int, duration: Int, dragRate: Float, animationOnly: Boolean): Boolean {
        return if (isEnableRefreshOrLoadMore(mEnableRefresh)) {
            val runnable = Runnable {
                if (mViceState != RefreshState.Refreshing) return@Runnable
                if (reboundAnimator != null) {
                    reboundAnimator.cancel()
                }
                val thisView: View = this@CstSmartRefresh
                mLastTouchX = thisView.measuredWidth / 2f
                mKernel.setState(RefreshState.PullDownToRefresh)
                reboundAnimator = ValueAnimator.ofInt(mSpinner, (mHeaderHeight * dragRate).toInt())
                reboundAnimator.duration = duration.toLong()
                reboundAnimator.interpolator = SmartUtil(SmartUtil.INTERPOLATOR_VISCOUS_FLUID)
                reboundAnimator.addUpdateListener { animation ->
                    if (reboundAnimator != null) {
                        mKernel.moveSpinner(animation.animatedValue as Int, true)
                    }
                }
                reboundAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (reboundAnimator != null) {
                            reboundAnimator = null
                            if (mState != RefreshState.ReleaseToRefresh) {
                                mKernel.setState(RefreshState.ReleaseToRefresh)
                            }
                            setStateRefreshing(!animationOnly)
                        }
                    }
                })
                reboundAnimator.start()
            }
            setViceState(RefreshState.Refreshing)
            if (delayed > 0) {
                mHandler.postDelayed(runnable, delayed.toLong())
            } else {
                runnable.run()
            }
            true
        } else {
            false
        }
    }
}