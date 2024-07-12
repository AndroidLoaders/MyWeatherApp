package com.example.zooplsdemo.ui.networkadapter.retrofit

import com.example.zooplsdemo.BuildConfig

object WeatherApiProvider {

    private const val BASE_URL: String = "${BuildConfig.BASE_URL}data/2.5/"

    private const val GET_FETCH_CURRENT_WEATHER = "weather"
    const val API_FETCH_CURRENT_WEATHER = BASE_URL + GET_FETCH_CURRENT_WEATHER

    private const val GET_FORECAST_WEATHER_DATA = "forecast"
    const val API_FORECAST_WEATHER_DATA = BASE_URL + GET_FORECAST_WEATHER_DATA

    private const val GET_AIR_POLLUTION_DATA = "air_pollution"
    const val API_AIR_POLLUTION_DATA = BASE_URL + GET_AIR_POLLUTION_DATA
}