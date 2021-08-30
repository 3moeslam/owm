package com.sparrow.weatherapp.frameworks.android

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to hide soft input "Keyboard"
 *
 * @author Eslam Ahmad
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}