package com.rikvanvelzen.md_test.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Instead of having to adding the api key to every request via query parameter
 * this Interceptor can be used to add the api key to each request
 */
class ApiKeyInterceptor(private val apiKey: String,
                        private val apiKeyQueryParam: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(apiKeyQueryParam, apiKey)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder().url(newUrl)
        val request: Request = requestBuilder.build()

        return chain.proceed(request)
    }
}