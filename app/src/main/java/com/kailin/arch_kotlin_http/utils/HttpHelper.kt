package com.kailin.arch_kotlin_http.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import com.kailin.arch_kotlin_http.app.MyApplication
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpHelper {

    private val userAgent: String
    private val jobMap = mutableMapOf<Any, Job>()

    init {
        val osVersion = Build.VERSION.RELEASE
        val webViewVersion = try {
            val pi = MyApplication.instance.packageManager.getPackageInfo(
                "com.google.android.webview",
                0
            )
            pi.versionName
        } catch (e: java.lang.Exception) {
            "85.0.4183.127"
        }
        userAgent =
            "vendor=Google Inc.&userAgent=Mozilla/5.0 (Linux; Android $osVersion; SM-G9860) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/$webViewVersion Mobile Safari/537.36"
    }

    fun <T> callApi(
        url: String,
        exceptionCB: (Exception) -> Unit,
        formatMethod: (String) -> T,
        successCB: (T) -> Unit
    ) {
        jobMap[url]?.cancel()
        jobMap[url] = GlobalScope.launch(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                connection = createHttpURLConnection(url)
                connection.connect()
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                    val stringBuilder = StringBuilder()
                    var input: String?
                    while (bufferedReader.readLine().also { input = it } != null)
                        stringBuilder.append(input)
                    bufferedReader.close()
                    val result = formatMethod(stringBuilder.toString())
                    successCB(result)
                }
            } catch (e: Exception) {
                exceptionCB(e)
            } finally {
                connection?.disconnect()
            }
        }
    }

    fun callImage(url: String, tag: Any = 0, cb: (Bitmap) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            jobMap[tag]?.cancel()
            jobMap[tag] = imageAsync(url).apply { await()?.let(cb) }
        }
    }

    private fun imageAsync(url: String) = GlobalScope.async(Dispatchers.IO) {
        Log.d("callImage connect", url)
        var result: Bitmap? = null
        var connection: HttpURLConnection? = null
        try {
            connection = createHttpURLConnection(url)
            connection.connect()
            Log.d("callImage connect", "$url connect")
            val inputStream: InputStream = connection.inputStream
            val outputStream = ByteArrayOutputStream()
            //獲取文件的大小
            //獲取文件的大小
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } != -1) {
                outputStream.write(buffer, 0, length)
                outputStream.flush()
            }
            inputStream.close()
            val imgBytes = outputStream.toByteArray()
            result = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
            Log.d("callImage connect", "$url OK")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
        result
    }

    private fun createHttpURLConnection(url: String): HttpURLConnection {
        return (URL(url).openConnection() as HttpURLConnection).apply {
            addRequestProperty("User-Agent", userAgent)
            useCaches = true
        }
    }

    fun clear() {
        jobMap.forEach { (_, job) -> job.cancel() }
        jobMap.clear()
    }
//    : Bitmap? {
//        var result: Bitmap? = null
//        val connection = URL(url).openConnection() as HttpURLConnection
//        connection.addRequestProperty("User-Agent", userAgnet)
//        connection.useCaches = true
//        try {
//            connection.connect()
//            val inputStream: InputStream = connection.inputStream
//            val outputStream = ByteArrayOutputStream()
//            //獲取文件的大小
//            //獲取文件的大小
//            val buffer = ByteArray(1024)
//            var length: Int
//            while (inputStream.read(buffer).also { length = it } != -1) {
//                outputStream.write(buffer, 0, length)
//                outputStream.flush()
//            }
//            inputStream.close()
//            val imgBytes = outputStream.toByteArray()
//            result = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            connection.disconnect()
//        }
//        return result
//    }
}