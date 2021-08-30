package com.sparrow.weatherapp.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Kotlin representation for one-call-api
 *
 * @see <a href="https://openweathermap.org/api/one-call-api">https://openweathermap.org/api/one-call-api</a>
 *
 * @author Eslam Ahmad
 */

@Serializable
data class OneCallResponse(
    val current: Current,
    val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    val daily: List<DailyItem>,
    val lon: Double,
    val hourly: List<HourlyItem>,
    val lat: Double,
)

@Serializable
data class Current(
    val sunrise: Int,
    val temp: Double,
    val visibility: Int,
    val uvi: Double,
    val pressure: Int,
    val clouds: Int,
    @SerialName("feels_like") val feelsLike: Double,
    val dt: Long,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val sunset: Int,
    val weather: List<WeatherItem>,
    val humidity: Int,
    @SerialName("wind_speed") val windSpeed: Double,
)

@Serializable
data class HourlyItem(
    val temp: Double,
    val visibility: Int,
    val uvi: Double,
    val pressure: Int,
    val clouds: Int,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("wind_gust") val windGust: Double,
    val dt: Long,
    val pop: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val weather: List<WeatherItem>,
    val humidity: Int,
    @SerialName("wind_speed") val windSpeed: Double,
)

@Serializable
data class FeelsLike(
    val eve: Double,
    val night: Double,
    val day: Double,
    val morn: Double,
)

@Serializable
data class WeatherItem(
    val icon: String,
    val description: String,
    val main: String,
    val id: Int,
)

@Serializable
data class DailyItem(
    val moonset: Int,
    val sunrise: Int,
    val temp: Temp,
    val uvi: Double,
    val moonrise: Int,
    val pressure: Int,
    val clouds: Int,
    @SerialName("feels_like") val feelsLike: FeelsLike,
    @SerialName("wind_gust") val windGust: Double,
    val dt: Long,
    val pop: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val sunset: Int,
    val weather: List<WeatherItem>,
    val humidity: Int,
    @SerialName("wind_speed") val windSpeed: Double,
)

@Serializable
data class Temp(
    val min: Double,
    val max: Double,
    val eve: Double,
    val night: Double,
    val day: Double,
    val morn: Double,
)

