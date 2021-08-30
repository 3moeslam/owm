package com.sparrow.weatherapp.frameworks.android

import com.sparrow.weatherapp.domain.datasources.LocalCacheStore
import com.sparrow.weatherapp.domain.datasources.SettingsStore
import com.sparrow.weatherapp.domain.datasources.SettingsStore.Companion.LANGUAGE_KEY
import javax.inject.Inject

/**
 * Implementation of [SettingsStore] to be used to cache and provide user preferences
 * It not base on any specific framework, just base on any implementation of [LocalCacheStore]
 * So we can change local cache methodology at any time without impacting other parts,
 * just by providing other implementation for [LocalCacheStore] :)
 *
 * @author Eslam Ahmad
 */
class SharedPrefSettingsProvider @Inject constructor(
    private val sharedPref: LocalCacheStore,
) : SettingsStore {

    private var _language: String = "en"


    override var language: String
        get() = _language
        set(value) {
            _language = value
            sharedPref.setString(LANGUAGE_KEY, value)
        }


    override suspend fun invalidate() {
        _language = sharedPref.getString(LANGUAGE_KEY) ?: "en"
    }
}