package com.example.zooplsdemo.ui.networkadapter.models

import com.example.zooplsdemo.ui.utils.Constants
import com.google.gson.annotations.SerializedName

data class AirPollutionData(@SerializedName("list") val components: List<ComponentList> = arrayListOf())

data class ComponentList(@SerializedName("components") val component: Component = Component())

data class Component(
    val co: String = Constants.DATA_NOT_AVAILABLE,
    val no: String = Constants.DATA_NOT_AVAILABLE,
    val no2: String = Constants.DATA_NOT_AVAILABLE,
    @SerializedName("o3") val ozone: String = Constants.DATA_NOT_AVAILABLE,
    val so2: String = Constants.DATA_NOT_AVAILABLE,
    @SerializedName("pm2_5") val pm2to5: String = Constants.DATA_NOT_AVAILABLE,
    val pm10: String = Constants.DATA_NOT_AVAILABLE,
    val nh3: String = Constants.DATA_NOT_AVAILABLE,
    var progressVisibility: Boolean = false
)


