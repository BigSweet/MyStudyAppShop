package com.spero.vision.ktx

import android.net.Uri

val Uri.param: LinkedHashMap<String, String>
    get() = LinkedHashMap<String, String>().apply {
        queryParameterNames.forEach {
            this[it] = getQueryParameter(it)
        }
    }

/** 添加参数，并返回一个新的uri  */
fun Uri.withParam(map: Map<String, String>, isCover: Boolean = true): Uri {
    val tempParam = param
    for ((key, value) in map) {
        if (isCover || tempParam[key].isNullOrEmpty()) {
            tempParam[key] = value
        }
    }
    return newUri(tempParam)
}

/** 根据新的map，清空原来所有的参数，创建一个新的url */
fun Uri.newUri(map: Map<String, String>): Uri {
    var builder = buildUpon().clearQuery()
    for ((key, value) in map) {
        builder.appendQueryParameter(key, value)
    }
    return builder.build()
}