package com.example.zooplsdemo.ui.utils

import com.example.zooplsdemo.ui.ui.home.model.ForecastWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SortAscendingComparator : Comparator<ForecastWeather> {
    override fun compare(weather1: ForecastWeather, weather2: ForecastWeather): Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            val date1: Date? = formatter.parse(weather1.dateTime)
            val date2: Date? = formatter.parse(weather2.dateTime)
            if ((date1 != null) && (date2 != null)) date1.compareTo(date2)
            else 0
        } catch (exception: Exception) {
            println("TAG -- Sorting --> ${exception.printStackTrace()}")
            0
        }
    }
}