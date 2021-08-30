package com.sparrow.weatherapp.domain.typeextensions

import com.sparrow.weatherapp.entities.DailyItem
import com.sparrow.weatherapp.entities.DayData
import com.sparrow.weatherapp.entities.FeelsLike
import com.sparrow.weatherapp.entities.HourlyData
import com.sparrow.weatherapp.entities.HourlyItem
import com.sparrow.weatherapp.entities.OneCallResponse
import com.sparrow.weatherapp.entities.ScreenTemp
import com.sparrow.weatherapp.entities.Temp
import com.sparrow.weatherapp.entities.WeatherItem
import com.sparrow.weatherapp.entities.WeatherScreenData
import com.sparrow.weatherapp.entities.WeatherState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

/**
 * Map [OneCallResponse] which loaded from <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to displayable data
 *
 * @author Eslam Ahmad
 */
fun OneCallResponse.toWeatherScreenData(cityName: String): WeatherScreenData {
    val currentZone = Calendar.getInstance().timeZone.toZoneId()
    return WeatherScreenData(
        cityName = cityName,
        dailyData = daily.mapIndexed { index, dailyItem ->
            val dailyItems = hourly.filter { it.dt.toZonedTime(currentZone).dayOfYear == dailyItem.dt.toZonedTime(currentZone).dayOfYear }
            dailyItem.toDayData(index, dailyItems, currentZone)

        }
    )
}

/**
 * Map [DailyItem] which is part of  <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to displayable data
 *
 * @author Eslam Ahmad
 */
fun DailyItem.toDayData(id: Int, hourlyData: List<HourlyItem>, currentZone: ZoneId) = DayData(
    id = id,
    name = dt.toZonedTime(currentZone).format(DateTimeFormatter.ofPattern("EEE")),
    state = weather.first().toWeatherState(),
    minTemp = temp.min.toInt(),
    maxTemp = temp.max.toInt(),
    actualTemp = temp.toScreenTemp(),
    feelLikeTemp = feelsLike.toScreenTemp(),
    hourlyData = hourlyData.mapIndexed { index, hourlyItem -> hourlyItem.toHourlyData(index, currentZone) },
    precipitation = pop.toInt(),
    humidity = humidity
)


/**
 * Map [HourlyItem] which is part of  <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to displayable data
 *
 * @author Eslam Ahmad
 */
fun HourlyItem.toHourlyData(id: Int, currentZone: ZoneId) = HourlyData(
    state = weather.first().toWeatherState(),
    temp = temp.toInt(),
    feelLike = feelsLike.toInt(),
    id = id,
    hour = dt.toZonedTime(currentZone).format(DateTimeFormatter.ofPattern("HH:mm")),
    precipitation = pop.toInt(),
    humidity = humidity
)

/**
 * Map [FeelsLike] which is part of  <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to displayable data
 *
 * @author Eslam Ahmad
 */
fun FeelsLike.toScreenTemp() = ScreenTemp(eve.toInt(), night.toInt(), day.toInt(), morn.toInt())

/**
 * Map [Temp] which is part of  <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to displayable data
 *
 * @author Eslam Ahmad
 */
fun Temp.toScreenTemp() = ScreenTemp(eve.toInt(), night.toInt(), day.toInt(), morn.toInt())

/**
 * Map [WeatherItem] which is part of  <a href="https://openweathermap.org/api/one-call-api">one-call-api</a>
 * to enum used to differentiate between weather states
 *
 * @author Eslam Ahmad
 */
fun WeatherItem.toWeatherState() = when (icon) {
    "01n", "01d", "02n", "02d", "03n", "03d", "04n", "04d" -> WeatherState.SUNNY
    "09n", "09d", "10n", "10d" -> WeatherState.RAIN
    "11n", "11d", "13n", "13d" -> WeatherState.STORM
    "50n", "50d" -> WeatherState.MIST
    else -> WeatherState.SUNNY
}