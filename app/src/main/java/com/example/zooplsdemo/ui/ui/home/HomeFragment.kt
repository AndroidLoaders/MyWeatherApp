package com.example.zooplsdemo.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.zooplsdemo.R
import com.example.zooplsdemo.databinding.FragmentHomeBinding
import com.example.zooplsdemo.ui.adapters.WeatherDataAdapter
import com.example.zooplsdemo.ui.repository.NETWORK_STATUS_SUCCESS
import com.example.zooplsdemo.ui.ui.home.model.ForecastWeather
import com.example.zooplsdemo.ui.utils.SortAscendingComparator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Collections

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            this.viewModel = homeViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.llCurrentWeather.setOnClickListener {
            findNavController().navigate(resId = R.id.actionHomeFragmentToAirQualityDetails)
        }

        observeData()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.manageNetworkCall()
            }
        }
    }

    private fun observeData() {
        homeViewModel.forecastWeatherData.observe(viewLifecycleOwner) { forecastWeather ->
            when (forecastWeather.responseCode) {
                NETWORK_STATUS_SUCCESS -> loadForecastWeatherData(forecastWeather.weatherData)
                else -> Toast.makeText(requireContext(), R.string.error_message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadForecastWeatherData(weatherData: List<ForecastWeather>) {
        if (weatherData.isNotEmpty()) {
            Collections.sort(weatherData, SortAscendingComparator())
            binding.rvWeatherTimeList.adapter = WeatherDataAdapter(weatherData)
        }
    }
}