package com.sparrow.weatherapp.entities

import kotlinx.serialization.Serializable

/**
 * Kotlin representation for location zip code response
 *
 * @note some of parts of this response are removed from this data class to save memory locations
 *       as we only interested in location lat lon
 *
 * @see: Too see response <a href="https://openweathermap.org/current#zip">https://openweathermap.org/current#zip</a>
 *
 * @author Eslam Ahmad
 */
@Serializable
data class ZipCodeLocationResponse(
    val coord: Coord? = null,
)

@Serializable
data class Coord(
    val lon: Double? = null,
    val lat: Double? = null,
)