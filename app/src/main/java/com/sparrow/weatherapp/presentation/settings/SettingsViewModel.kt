package com.sparrow.weatherapp.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sparrow.weatherapp.domain.datasources.SettingsStore
import com.sparrow.weatherapp.domain.datasources.SettingsStore.Companion.SUPPORTED_LANGUAGES
import com.sparrow.weatherapp.frameworks.android.immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsStore: SettingsStore,
) : ViewModel() {

    private val _isSaved = MutableLiveData<Boolean>()

    val isSaved = _isSaved.immutable
    val supportedLanguages get() = SUPPORTED_LANGUAGES

    fun onLanguageChanged(langIndex: Int) {
        settingsStore.language = SUPPORTED_LANGUAGES[langIndex]
    }
}