package com.sparrow.weatherapp.frameworks.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module to provide [SharedPreferences]
 *
 * @author Eslam Ahmad
 */
@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {
    const val SHARED_PREF_KEY = "DEFAULT"

    @Provides
    fun provideSharedPref(app: Application): SharedPreferences = app.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
}