package com.sparrow.weatherapp.presentation.bindingadaptors

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.sparrow.weatherapp.R
import com.sparrow.weatherapp.entities.WeatherState

/**
 * Method provide gradient color based on weather state
 * TODO: move colors to colors file
 *
 * @author Eslam Ahmad
 */
private fun WeatherState.backgroundColors(): List<String> = when (this) {
    WeatherState.RAIN -> listOf("#0098B1", "#053D75")
    WeatherState.SUNNY -> listOf("#ECBB69", "#E36550")
    WeatherState.STORM -> listOf("#E2EEA4", "#065963")
    WeatherState.MIST -> listOf("#79838D", "#B9C6D6")
}

/**
 * Method provide weather background icon based on weather state
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
private fun WeatherState.weatherBackgroundIcon(): Int = when (this) {
    WeatherState.RAIN -> R.drawable.ic_rain_background
    WeatherState.SUNNY -> R.drawable.ic_sunny_background
    WeatherState.STORM -> R.drawable.ic_storm_background
    WeatherState.MIST -> R.drawable.ic_mist_background
}

/**
 * Method provide weather icon based on weather state
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
private fun WeatherState.weatherIcon(): Int = when (this) {
    WeatherState.RAIN -> R.drawable.ic_rain
    WeatherState.SUNNY -> R.drawable.ic_sunny
    WeatherState.STORM -> R.drawable.ic_storm
    WeatherState.MIST -> R.drawable.ic_mist
}

/**
 * binding adapter will change ViewGroup background to Gradient color provided from [backgroundColors]
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
@BindingAdapter("weatherBackground")
fun bindWeatherBackground(view: ViewGroup, state: WeatherState) {
    GradientDrawable(
        GradientDrawable.Orientation.BL_TR,
        state.backgroundColors().map { Color.parseColor(it) }.toIntArray()
    ).apply { cornerRadius = 0f }.run { view.background = this }
}

/**
 * binding adapter will change ImageView src to image provided from [weatherBackgroundIcon]
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
@BindingAdapter("weatherBackground")
fun bindWeatherBackground(view: ImageView, state: WeatherState) {
    view.setImageResource(state.weatherBackgroundIcon())
}

/**
 * binding adapter will change ImageView src to image provided from [weatherIcon]
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
@BindingAdapter("weatherIcon")
fun bindWeatherIcon(view: ImageView, state: WeatherState) {
    view.setImageResource(state.weatherIcon())
}


/**
 * binding adapter will change TextView text string resource provided
 *
 * @see: XD File
 *
 * @author Eslam Ahmad
 */
@BindingAdapter("android:text")
fun setText(view: TextView, @StringRes resId: Int) {
    if (resId == 0) {
        view.text = null
    } else {
        view.setText(resId)
    }
}