package com.example.zooplsdemo.ui.networkadapter.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherData(
    val id: String,
    @SerializedName("cod") val statusCode: String,
    val name: String,
    val base: String,
    @SerializedName("timezone") val timeZone: String,
    @SerializedName("dt") val dateTimeMillis: String,
    val visibility: String,
    @SerializedName("main") val mainData: MainData,
    val wind: Wind,
    val cloud: Cloud,
    @SerializedName("sys") val system: System,
    @SerializedName("weather") val weatherData: List<Weather>,
)

data class Weather(val id: String, val main: String, val description: String, val icon: String)

data class MainData(
    val pressure: String,
    val humidity: String,
    @SerializedName("temp") val temperature: String,
    @SerializedName("feels_like") val feelsLike: String,
    @SerializedName("temp_min") val minimumTemperature: String,
    @SerializedName("temp_max") val maximumTemperature: String,
    @SerializedName("sea_level") val seaLevel: String,
    @SerializedName("grnd_level") val groundLevel: String,
)

data class Wind(val speed: String, val deg: String, val gust: String)

data class Cloud(val all: String)

data class System(
    val id: String,
    val country: String,
    val type: String,
    @SerializedName("sunrise") val sunRise: String,
    @SerializedName("sunset") val sunSet: String
)