package com.sparrow.weatherapp.frameworks.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Used to passs common headers to network calls
 *
 * @note: There are no need to it for now,
 *
 * @author Eslam Ahmad
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain,
    ): Response = chain.request()
        .newBuilder()
        .addHeader("deviceplatform", "android")
        .build()
        .let(chain::proceed)
}