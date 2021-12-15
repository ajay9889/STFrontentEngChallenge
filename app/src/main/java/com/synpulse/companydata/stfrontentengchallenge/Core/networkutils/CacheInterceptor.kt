package com.synpulse.companydata.Core.networkutils
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class CacheInterceptor (private val context: Context) : Interceptor {
    private val maxStale = 60 * 60 * 24 * 30
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkConnectivity.isNetworkConnected(context)) {
            request = request.newBuilder()
                    .removeHeader("Access-Control-Allow-Origin")
                    .removeHeader("Vary")
                    .removeHeader("Age")
                    .removeHeader("Via")
                    .removeHeader("C3-Request")
                    .removeHeader("C3-Domain")
                    .removeHeader("C3-Date")
                    .removeHeader("C3-Hostname")
                    .removeHeader("C3-Cache-Control")
                    .removeHeader("X-Varnish-back")
                    .removeHeader("X-Varnish")
                    .removeHeader("X-Cache")
                    .removeHeader("X-Cache-Hit")
                    .removeHeader("X-Varnish-front")
                    .removeHeader("Connection")
                    .removeHeader("Accept-Ranges")
                    .removeHeader("Transfer-Encoding")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
        return chain.proceed(request)
    }
}

class OnlineCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val maxAge = 0 // read from cache for 0 second even if there is internet connection
        return response.newBuilder()
                .removeHeader("Access-Control-Allow-Origin")
                .removeHeader("Vary")
                .removeHeader("Age")
                .removeHeader("Via")
                .removeHeader("C3-Request")
                .removeHeader("C3-Domain")
                .removeHeader("C3-Date")
                .removeHeader("C3-Hostname")
                .removeHeader("C3-Cache-Control")
                .removeHeader("X-Varnish-back")
                .removeHeader("X-Varnish")
                .removeHeader("X-Cache")
                .removeHeader("X-Cache-Hit")
                .removeHeader("X-Varnish-front")
                .removeHeader("Connection")
                .removeHeader("Accept-Ranges")
                .removeHeader("Transfer-Encoding")
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
    }
}