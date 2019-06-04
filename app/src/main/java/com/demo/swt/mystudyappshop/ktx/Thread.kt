package com.spero.vision.ktx

import android.os.Handler
import android.os.Looper

fun runAsync(action: () -> Unit) = Thread(Runnable(action)).start()

fun runOnUiThread(action: () -> Unit) {
    if (isMainLooperAlive()) {
        action()
    } else {
        Handler(Looper.getMainLooper()).post(Runnable(action))
    }
}

fun runDelayed(delayMillis: Long, action: () -> Unit) = Handler().postDelayed(Runnable(action), delayMillis)

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) = Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)

private fun isMainLooperAlive() = Looper.myLooper() == Looper.getMainLooper()