package com.sparrow.weatherapp.frameworks.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * observe given live data, and pass data to given call back method
 *
 * @note: it's just a simplification
 *
 * @param liveData live data instance to observe
 * @param onChange callback method will be hit with changed data
 *
 * @author Eslam Ahmad
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(this) { onChange(it) }
}