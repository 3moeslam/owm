package com.sparrow.weatherapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sparrow.weatherapp.R
import com.sparrow.weatherapp.databinding.FragmentSettingsBinding
import com.sparrow.weatherapp.frameworks.android.changeStatusBarColor
import com.sparrow.weatherapp.frameworks.android.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.changeStatusBarColor(R.color.primrose)
        initializeLanguageSpinner()
        observe(viewModel.isSaved, ::onSettingsSaved)
    }

    private fun onSettingsSaved(isSaved: Boolean) {
        if (!isSaved) return
        activity?.onBackPressed()
    }

    private fun initializeLanguageSpinner() = with(binding.languageSpinner) {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, viewModel.supportedLanguages)
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onLanguageChanged(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}