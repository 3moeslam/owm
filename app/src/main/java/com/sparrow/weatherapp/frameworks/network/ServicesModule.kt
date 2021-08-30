package com.sparrow.weatherapp.frameworks.network

import com.sparrow.weatherapp.domain.datasources.GeoLocationService
import com.sparrow.weatherapp.domain.datasources.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServicesModule {

    @Provides
    @ViewModelScoped
    fun provideLocationServices(
        retrofit: Retrofit,
    ): GeoLocationService = retrofit.create(GeoLocationService::class.java)

    @Provides
    @ViewModelScoped
    fun provideWeatherService(
        retrofit: Retrofit,
    ): WeatherService = retrofit.create(WeatherService::class.java)
}