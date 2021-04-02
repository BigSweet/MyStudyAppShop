package com.android.okhttp.monitor.utils

import com.android.okhttp.monitor.db.HttpHeader
import okhttp3.Headers
import okhttp3.Response
import okhttp3.internal.http.StatusLine
import java.net.HttpURLConnection
import java.util.*

fun Headers?.toJsonString(): String {
    return if (this != null) {
        val httpHeaders = ArrayList<HttpHeader>()
        var i = 0
        val count = this.size
        while (i < count) {
            httpHeaders.add(HttpHeader(this.name(i), this.value(i)))
            i++
        }
        GsonHelper.toJson(httpHeaders)
    } else {
        ""
    }
}

fun Response.promisesBody(): Boolean {
    // HEAD requests never yield a body regardless of the response headers.
    if (request.method == "HEAD") {
        return false
    }

    val responseCode = code
    if ((responseCode < StatusLine.HTTP_CONTINUE || responseCode >= 200) && responseCode != HttpURLConnection.HTTP_NO_CONTENT && responseCode != HttpURLConnection.HTTP_NOT_MODIFIED) {
        return true
    }

    // If the Content-Length or Transfer-Encoding headers disagree with the response code, the
    // response is malformed. For best compatibility, we honor the headers.
    if (headersContentLength() != -1L || "chunked".equals(
            header("Transfer-Encoding"),
            ignoreCase = true
        )
    ) {
        return true
    }

    return false
}

/** Returns the Content-Length as reported by the response headers. */
fun Response.headersContentLength(): Long {
    return headers["Content-Length"]?.toLongOrDefault(-1L) ?: -1L
}


fun String.toLongOrDefault(defaultValue: Long): Long {
    return try {
        toLong()
    } catch (_: NumberFormatException) {
        defaultValue
    }
}
