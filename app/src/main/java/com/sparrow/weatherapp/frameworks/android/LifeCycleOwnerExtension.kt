package com.sparrow.weatherapp.frameworks.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, onChange: (T) -> Unit) {
    liveData.observe(this) { onChange(it) }
}