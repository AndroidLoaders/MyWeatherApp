package com.example.zooplsdemo.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zooplsdemo.ui.networkadapter.ResponseData
import com.example.zooplsdemo.ui.networkadapter.mappers.ForecastWeatherDataMapper
import com.example.zooplsdemo.ui.networkadapter.models.CurrentWeatherData
import com.example.zooplsdemo.ui.networkadapter.models.ForecastWeatherData
import com.example.zooplsdemo.ui.repository.NETWORK_STATUS_404
import com.example.zooplsdemo.ui.repository.WeatherRepository
import com.example.zooplsdemo.ui.ui.home.model.ForecastWeatherDetails
import com.example.zooplsdemo.ui.ui.home.model.WeatherDetails
import com.example.zooplsdemo.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val mapper: ForecastWeatherDataMapper
) : ViewModel() {

    private val weatherDataLoader by lazy { WeatherDetails(progressVisibility = true) }

    private val _weatherData: MutableLiveData<WeatherDetails> = MutableLiveData()
    val weatherData: LiveData<WeatherDetails> = _weatherData

    private val forecastDataLoader by lazy { ForecastWeatherDetails(progressVisibility = true) }

    private val _forecastWeatherData: MutableLiveData<ForecastWeatherDetails> = MutableLiveData()
    val forecastWeatherData: LiveData<ForecastWeatherDetails> = _forecastWeatherData

    private fun onSuccessWeatherDetails(currentWeather: CurrentWeatherData?) {
        _weatherData.value = currentWeather?.weatherData?.let {
            weatherDataLoader.apply {
                progressVisibility = false
                weatherType = if (it.isNotEmpty()) "${it[0].main}\n(${it[0].description})"
                else Constants.DATA_NOT_AVAILABLE
            }
        } ?: weatherDataLoader.apply {
            progressVisibility = false
            weatherType = Constants.DATA_NOT_AVAILABLE
        }
    }

    private fun onErrorWeatherDetails() {
        _weatherData.value = weatherDataLoader.apply {
            progressVisibility = false
            weatherType = Constants.DATA_NOT_AVAILABLE
        }
    }

    private suspend fun prepareCurrentWeatherData(response: ResponseData<CurrentWeatherData>) {
        withContext(Dispatchers.Main) {
            try {
                when (response) {
                    is ResponseData.Success -> onSuccessWeatherDetails(response.responseData)
                    is ResponseData.Error -> onErrorWeatherDetails()
                }
            } catch (exception: Exception) {
                onErrorWeatherDetails()
                println("TAG -- HomeViewModel --> ${exception.printStackTrace()}")
            }
        }
    }

    private suspend fun prepareForecastWeatherData(response: ResponseData<ForecastWeatherData>) {
        withContext(Dispatchers.Main) {
            try {
                when (response) {
                    is ResponseData.Success -> {
                        _forecastWeatherData.value =
                            response.responseData?.let { forecastWeatherData ->
                                mapper.mapTo(forecastWeatherData)
                            } ?: forecastDataLoader.apply {
                                progressVisibility = false
                                responseCode = NETWORK_STATUS_404
                            }
                    }

                    is ResponseData.Error -> {
                        _forecastWeatherData.value = forecastDataLoader.apply {
                            progressVisibility = false
                            responseCode = response.errorCode
                        }
                        println("TAG --> ResponseData.Error --> ${response.errorMessage}")
                    }
                }
            } catch (exception: Exception) {
                _forecastWeatherData.value = forecastDataLoader.apply {
                    progressVisibility = false
                    responseCode = NETWORK_STATUS_404
                }
                println("TAG -- HomeViewModel --> ${exception.printStackTrace()}")
            }
        }
    }

    private suspend fun fetchCurrentWeather(weatherData: Deferred<ResponseData<CurrentWeatherData>>) {
        try {
            prepareCurrentWeatherData(weatherData.await())
        } catch (exception: Exception) {
            withContext(Dispatchers.Main) {
                _weatherData.value = weatherDataLoader.copy(progressVisibility = false)
            }
            println("TAG -- HomeViewModel --> ${exception.printStackTrace()}")
        }
    }

    private suspend fun fetchForecastWeather(forecastWeatherData: Deferred<ResponseData<ForecastWeatherData>>) {
        try {
            prepareForecastWeatherData(forecastWeatherData.await())
        } catch (exception: Exception) {
            withContext(Dispatchers.Main) {
                _forecastWeatherData.value = forecastDataLoader.apply {
                    progressVisibility = false
                    responseCode = NETWORK_STATUS_404
                }
            }
            println("TAG -- HomeViewModel --> ${exception.printStackTrace()}")
        }
    }

    suspend fun manageNetworkCall() {
        _weatherData.value = weatherDataLoader
        _forecastWeatherData.value = forecastDataLoader
        viewModelScope.launch(Dispatchers.IO) {
            supervisorScope {
                val weatherData = async { repository.fetchCurrentWeatherData() }
                fetchCurrentWeather(weatherData)

                val forecastWeatherData = async { repository.fetchForecastWeatherData() }
                fetchForecastWeather(forecastWeatherData)
            }
        }
    }
}