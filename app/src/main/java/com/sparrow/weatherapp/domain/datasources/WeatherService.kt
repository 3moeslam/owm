package com.sparrow.weatherapp.domain.datasources

import com.sparrow.weatherapp.entities.OneCallResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    /**
     * Load weather data for given GeoLocation
     * By default we didn't load hourly and minutely weather data,
     * and imperial units is loaded (Temperature "in Fahrenheit")
     *
     * @see: Too see response <a href="https://openweathermap.org/api/one-call-api">https://openweathermap.org/api/one-call-api</a>
     *
     * @author Eslam Ahmad
     */
    @GET("data/2.5/onecall")
    suspend fun loadWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = "imperial",
    ): Response<OneCallResponse>
}