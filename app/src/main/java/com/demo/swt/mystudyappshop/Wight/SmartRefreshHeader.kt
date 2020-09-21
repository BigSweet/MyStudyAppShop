package com.demo.swt.mystudyappshop.Wight

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.demo.swt.mystudyappshop.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.spero.vision.ktx.inflateLayout

interface HeadListener {
    fun twoListener()
    fun expand()
    fun open()
}

class SmartRefreshHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : RelativeLayout(context, attrs, defStyleAttr), RefreshHeader {
    private var headerHeight = 0
    protected var mPercent = 0f
    protected var mFloorRate = 1.4f
    var listener: HeadListener? = null
    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        if (isDragging) {
            if (mPercent < mFloorRate && percent >= mFloorRate) {
                listener?.twoListener()
            }
            mPercent = percent
        }
        Log.d("swt", "isDragging" + isDragging + "percent" + percent + "offset" + offset + "height" + height + "maxDragHeight" + maxDragHeight)
    }

    fun actionUp() {
        if (mPercent > 0.5f && 1 > mPercent && state == RefreshState.PullDownToRefresh) {
            listener?.open()
        }
        if (mPercent > 0 && 1 >= mPercent && state == RefreshState.Refreshing) {
            listener?.expand()
        }

    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        Log.d("swt", "onReleased")
    }

    init {
        inflateLayout(R.layout.item_data_header, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        Log.i("SmartRefreshHeader", "height: $height, measureHeight: $measuredHeight")
        if (headerHeight == 0) {
            headerHeight = measuredHeight
        }
        if (headerHeight != measuredHeight) { // 解决header 高度发生变化，但RefreshLayout无法重新计算header 高度的bug
            headerHeight = measuredHeight
            (parent as? RefreshLayout)?.setRefreshHeader(this)
        }
    }

    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun setPrimaryColors(vararg colors: Int) {
        Log.d("swt", "setPrimaryColors")
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {
        Log.d("swt", "onInitialized")
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
        Log.d("swt", "percentX" + percentX + "offsetX" + offsetX + "offsetMax" + offsetMax)
    }

    override fun onStartAnimator(layout: RefreshLayout, height: Int, extendHeight: Int) {
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        Log.d("swt", "onFinish")
        return 0
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    var state = RefreshState.None
    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        Log.d("swt", "oldState" + oldState + "newState" + newState)
        state = newState
        when (newState) {
            RefreshState.None -> {

            }
            else -> {

            }
        }
    }
}
