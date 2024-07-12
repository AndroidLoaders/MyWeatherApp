package com.example.zooplsdemo.ui.networkadapter.mappers

import com.example.zooplsdemo.ui.networkadapter.models.ForecastWeatherData
import com.example.zooplsdemo.ui.ui.home.model.ForecastWeather
import com.example.zooplsdemo.ui.ui.home.model.ForecastWeatherDetails
import javax.inject.Inject

class ForecastWeatherDataMapper @Inject constructor() :
    Mapper<ForecastWeatherData, ForecastWeatherDetails> {

    override fun mapTo(from: ForecastWeatherData): ForecastWeatherDetails {
        val forecastWeatherList: MutableList<ForecastWeather> = mutableListOf()
        from.forecastData.map { weather ->
            val dateTime = weather.dateText
            val weatherType = if (weather.weather.isNotEmpty()) weather.weather[0].main else ""
            val forecastWeather = ForecastWeather(dateTime = dateTime, weatherType = weatherType)
            forecastWeatherList.add(forecastWeather)
        }
        return ForecastWeatherDetails(weatherData = forecastWeatherList)
    }
}