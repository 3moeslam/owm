package com.sparrow.weatherapp.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocationByNameResponseItem(
    val lat: Double? = null,
    val lon: Double? = null,
)

