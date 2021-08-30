package com.sparrow.weatherapp.entities

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.sparrow.weatherapp.R

/**
 * Presentable data for one-call-response
 *
 * @see <a href="https://openweathermap.org/api/one-call-api">https://openweathermap.org/api/one-call-api</a>
 * @see: design XD file
 *
 * @author Eslam Ahmad
 */
data class WeatherScreenData(
    val cityName: String,
    val dailyData: List<DayData>,
)


data class DayData(
    val id: Int,
    val name: String,
    val state: WeatherState,
    val minTemp: Int,
    val maxTemp: Int,
    val actualTemp: ScreenTemp,
    val feelLikeTemp: ScreenTemp,
    val hourlyData: List<HourlyData>?,
    val precipitation: Int,
    val humidity: Int,
)

data class ScreenTemp(
    val eve: Int,
    val night: Int,
    val day: Int,
    val morn: Int,
)

data class HourlyData(
    val id: Int,
    val state: WeatherState,
    val temp: Int,
    val feelLike: Int,
    val hour: String,
    val precipitation: Int,
    val humidity: Int,
)

enum class WeatherState(@StringRes val stateName: Int, @ColorRes val themeColor: Int) {
    RAIN(R.string.rain, R.color.blue), SUNNY(R.string.sunny, R.color.orange), STORM(R.string.storm, R.color.green), MIST(R.string.mist, R.color.gray)
}