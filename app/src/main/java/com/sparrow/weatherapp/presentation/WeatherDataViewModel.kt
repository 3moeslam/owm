package com.sparrow.weatherapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparrow.weatherapp.R
import com.sparrow.weatherapp.domain.datasources.GeoLocationService
import com.sparrow.weatherapp.domain.repositories.WeatherRepository
import com.sparrow.weatherapp.entities.CallResult
import com.sparrow.weatherapp.entities.DayData
import com.sparrow.weatherapp.entities.ErrorType
import com.sparrow.weatherapp.entities.HourlyData
import com.sparrow.weatherapp.entities.WeatherScreenData
import com.sparrow.weatherapp.entities.WeatherState
import com.sparrow.weatherapp.frameworks.android.immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _navigate = MutableLiveData<Boolean>()
    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Int?>()

    private val _cityName = MutableLiveData<String>()
    private val _selectedDay = MutableLiveData<DayData>()
    private val _actualTemp = MutableLiveData<String>()
    private val _humidity = MutableLiveData<String>()
    private val _precipitation = MutableLiveData<String>()
    private val _weatherState = MutableLiveData<WeatherState>()

    val navigate = _navigate.immutable
    val loading = _loading.immutable
    val error = _error.immutable

    val selectedDay = _selectedDay.immutable
    val actualTemp = _actualTemp.immutable
    val weatherState = _weatherState.immutable
    val humidity = _humidity.immutable
    val precipitation = _precipitation.immutable
    val cityName = _cityName.immutable

    val days: Array<String> get() = weatherData.dailyData.map { it.name }.toTypedArray()

    private lateinit var weatherData: WeatherScreenData
    private var selectedHour: HourlyData? = null

    /**
     * Refresh screen data based on pre-selected city name
     *
     * @author Eslam Ahmad
     */
    fun refresh() {
        _cityName.value?.let { search(it) }
    }

    /**
     * Load weather data based on given keyword (a City/state/country/zipcode/etc)
     *
     * @param searchKey : City/state/country/zipcode/etc
     *
     * @author Eslam Ahmad
     */
    fun search(searchKey: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.postValue(true)
        _error.postValue(null)
        when (val callResult = weatherRepository.loadWeatherData(searchKey)) {
            is CallResult.Success<*> -> {
                val data = callResult.data as WeatherScreenData
                onWeatherDataLoaded(data)
            }
            is CallResult.Error -> {
                _loading.postValue(false)
                _error.postValue(callResult.errorType.getStringId())
            }
        }
    }

    /**
     * Change populated weather data based on selected day
     *
     * @author Eslam Ahmad
     */
    fun onDayChanged(newVal: Int) {
        val newSelectedDay = weatherData.dailyData[newVal.minus(1)]
        _selectedDay.postValue(newSelectedDay)
        selectedHour = newSelectedDay.hourlyData?.firstOrNull()

        populateCurrentWeatherData(newSelectedDay)
    }

    /**
     * Change populated weather data based on selected hour
     *
     * @author Eslam Ahmad
     */
    fun onHourChanged(newVal: Int) {
        val selectedDay = _selectedDay.value ?: return
        selectedHour = selectedDay.hourlyData?.get(newVal.minus(1)) ?: return
        populateCurrentWeatherData(selectedDay)
    }

    private fun onWeatherDataLoaded(data: WeatherScreenData) {
        val day = data.dailyData.first()
        selectedHour = day.hourlyData?.first()
        weatherData = data

        _cityName.postValue(data.cityName)
        _selectedDay.postValue(day)

        _actualTemp.postValue(selectedHour?.temp?.toString() ?: day.actualTemp.toString())
        _weatherState.postValue(selectedHour?.state)

        _loading.postValue(false)
        _navigate.postValue(true)
        _precipitation.postValue(selectedHour?.precipitation?.toString() ?: day.precipitation.toString())
        _humidity.postValue(selectedHour?.humidity?.toString() ?: day.humidity.toString())
    }

    private fun populateCurrentWeatherData(newSelectedDay: DayData) {
        _actualTemp.postValue(selectedHour?.temp?.toString() ?: newSelectedDay.actualTemp.eve.toString())
        _weatherState.postValue(selectedHour?.state ?: newSelectedDay.state)
        _precipitation.postValue(selectedHour?.precipitation?.toString() ?: newSelectedDay.precipitation.toString())
        _humidity.postValue(selectedHour?.humidity?.toString() ?: newSelectedDay.humidity.toString())
    }
}

private fun ErrorType.getStringId(): Int = when (this) {
    is ErrorType.ApiError -> R.string.please_try_again
    is ErrorType.LogicalError -> R.string.please_try_again
    ErrorType.NoInternet -> R.string.no_internet_connection
    is ErrorType.UnknownError -> R.string.please_try_again
}

