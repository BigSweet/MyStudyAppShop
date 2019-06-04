package com.spero.vision.ktx

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import com.demo.swt.mystudyappshop.ktx.windowManager

inline val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

inline val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

inline val Context.realScreenHeight: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(dm)
            dm.heightPixels
        } else {
            resources.displayMetrics.heightPixels
        }
    }

inline val Context.realScreenWidth: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(dm)
            dm.widthPixels
        } else {
            resources.displayMetrics.widthPixels
        }
    }

fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun Context.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

fun Context.getNavigationBarHeight2(): Int {
    return realScreenHeight - screenHeight
}

fun Activity.listenerNavigationBarStatus(isOnce: Boolean = true, action: (nvbHeight: Int) -> Unit) {
    val navigationBarHeight = getNavigationBarHeight2()
    if (navigationBarHeight > 0) {
        val height = realScreenHeight
        val view = findViewById<View>(android.R.id.content)
        var isInvoke: Boolean = false
        if (view != null && view.height > 0) {
            action(height - view.bottom)
            isInvoke = true
        }
        if (!isOnce || !isInvoke) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    if (bottom > 0) {
                        action(height - view.bottom)
                        if (isOnce) {
                            view.removeOnLayoutChangeListener(this)
                        }
                    }
                }
            })
        }
    } else {
        action(0)
    }
}

fun Context.dp2px(dp: Float): Int {
//    val displayMetrics = resources.displayMetrics
//    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

fun Context.px2dp(px: Int): Float {
    val displayMetrics = resources.displayMetrics
    return px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            sp, resources.displayMetrics).toInt()
}

fun Context.px2sp(px: Int): Float {
    return (px / resources.displayMetrics.scaledDensity)
}