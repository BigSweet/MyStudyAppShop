package com.spero.vision.ktx

import android.content.Context
import android.content.SharedPreferences

inline fun SharedPreferences.commit(body: SharedPreferences.Editor.() -> Unit): Boolean {
    val editor = edit()
    editor.body()
    return editor.commit()
}

inline fun SharedPreferences.applySPValue(body: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.body()
    editor.apply()
}

inline fun Context.applySPValue(body: SharedPreferences.Editor.() -> Unit, fileName: String = packageName) {
    getSharedPreferences(fileName, Context.MODE_PRIVATE).applySPValue(body)
}

inline fun Context.commit(body: SharedPreferences.Editor.() -> Unit, fileName: String = packageName) : Boolean {
    return getSharedPreferences(fileName, Context.MODE_PRIVATE).commit(body)
}

fun Context.remove(vararg keys: String, fileName: String = packageName) : Boolean {
    var editor = getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
    keys.forEach { editor.remove(it) }
    return editor.commit()
}

fun Context.clear(fileName: String = packageName) : Boolean {
    var editor = getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
    editor.clear()
    return editor.commit()
}

/** 默认值和返回值 不允许为null */
fun <T> Context.getSPValue(key: String, defaultValue: T, fileName: String = packageName) : T {
    return when (defaultValue) {
        is String -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getString(key, defaultValue as String) as T
        is Int -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getInt(key, defaultValue as Int) as T
        is Long -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getLong(key, defaultValue as Long) as T
        is Float -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getFloat(key, defaultValue as Float) as T
        is Boolean -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getBoolean(key, defaultValue as Boolean) as T
        is Set<*> -> getSharedPreferences(fileName, Context.MODE_PRIVATE).getStringSet(key, defaultValue as Set<String>) as T
        else -> throw IllegalArgumentException("getSPValue not support this class")
    }
}