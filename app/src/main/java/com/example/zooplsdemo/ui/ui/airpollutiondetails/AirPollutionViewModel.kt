package com.example.zooplsdemo.ui.ui.airpollutiondetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zooplsdemo.ui.networkadapter.ResponseData
import com.example.zooplsdemo.ui.networkadapter.models.AirPollutionData
import com.example.zooplsdemo.ui.networkadapter.models.Component
import com.example.zooplsdemo.ui.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AirPollutionViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _airPollutionData: MutableLiveData<Component> = MutableLiveData()
    val airPollutionData: LiveData<Component> = _airPollutionData

    private val _onError: MutableLiveData<Unit> = MutableLiveData()
    val onError: LiveData<Unit> = _onError

    suspend fun fetchAirPollution() {
        val component = Component(progressVisibility = true)
        _airPollutionData.value = component
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response = repository.fetchAirPollution()) {
                    is ResponseData.Success -> onSuccess(response.responseData, component)
                    is ResponseData.Error -> onError(component)
                }
            } catch (exception: Exception) {
                onError(component)
                println("TAG -- AirPollutionViewModel --> ${exception.printStackTrace()}")
            }
        }
    }

    private suspend fun onSuccess(response: AirPollutionData?, component: Component) {
        withContext(Dispatchers.Main) {
            _airPollutionData.value = response?.let { airPollution ->
                if (airPollution.components.isNotEmpty()) airPollution.components[0].component
                else component.apply { progressVisibility = false }
            } ?: component.apply { progressVisibility = false }
        }
    }

    private suspend fun onError(component: Component) {
        withContext(Dispatchers.Main) {
            _airPollutionData.value = component.apply { progressVisibility = false }
            _onError.value = Unit
        }
    }
}