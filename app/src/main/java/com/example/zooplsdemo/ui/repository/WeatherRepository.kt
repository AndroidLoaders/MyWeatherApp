package com.example.zooplsdemo.ui.repository

import com.example.zooplsdemo.ui.networkadapter.ResponseData
import com.example.zooplsdemo.ui.networkadapter.models.AirPollutionData
import com.example.zooplsdemo.ui.networkadapter.models.CurrentWeatherData
import com.example.zooplsdemo.ui.networkadapter.models.ForecastWeatherData
import com.example.zooplsdemo.ui.networkadapter.weatherdataprovider.WeatherDataServiceProvider
import retrofit2.Response
import javax.inject.Inject

const val NETWORK_STATUS_SUCCESS: Int = 200
const val NETWORK_STATUS_404: Int = 404

interface WeatherRepository {
    suspend fun fetchCurrentWeatherData(): ResponseData<CurrentWeatherData>
    suspend fun fetchForecastWeatherData(): ResponseData<ForecastWeatherData>
    suspend fun fetchAirPollution(): ResponseData<AirPollutionData>
}

class WeatherRepositoryImpl @Inject constructor(private val apiService: WeatherDataServiceProvider) :
    WeatherRepository {

    override suspend fun fetchCurrentWeatherData(): ResponseData<CurrentWeatherData> {
        val response = apiService.fetchCurrentWeatherData()
        return responseConverter(response)
        //throw IllegalArgumentException("Testing SupervisorScope")
    }

    override suspend fun fetchForecastWeatherData(): ResponseData<ForecastWeatherData> {
        val response = apiService.fetchForecastWeatherData()
        return responseConverter(response)
    }

    override suspend fun fetchAirPollution(): ResponseData<AirPollutionData> {
        val response = apiService.fetchAirPollution()
        return responseConverter(response)
    }

    private fun <Data> responseConverter(response: Response<Data>): ResponseData<Data> =
        if (response.isSuccessful && response.code() == NETWORK_STATUS_SUCCESS)
            ResponseData.Success(responseData = response.body(), responseCode = response.code())
        else
            ResponseData.Error(errorMessage = response.message(), errorCode = response.code())
}