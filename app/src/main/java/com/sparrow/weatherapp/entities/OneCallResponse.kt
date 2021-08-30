//package com.sparrow.weatherapp.entities
//
//import kotlinx.serialization.Serializable
//
//@Serializable
//data class OneCallResponse(
//	val current: Current,
//	val timezone: String,
//	val timezoneOffset: Int,
//	val daily: List<DailyItem>,
//	val lon: Double,
//	val hourly: List<HourlyItem>,
//	val lat: Double
//)
//
//@Serializable
//data class DailyItem(
//	val temp: Temp,
//	val feelsLike: FeelsLike,
//	val dt: Int,
//	val weather: List<WeatherItem>,
//)
//
//@Serializable
//data class WeatherItem(
//	val icon: String,
//	val description: String,
//	val main: String,
//	val id: Int
//)
//
//@Serializable
//data class Current(
//	val temp: Temp,
//	val feelsLike: FeelsLike,
//	val dt: Int,
//	val weather: List<WeatherItem>,
//)
//
//@Serializable
//data class FeelsLike(
//	val eve: Double,
//	val night: Double,
//	val day: Double,
//	val morn: Double
//)
//
//@Serializable
//data class Temp(
//	val min: Double,
//	val max: Double,
//	val eve: Double,
//	val night: Double,
//	val day: Double,
//	val morn: Double
//)
//
//@Serializable
//data class HourlyItem(
//	val temp: Double,
//	val feelsLike: Double,
//	val dt: Int,
//	val weather: List<WeatherItem>,
//)
//
