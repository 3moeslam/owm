package com.sparrow.weatherapp.domain.datasources

interface LocalCacheStore {
    /**
     * provide local cached string value
     *
     * @author Eslam Ahmad
     */
    fun getString(key: String): String?

    /**
     * Update/Set local cached string value
     *
     * @author Eslam Ahmad
     */
    fun setString(key: String, value: String)
}