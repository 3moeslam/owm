package com.sparrow.weatherapp.presentation.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.sparrow.weatherapp.databinding.FragmentWeatherBinding
import com.sparrow.weatherapp.entities.DayData
import com.sparrow.weatherapp.entities.HourlyData
import com.sparrow.weatherapp.entities.WeatherScreenData
import com.sparrow.weatherapp.entities.WeatherState
import com.sparrow.weatherapp.frameworks.android.hideKeyboard
import com.sparrow.weatherapp.frameworks.android.observe
import com.sparrow.weatherapp.presentation.loadlocation.WeatherDataViewModel


class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel by activityViewModels<WeatherDataViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindScreenData()

        setWheelsChangeListeners()
        setScreenActions()
        observeViewModelChanges()
    }

    private fun setScreenActions() = with(binding) {
        swipeRefresh.setOnRefreshListener { viewModel.refresh() }
        locationEntryEditText.setOnEditorActionListener { v, _, _ ->
            search()
            true
        }
        locationEntryLayout.setEndIconOnClickListener {
            search()
        }
    }

    private fun search() {
        viewModel.search(binding.locationEntryEditText.text.toString())
        view?.hideKeyboard()
    }

    private fun bindScreenData() = with(binding) {
        lifecycleOwner = this@WeatherFragment
        vm = viewModel
        val days = viewModel.days

        daysWheel.apply {
            minValue = 1
            maxValue = days.size
            displayedValues = days
        }
    }

    private fun setWheelsChangeListeners() = with(binding) {
        daysWheel.setOnValueChangedListener { _, _, newVal ->
            viewModel.onDayChanged(newVal)
        }
        hoursWheel.setOnValueChangedListener { _, _, newVal ->
            viewModel.onHourChanged(newVal)
        }
    }

    private fun observeViewModelChanges() = with(viewModel) {
        observe(loading, ::onLoadingChanged)
        observe(selectedDay, ::onSelectedDateChanged)
    }

    private fun onLoadingChanged(isLoading: Boolean) = with(binding) {
        swipeRefresh.isRefreshing = isLoading
    }

    private fun onSelectedDateChanged(dayData: DayData) = with(binding) {
        bindHours(dayData)
    }

    private fun FragmentWeatherBinding.bindHours(dayData: DayData) {
        if (dayData.hourlyData?.isEmpty() == false) {
            hoursWheel.apply {
                isVisible = true
                minValue = 1
                maxValue = dayData.hourlyData.size
                displayedValues = dayData.hourlyData.map { it.hour }.toTypedArray()
            }
        } else {
            hoursWheel.isGone = true
        }
    }
}