package com.sparrow.weatherapp.domain.typeextensions

fun String.isValidZipCode(): Boolean = "^\\d{5}(?:[-\\s]\\d{4})?\$".toRegex().matches(this)