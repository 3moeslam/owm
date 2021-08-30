package com.sparrow.weatherapp.entities

import kotlinx.serialization.Serializable

/**
 * kotlin representation for geo-api response
 *
 * @note: there are alot of data removed as we don't need them (for now), and to optimize memory usage
 *
 * @author Eslam Ahmad
 */
@Serializable
data class LocationByNameResponseItem(
    val lat: Double? = null,
    val lon: Double? = null,
)

