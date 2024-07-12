package com.example.zooplsdemo.ui.networkadapter.models

import com.google.gson.annotations.SerializedName

data class ForecastWeatherData(
    val city: City = City(),
    @SerializedName("list") val forecastData: List<ContentData> = mutableListOf()
)

data class City(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val population: String = "",
    val timezone: String = "",
    val sunrise: String = "",
    val sunset: String = ""
)

data class SystemData(val pod: String = "")

data class ContentData(
    val main: MainData,
    val weather: List<Weather> = mutableListOf(),
    val clouds: Cloud,
    val wind: Wind,
    val visibility: String = "",
    val pop: String = "",
    @SerializedName("dt") val dateTime: String = "",
    @SerializedName("sys") val system: SystemData,
    @SerializedName("dt_txt") val dateText: String = ""
)