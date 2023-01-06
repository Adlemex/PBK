package com.adlemgames.pbk

import android.content.Context
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit


class Client {
    companion object {
        lateinit var okhttp_client: OkHttpClient
        fun create(context: Context){
            val httpCacheDirectory = File(context.getCacheDir(), "api_cache")
            val cacheSize = 100 * 1024 * 1024 // 10 MiB

            val cache = Cache(httpCacheDirectory, cacheSize.toLong())
            okhttp_client = OkHttpClient.Builder()
                .addNetworkInterceptor(CacheInterceptor())
                .cache(cache)
                .build()
        }
    }
    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(365, TimeUnit.DAYS) // year cache
                .build()
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }
}