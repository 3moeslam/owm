package com.sparrow.weatherapp.domain.datasources

interface SettingsStore {

    var language: String

    suspend fun invalidate()

    companion object {
        const val LANGUAGE_KEY = "APP_LANGUAGE"
    }
}