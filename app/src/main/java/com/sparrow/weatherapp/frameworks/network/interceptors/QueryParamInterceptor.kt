package com.sparrow.weatherapp.frameworks.network.interceptors

import com.sparrow.weatherapp.BuildConfig
import com.sparrow.weatherapp.domain.datasources.SettingsStore
import com.sparrow.weatherapp.frameworks.network.QueryParamInterceptorOkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Used to pass common query parameters, such as lang and appid
 *
 * @author Eslam Ahmad
 */
@QueryParamInterceptorOkHttpClient
class QueryParamInterceptor @Inject constructor(
    private val settingsProvider: SettingsStore,
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain,
    ): Response = chain.request()
        .url
        .newBuilder()
        .addQueryParameter(APP_ID_KEY, BuildConfig.API_KEY)
        .addQueryParameter(LANGUAGE_KEY, settingsProvider.language)
        .build()
        .let { chain.request().newBuilder().url(it).build() }
        .let(chain::proceed)

    companion object {
        const val APP_ID_KEY = "appid"
        const val LANGUAGE_KEY = "lang"
    }
}