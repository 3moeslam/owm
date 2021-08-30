package com.sparrow.weatherapp.frameworks.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


val <T> MutableLiveData<T>.immutable get() = this as LiveData<T>