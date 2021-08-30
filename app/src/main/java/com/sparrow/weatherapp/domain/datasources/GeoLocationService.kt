package com.sparrow.weatherapp.domain.datasources

import com.sparrow.weatherapp.entities.LocationByNameResponseItem
import com.sparrow.weatherapp.entities.ZipCodeLocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoLocationService {

    /**
     * Load geo location info by provide city name or state code or country code
     *
     * @see <a href="https://openweathermap.org/api/geocoding-api">https://openweathermap.org/api/geocoding-api</a>
     *
     * @author Eslam Ahmad
     */
    //TODO: Change [Response] object to built-in type to isolate code base from third-party dependencies
    @GET("geo/1.0/direct")
    suspend fun loadLocationByName(@Query("q") name: String, @Query("limit") limit: Int = 5): Response<List<LocationByNameResponseItem>>

    /**
     * Load geo location info by zip code
     *
     * @see <a href="https://openweathermap.org/current#zip">https://openweathermap.org/current#zip</a>
     *
     * @author Eslam Ahmad
     */
    //TODO: Change [Response] object to built-in type to isolate code base from third-party dependencies
    @GET("data/2.5/weather")
    suspend fun loadLocationByZipCode(@Query("zip") zipCode: String): Response<ZipCodeLocationResponse>
}