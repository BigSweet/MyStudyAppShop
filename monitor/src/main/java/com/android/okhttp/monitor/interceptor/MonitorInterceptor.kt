package com.android.okhttp.monitor.interceptor

import android.net.Uri
import android.util.Log
import com.android.okhttp.monitor.MonitorHelper
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.utils.promisesBody
import com.android.okhttp.monitor.utils.toJsonString
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.*
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min

class MonitorInterceptor : Interceptor {
    companion object {
        private const val TAG = "MonitorInterceptor"
        private val CHARSET_UTF8 = Charset.forName("UTF-8")
    }

    private var maxContentLength = 250000L

    override fun intercept(chain: Interceptor.Chain): Response {
        val monitorData = MonitorData()
        val request = chain.request()

        monitorData.method = request.method
        val url = request.url.toString()
        monitorData.url = url
        if (!url.isBlank()) {
            val uri = Uri.parse(url)
            monitorData.host = uri.host
            monitorData.path = uri.path!! + if (uri.query != null) "?" + uri.query!! else ""
            monitorData.scheme = uri.scheme
        }

        val requestBody = request.body
        monitorData.requestDate = Date()
        monitorData.requestHeaders = request.headers.toJsonString()

        requestBody?.let {
            val contentType = it.contentType()
            if (contentType != null) {
                monitorData.requestContentType = contentType.toString()
            }
        }

        monitorData.isRequestBodyIsPlainText = !bodyHasUnsupportedEncoding(request.headers)
        if (requestBody != null && monitorData.isRequestBodyIsPlainText) {
            val source = getNativeSource(Buffer(), bodyGzipped(request.headers))
            val buffer = source.buffer
            requestBody.writeTo(buffer)
            val charset: Charset?
            val contentType = requestBody.contentType()
            charset = if (contentType != null) {
                contentType.charset(CHARSET_UTF8)
            } else {
                CHARSET_UTF8
            }
            if (isPlaintext(buffer)) {
                monitorData.requestBody = readFromBuffer(buffer, charset)
            } else {
                monitorData.isResponseBodyIsPlainText = false
            }
        }
        insert(monitorData)
        val startTime = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            monitorData.errorMsg = e.toString()
            update(monitorData)
            throw e
        }
        monitorData.requestHeaders = response.request.headers.toJsonString()

        monitorData.responseDate = Date()
        monitorData.duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)
        monitorData.protocol = response.protocol.toString()
        monitorData.responseCode = response.code
        monitorData.responseMessage = response.message
        val responseBody = response.body
        if (responseBody != null) {
            monitorData.responseContentLength = responseBody.contentLength()
            val contentType = responseBody.contentType()
            if (contentType != null) {
                monitorData.responseContentType = contentType.toString()
            }
        }
        monitorData.responseHeaders = response.headers.toJsonString()
        monitorData.isResponseBodyIsPlainText = !bodyHasUnsupportedEncoding(response.headers)
        if (response.promisesBody() && monitorData.isResponseBodyIsPlainText) {
            val source = getNativeSource(response)
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer
            var charset: Charset? =
                    CHARSET_UTF8
            if (responseBody != null) {
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    try {
                        charset = contentType.charset(CHARSET_UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        update(monitorData)
                        return response
                    }

                }
            }
            if (isPlaintext(buffer)) {
                monitorData.responseBody = readFromBuffer(buffer.clone(), charset)
            } else {
                monitorData.isResponseBodyIsPlainText = false
            }
            monitorData.responseContentLength = buffer.size
        }
        update(monitorData)
        return response
    }

    private fun insert(monitorData: MonitorData) {
        MonitorHelper.insert(monitorData)
    }

    private fun update(monitorData: MonitorData) {
        MonitorHelper.update(monitorData)
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false
        }

    }

    private fun bodyHasUnsupportedEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null &&
                !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun readFromBuffer(buffer: Buffer, charset: Charset?): String {
        val bufferSize = buffer.size
        val maxBytes = min(bufferSize, maxContentLength)
        var body: String
        body = try {
            buffer.readString(maxBytes, charset!!)
        } catch (e: EOFException) {
            "\\n\\n--- Unexpected end of content ---"
        }
        if (bufferSize > maxContentLength) {
            body += "\\n\\n--- Content truncated ---"
        }
        return body
    }

    @Throws(IOException::class)
    private fun getNativeSource(response: Response): BufferedSource {
        if (bodyGzipped(response.headers)) {
            val source = response.peekBody(maxContentLength).source()
            if (source.buffer.size < maxContentLength) {
                return getNativeSource(source, true)
            } else {
                Log.e(TAG, "gzip encoded response was too long")
            }
        }
        return response.body!!.source()
    }

    private fun getNativeSource(input: BufferedSource, isGzipped: Boolean): BufferedSource {
        return if (isGzipped) {
            val source = GzipSource(input)
            source.buffer()
        } else {
            input
        }
    }

    private fun bodyGzipped(headers: Headers): Boolean {
        return "gzip".equals(headers["Content-Encoding"], ignoreCase = true)
    }

    fun maxContentLength(max: Long): MonitorInterceptor {
        this.maxContentLength = max
        return this
    }


}