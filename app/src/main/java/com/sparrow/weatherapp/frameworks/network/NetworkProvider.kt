package com.sparrow.weatherapp.frameworks.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sparrow.weatherapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Core Network functionality module which provides [Retrofit] instance which will used to create app services
 *
 * Components provided
 *  - Converter Factories
 *  - OkHttp Client
 *  - Retrofit instance
 *
 *  @author Eslam Ahmad
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {

    /**
     * Provides converter factory that's convert json to POJOs
     *
     * @author Eslam Ahmad
     */
    @Provides
    fun provideConverterFactory(): Converter.Factory =
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())

    /**
     * Provides OkHttp client to be used by retrofit
     *
     * @why JvmSuppressWildcards: https://github.com/google/dagger/issues/668
     * @author Eslam Ahmad
     */
    @Provides
    fun okHttpProvider(
        interceptorList: List<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient = OkHttpClient.Builder()
        .apply { interceptorList.forEach(::addInterceptor) }
        .callTimeout(BuildConfig.NETWORK_CALL_TIME_OUT_MS, TimeUnit.MILLISECONDS)
        .build()

    /**
     * Provide Retrofit instance to be create all services in future
     *
     * @note: this instance is singleton as we need to create service in all screens
     *
     * @author Eslam Ahmad
     */
    @Singleton
    @Provides
    fun retrofitProvider(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .baseUrl(BuildConfig.SERVER_URL)
        .build()
}