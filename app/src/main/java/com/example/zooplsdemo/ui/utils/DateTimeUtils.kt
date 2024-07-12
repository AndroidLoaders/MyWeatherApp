package com.example.zooplsdemo.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {

    const val INPUT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val OUTPUT_DATE_FORMAT = "dd/MM/yyyy"
    const val OUTPUT_TIME_FORMAT = "HH:mm a"

    fun fetchCurrentDate(): String {
        val currentDateTime: Date = Calendar.getInstance().time

        var currentDate = ""
        try {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            currentDate = dateFormat.format(currentDateTime)
        } catch (exception: Exception) {
            println("TAG -- ${this::class.simpleName} --> $exception")
        }

        return currentDate
    }

    fun formatDateTime(dateTimeString: String, inputFormat: String, outputFormat: String): String {
        if (dateTimeString.isEmpty() || inputFormat.isEmpty() || outputFormat.isEmpty()) return Constants.DATA_NOT_AVAILABLE

        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

        return try {
            inputDateFormat.parse(dateTimeString)?.let {
                outputDateFormat.format(it)
            } ?: Constants.DATA_NOT_AVAILABLE
        } catch (e: java.lang.Exception) {
            return Constants.DATA_NOT_AVAILABLE
        }
    }
}