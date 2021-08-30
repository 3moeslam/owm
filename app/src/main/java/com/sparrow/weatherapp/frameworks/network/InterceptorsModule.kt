package com.sparrow.weatherapp.frameworks.network

import com.sparrow.weatherapp.BuildConfig
import com.sparrow.weatherapp.domain.datasources.SettingsStore
import com.sparrow.weatherapp.frameworks.network.interceptors.HeaderInterceptor
import com.sparrow.weatherapp.frameworks.network.interceptors.QueryParamInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Interceptors provider, to be injected as List<Interceptor> to httpClient
 *
 * @see [NetworkProvider.okHttpProvider]
 *
 * @author Eslam Ahmad
 */
@Module
@InstallIn(SingletonComponent::class)
object InterceptorsModule {

    /**
     * Provide Http Logging Interceptor to application
     * We should Log only in Debug mode for security reasons
     *
     * @author Eslam Ahmad
     */
    @LoggingInterceptorOkHttpClient
    @Provides
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    /**
     * Provide headers interceptor for all calls
     *
     * @author Eslam Ahmad
     */
    @HeaderInterceptorOkHttpClient
    @Provides
    fun provideHeadersInterceptor(): Interceptor = HeaderInterceptor()

    /**
     * Provides auth query param to be passed to all calls
     *
     * @author Eslam Ahmad
     */
    @QueryParamInterceptorOkHttpClient
    @Provides
    fun provideAuthInterceptor(settingsProvider: SettingsStore): Interceptor = QueryParamInterceptor(settingsProvider)

    /**
     * Provide list of interceptors to be used by [OkHttpClient]
     *
     * @note: this provider used to be able to apply Open/Closed as we can provide interceptors list from anywhere
     *
     * @author Eslam Ahmad
     */
    @Provides
    fun provideInterceptorsList(
        @LoggingInterceptorOkHttpClient loggingInterceptor: Interceptor,
        @HeaderInterceptorOkHttpClient headersInterceptor: Interceptor,
        @QueryParamInterceptorOkHttpClient authInterceptor: Interceptor,
    ): List<Interceptor> = listOf(loggingInterceptor, headersInterceptor, authInterceptor)
}