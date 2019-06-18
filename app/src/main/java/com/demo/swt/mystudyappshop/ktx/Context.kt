package com.spero.vision.ktx

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, message, duration).apply { show() }
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, resId, duration).apply { show() }
}

fun Context.inflateLayout(@LayoutRes layoutResId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false) = LayoutInflater.from(this).inflate(layoutResId, parent, attachToRoot)

fun Context.isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Context.arePermissionsGranted(vararg permissions: String): Boolean =
        permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }

inline fun <reified T : Activity> Context.startActivity(intentInit: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply { intentInit() })
}

inline fun Context.startActivity(intent: Intent, intentInit: Intent.() -> Unit = {}) {
    startActivity(intent.apply { intentInit() })
}

inline fun Context.startLaunchActivity(init: Intent.() -> Unit = {}) {
    startActivity(packageManager.getLaunchIntentForPackage(packageName).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        init()
    })
}

fun Context.isAppInstall(packageName: String): Boolean {
    val pinfo = packageManager.getInstalledPackages(0)
    return pinfo?.any { it.packageName == packageName } ?: false
}

/**
 * load file from assets resource of [fileName]
 */
fun Context.loadAsset(fileName: String): String? {
    try {
        return assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Context.copyString(str: String) {
    val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipBoard.primaryClip = ClipData.newPlainText("video url", str)
}
