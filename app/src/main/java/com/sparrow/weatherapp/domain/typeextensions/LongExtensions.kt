package com.sparrow.weatherapp.domain.typeextensions

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Map epoch seconds to ZonedTime
 *
 * @author Eslam Ahmad
 */
fun Long.toZonedTime(timeZone: ZoneId): ZonedDateTime = Instant.ofEpochSecond(this).atZone(timeZone)