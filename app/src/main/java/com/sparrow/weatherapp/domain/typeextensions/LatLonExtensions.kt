package com.sparrow.weatherapp.domain.typeextensions

import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.LatLon
import com.sparrow.weatherapp.entities.LocationByNameResponseItem
import com.sparrow.weatherapp.entities.ZipCodeLocationResponse


/**
 * Extract Latitude and Longitude from [locationByNameResponse]
 *
 * @author Eslam Ahmad
 */
fun LocationByNameResponseItem.toLatLon(): LatLon? =
    takeIf { it.lat != null && it.lon != null }
        ?.let { LatLon(lat = it.lat!!, lon = it.lon!!) }


/**
 * Extract Latitude and Longitude from [ZipCodeLocationResponse]
 *
 * @author Eslam Ahmad
 */
fun ZipCodeLocationResponse.toLatLon(): LatLon? =
    coord
        ?.takeIf { it.lat != null && it.lon != null }
        ?.let { LatLon(lat = it.lat!!, lon = it.lon!!) }

/**
 * Extract Latitude and Longitude from Call result class
 * it will return LatLon object only if [CallResult.Success] data is
 * [LocationByNameResponse] or [ZipCodeLocationResponse]
 *
 * @return [LatLon] which is POJO hold Latitude and Longitude
 *
 * @author Eslam Ahmad
 */
fun CallResult.Success<*>.toLatLon(): LatLon? {
    return when (data) {
        is List<*> -> data.first().let { it as? LocationByNameResponseItem }?.toLatLon()
        is ZipCodeLocationResponse -> data.toLatLon()
        else -> null
    }
}
