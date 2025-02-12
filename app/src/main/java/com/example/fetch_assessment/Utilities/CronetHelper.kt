package com.example.fetch_assessment.Utilities

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.chromium.net.CronetEngine
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

private const val TAG = "UrlReqCallback"

class CronetHelper(private val context: Context) {

    private val responseBuilder = StringBuilder()
    private lateinit var res:String

    suspend fun fetchData(): String = withContext(Dispatchers.IO) {
            val cronetEngine = CronetEngine.Builder(context).build()
            val executor = Executors.newSingleThreadExecutor()
            val result = CompletableDeferred<String>()

            val requestBuilder = cronetEngine.newUrlRequestBuilder(
                "https://fetch-hiring.s3.amazonaws.com/hiring.json",
                object : UrlRequest.Callback() {
                    private val responseBuffer = StringBuilder()

                    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
                    override fun onSucceeded(request: UrlRequest, p1: UrlResponseInfo) {
                        Log.i(TAG, "Request completed successfully.")
                        val res = responseBuilder.toString()
//                        println("Full Response: $res")
                        result.complete(res)
                    }

                    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
                        Log.i(TAG, "onFailed method called.")
                        result.completeExceptionally(Exception("Request failed $error"))
                    }


                    override fun onRedirectReceived(request: UrlRequest, p1: UrlResponseInfo, p2: String) {
                        Log.i(TAG, "onRedirectReceived method called.")
                        request.followRedirect()
                    }

                    override fun onResponseStarted(request: UrlRequest, p1: UrlResponseInfo) {
                        Log.i(TAG, "onResponseStarted method called.")
                        request.read(ByteBuffer.allocateDirect(102400))
                    }

                    override fun onReadCompleted(request: UrlRequest, p1: UrlResponseInfo, byteBuffer: ByteBuffer) {
                        Log.i(TAG, "onReadCompleted method called.")
                        byteBuffer.flip()

                        val bytes = ByteArray(byteBuffer.remaining())
                        byteBuffer.get(bytes) // Read bytes into array

                        val data = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes)).toString()
                        extractData(data)

                        byteBuffer.clear()
                        request.read(byteBuffer)
                    }
                },
                executor
            )

            val request = requestBuilder.build()
            request.start()

            return@withContext result.await()
        }


    fun extractData(data:String){
        val clean_data = data.replace(Regex("[^\\p{Print}\\t\\n\\r]"), "")
//        println("Received cleaned data chunk: $clean_data")
        responseBuilder.append(clean_data)
    }

}