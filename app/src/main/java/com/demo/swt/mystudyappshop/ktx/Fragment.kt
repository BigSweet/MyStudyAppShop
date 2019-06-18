package com.spero.vision.ktx

import android.app.Fragment
import androidx.annotation.StringRes
import android.widget.Toast

/**
 * Created by hexi on 2018/9/11.
 */


fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast? = activity?.toast(message, duration)


fun Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast? = activity?.toast(resId, duration)

fun androidx.fragment.app.Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast? = activity?.toast(message, duration)

fun androidx.fragment.app.Fragment.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast? = activity?.toast(resId, duration)