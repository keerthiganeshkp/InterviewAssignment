package com.keerthi.interviewassignment.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("appid", "5ad7218f2e11df834b0eaf3a33a39d2a")
            .build()

        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .url(url)
            .build()

        return chain.proceed(request)
    }
}