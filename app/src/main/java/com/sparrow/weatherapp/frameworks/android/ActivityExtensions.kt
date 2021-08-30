package com.sparrow.weatherapp.frameworks.android

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * Change status bar and navigation bar to given color
 *
 * @param color: color to be set to status and navigation bar
 *
 * @author Eslam Ahmad
 */
fun FragmentActivity.changeStatusBarColor(@ColorRes color: Int) {
    window?.apply {
        statusBarColor = ContextCompat.getColor(context, color)
        window.navigationBarColor = ContextCompat.getColor(context, color)
    }
}