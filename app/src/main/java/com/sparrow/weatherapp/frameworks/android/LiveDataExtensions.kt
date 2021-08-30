package com.sparrow.weatherapp.frameworks.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Just a simple cast for [MutableLiveData] to [LiveData]
 *
 * @note: it's just a simplification
 *
 * @author Eslam Ahmad
 */
val <T> MutableLiveData<T>.immutable get() = this as LiveData<T>