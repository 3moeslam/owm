package com.sparrow.weatherapp.domain.typeextensions

/**
 * Validate if given string is a valid zip code
 *
 * @author Eslam Ahmad
 */
fun String.isValidZipCode(): Boolean = "^\\d{5}(?:[-\\s]\\d{4})?\$".toRegex().matches(this)