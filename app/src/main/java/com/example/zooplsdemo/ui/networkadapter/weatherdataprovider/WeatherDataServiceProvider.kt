package com.example.zooplsdemo.ui.networkadapter.weatherdataprovider

import com.example.zooplsdemo.BuildConfig
import com.example.zooplsdemo.ui.networkadapter.models.AirPollutionData
import com.example.zooplsdemo.ui.networkadapter.models.CurrentWeatherData
import com.example.zooplsdemo.ui.networkadapter.models.ForecastWeatherData
import com.example.zooplsdemo.ui.networkadapter.retrofit.WeatherApiProvider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataServiceProvider {

    @GET(WeatherApiProvider.API_FETCH_CURRENT_WEATHER)
    suspend fun fetchCurrentWeatherData(
        @Query(NetworkParams.LATITUDE) latitude: Double = NetworkParams.LATITUDE_VALUE,
        @Query(NetworkParams.LONGITUDE) longitude: Double = NetworkParams.LONGITUDE_VALUE,
        @Query(NetworkParams.APP_ID) apiKey: String = BuildConfig.API_KEY
    ): Response<CurrentWeatherData>

    @GET(WeatherApiProvider.API_FORECAST_WEATHER_DATA)
    suspend fun fetchForecastWeatherData(
        @Query(NetworkParams.LATITUDE) latitude: Double = NetworkParams.LATITUDE_VALUE,
        @Query(NetworkParams.LONGITUDE) longitude: Double = NetworkParams.LONGITUDE_VALUE,
        @Query(NetworkParams.APP_ID) apiKey: String = BuildConfig.API_KEY
    ): Response<ForecastWeatherData>

    @GET(WeatherApiProvider.API_AIR_POLLUTION_DATA)
    suspend fun fetchAirPollution(
        @Query(NetworkParams.LATITUDE) latitude: Double = NetworkParams.LATITUDE_VALUE,
        @Query(NetworkParams.LONGITUDE) longitude: Double = NetworkParams.LONGITUDE_VALUE,
        @Query(NetworkParams.APP_ID) apiKey: String = BuildConfig.AIR_POLLUTION_KEY
    ): Response<AirPollutionData>
}