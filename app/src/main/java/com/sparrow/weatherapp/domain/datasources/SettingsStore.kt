package com.sparrow.weatherapp.domain.datasources

/**
 * Data source to cache/provide user preferences
 *
 * TODO: We need to implement user preferences screen based on this
 *
 * @author Eslam Ahmad
 */
interface SettingsStore {

    var language: String

    suspend fun invalidate()

    companion object {
        const val LANGUAGE_KEY = "APP_LANGUAGE"
    }
}