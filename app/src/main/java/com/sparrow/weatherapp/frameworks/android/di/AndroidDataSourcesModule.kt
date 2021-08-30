package com.sparrow.weatherapp.frameworks.android.di

import com.sparrow.weatherapp.domain.datasources.LocalCacheStore
import com.sparrow.weatherapp.domain.datasources.SettingsStore
import com.sparrow.weatherapp.frameworks.android.SharedPrefSettingsProvider
import com.sparrow.weatherapp.frameworks.android.SharedPrefStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module used to provide implementation of [SettingsStore], and can be used to provide other data store
 *
 * @author Eslam Ahmad
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AndroidDataSourcesModule {

    @Binds
    abstract fun bindSettingsStore(
        settingsProvider: SharedPrefSettingsProvider,
    ): SettingsStore

    @Binds
    abstract fun bindLocalCacheStore(
        localCacheStore: SharedPrefStore,
    ): LocalCacheStore
}