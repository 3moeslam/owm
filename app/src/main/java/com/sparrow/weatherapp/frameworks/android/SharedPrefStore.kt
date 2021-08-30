package com.sparrow.weatherapp.frameworks.android

import android.content.SharedPreferences
import com.sparrow.weatherapp.domain.datasources.LocalCacheStore
import javax.inject.Inject

/**
 * Implementation of [LocalCacheStore]
 *
 * Can be replaced at any time with minimum effort to DataStore and it will not impact any logic in application
 *
 * @author Eslam Ahmad
 */
class SharedPrefStore @Inject constructor(
    private val sharedPref: SharedPreferences,
) : LocalCacheStore {

    /**
     * provide local cached string value
     *
     * @author Eslam Ahmad
     */
    override fun getString(key: String): String? =
        sharedPref.getString(key, null)

    /**
     * Update/Set local cached string value
     *
     * @author Eslam Ahmad
     */
    override fun setString(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }
}