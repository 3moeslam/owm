package com.sparrow.weatherapp.domain.repositories

import androidx.annotation.VisibleForTesting
import com.sparrow.weatherapp.domain.datasources.GeoLocationService
import com.sparrow.weatherapp.domain.datasources.WeatherService
import com.sparrow.weatherapp.domain.typeextensions.isValidZipCode
import com.sparrow.weatherapp.domain.typeextensions.toCallResult
import com.sparrow.weatherapp.domain.typeextensions.toLatLon
import com.sparrow.weatherapp.domain.typeextensions.toWeatherScreenData
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.ErrorType
import com.sparrow.weatherapp.entities.LatLon
import com.sparrow.weatherapp.entities.OneCallResponse
import com.sparrow.weatherapp.entities.WeatherScreenData
import javax.inject.Inject

/**
 * Weather loading business logic,
 *
 * @author Eslam Ahmad
 */
class WeatherRepository @Inject constructor(
    private val geoLocationService: GeoLocationService,
    private val weatherService: WeatherService,
) {
    /**
     * Load weather data from given [searchKey]
     *
     * @param searchKey: can be anything (City/state/country/zipcode/etc)
     *
     * @return [CallResult] for [WeatherScreenData] which can represent weather data for next week
     *
     * @author Eslam Ahmad
     */
    suspend fun loadWeatherData(
        searchKey: String,
    ): CallResult {
        return runCatching {
            when (val locationResult = loadLocation(searchKey)) {
                is CallResult.Success<*> -> loadWeather(locationResult.toLatLon(), searchKey)
                else -> locationResult
            }
        }.onFailure { CallResult.Error(ErrorType.UnknownError(it.message)) }.getOrNull()
            ?: CallResult.Error(ErrorType.UnknownError(""))
    }

    /**
     * Load weather data after GeoLocation loaded,
     * this method called after [loadWeatherData] loads location,
     * TODO: in future it should be public and load location from cached location
     *
     * @author Eslam Ahmad
     */
    private suspend fun loadWeather(latLon: LatLon?, searchKey: String): CallResult {
        if (latLon == null) return CallResult.invalidDataError()

        val weatherCallResult = weatherService.loadWeather(lat = latLon.lat, lon = latLon.lon).toCallResult()
        val weatherData = weatherCallResult.data<OneCallResponse>() ?: return weatherCallResult

        return CallResult.Success(weatherData.toWeatherScreenData(searchKey))
    }

    /**
     * Load GeoLocation Latitude and Longitude for given search key
     * which will load location by zip code if zip code provided in [searchKey]
     * or load by name if provided [searchKey] not valid zip key
     *
     * @author Eslam Ahmad
     */
    @VisibleForTesting
    suspend fun loadLocation(searchKey: String): CallResult = when {
        searchKey.isBlank() -> CallResult.invalidDataError()
        searchKey.isValidZipCode() -> loadLocationByZipCode(searchKey)
        else -> loadLocationName(searchKey)
    }

    private suspend fun loadLocationByZipCode(zipCode: String): CallResult =
        geoLocationService.loadLocationByZipCode(zipCode).toCallResult()

    private suspend fun loadLocationName(searchKey: String): CallResult =
        geoLocationService.loadLocationByName(searchKey).toCallResult()
}
