package com.android.okhttp.monitor.service

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.android.okhttp.monitor.MonitorHelper
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import java.io.IOException


class MonitorPCService(val context: Context, port: Int = 8080) : NanoHTTPD(port) {
    private val TAG = "MonitorPCService"
    override fun serve(session: IHTTPSession?): Response {

        val method = session?.method
        val uri = session?.uri
        val parms = session?.parms

        // 默认传入的url是以“/”开头的，需要删除掉，否则就变成了绝对路径
        val action = uri?.substring(1)

        Log.d(TAG, "uri = $uri   method = $method   parms = ${parms.toString()}")
        return when (action) {
            "index" -> {
                getStaticFileResponse("monitor_home.html")!!
            }
            "query" -> {
                val limit = parms?.get("limit")?.toInt() ?: 0
                newJsonResponse(MonitorHelper.getMonitorDataList(limit))
            }
            "clean" -> {
                MonitorHelper.deleteAll()
                newFixedLengthResponse("");
            }
            else -> {
                getNotFoundResponse()
            }
        }
    }

    private fun getStaticFileResponse(fileName: String): Response? {
        val assetManager: AssetManager = context.assets
        return try {
            val stream = assetManager.open(fileName)
            val extension: String = getFileExtensionName(fileName)
            newChunkedResponse(Response.Status.OK, mimeTypes()[extension], stream)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "File not exist. $fileName")
            getNotFoundResponse()
        }
    }

    private fun getFileExtensionName(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()
    }

    private fun newJsonResponse(msg: Any): Response {
        return newFixedLengthResponse(Response.Status.OK, mimeTypes()["json"], Gson().toJson(msg))
    }

    private fun getNotFoundResponse(): Response {
        return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found.");
    }
}