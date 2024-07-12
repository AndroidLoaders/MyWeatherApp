package com.example.zooplsdemo.ui.ui.home.model

import com.example.zooplsdemo.ui.repository.NETWORK_STATUS_SUCCESS
import com.example.zooplsdemo.ui.utils.Constants
import com.example.zooplsdemo.ui.utils.DateTimeUtils

data class WeatherDetails(
    var progressVisibility: Boolean = false,
    val currentDate: String = DateTimeUtils.fetchCurrentDate(),
    var weatherType: String = Constants.DATA_NOT_AVAILABLE
)

data class ForecastWeatherDetails(
    var progressVisibility: Boolean = false,
    val weatherData: List<ForecastWeather> = mutableListOf(),
    var responseCode: Int = NETWORK_STATUS_SUCCESS
)

data class ForecastWeather(
    val dateTime: String,
    val weatherType: String
)