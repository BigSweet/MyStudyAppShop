package widght

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.demo.swt.mystudyappshop.R
import com.spero.vision.ktx.dp2px


/**
 * introduce：这里写介绍
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 25/3/2019
 */
class CstPopWindows(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : PopupWindow(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var mContext = context
    private var popTv: TextView? = null
    var popView: View? = null
    private val location = IntArray(2)
    var popHeight: Int = 0
    var popWidth: Int = 0

    init {
        popView = LayoutInflater.from(context).inflate(R.layout.share_popwindows, null)
        contentView = popView
        height = WindowManager.LayoutParams.WRAP_CONTENT
        width = WindowManager.LayoutParams.WRAP_CONTENT
        initView(context)
    }

    private fun initView(context: Context) {
        popTv = popView?.findViewById(R.id.share_pop_tv)
        isOutsideTouchable = true
        setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent))
        contentView.measure(0, 0)
        popHeight = contentView.measuredHeight
        popWidth = contentView.measuredWidth
    }


    //上方左对齐
    fun showLeftTop(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAtLocation(view, Gravity.NO_GRAVITY, location[0],
                location[1] - popHeight - mContext!!.dp2px(3f))
    }


    //上方右对齐
    fun showRightTop(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.width - popWidth,
                location[1] - popHeight - mContext!!.dp2px(3f))
    }

    //下方右对齐
    fun showRightBottom(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.width - popWidth,
                location[1] + popHeight + mContext!!.dp2px(3f))
    }

    //下方左对齐
    fun showLeftBottom(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAtLocation(view, Gravity.NO_GRAVITY, location[0],
                location[1] + popHeight + mContext!!.dp2px(3f))
    }

    //下方左对齐
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun showAsDownLeftBottom(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAsDropDown(view)
    }

    //下方右对齐
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun showAsDownRightBottom(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAsDropDown(view, view.width - popWidth, 0)
    }

    //上方右对齐
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun showAsTopRightTop(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAsDropDown(view, view.width - popWidth, -view.height - popHeight - mContext!!.dp2px(3f))
    }


    //上方左对齐
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun showAsTopLeftTop(view: View) {
        if (this.isShowing) dismiss()
        view.getLocationOnScreen(location)
        showAsDropDown(view, 0, -view.height - popHeight - mContext!!.dp2px(3f))
    }


}