package com.sparrow.weatherapp.presentation.loadlocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sparrow.weatherapp.R
import com.sparrow.weatherapp.databinding.FragmentLoadLocationBinding
import com.sparrow.weatherapp.frameworks.android.hideKeyboard
import com.sparrow.weatherapp.frameworks.android.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadLocationFragment : Fragment() {

    private val viewModel by activityViewModels<WeatherDataViewModel>()

    private lateinit var binding: FragmentLoadLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoadLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModelChanges()
        searchAction()
    }

    private fun searchAction() = with(binding) {
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

    private fun observeViewModelChanges() = with(viewModel) {
        observe(loading, ::onLoadingChange)
        observe(navigate, ::onNavigationRequested)
        observe(error, ::onError)
    }

    private fun onError(errorStringID: Int?) {
        binding.statusText.setText(errorStringID ?: R.string.enter_you_location_to_load_weather)
    }

    private fun onNavigationRequested(navigate: Boolean) {
        if (navigate) {
            findNavController().navigate(R.id.toWeatherFragment)
        }
    }

    private fun onLoadingChange(loading: Boolean) {
        binding.apply {
            if (loading) {
                loadingAnimationView.playAnimation()
                statusText.setText(R.string.loading)
            } else {
                loadingAnimationView.cancelAnimation()
                statusText.setText(R.string.enter_you_location_to_load_weather)
            }
        }
    }
}