package com.example.zooplsdemo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.zooplsdemo.databinding.ListItemLayoutBinding
import com.example.zooplsdemo.ui.ui.home.model.ForecastWeather
import com.example.zooplsdemo.ui.utils.DateTimeUtils

open class WeatherDataAdapter(private val forecastWeatherData: List<ForecastWeather>) :
    RecyclerView.Adapter<WeatherDataAdapter.WeatherDataHolder>() {

    inner class WeatherDataHolder(val binding: ListItemLayoutBinding) : ViewHolder(binding.root) {
        fun bindData(weatherType: String) {
            binding.weatherType = weatherType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataHolder {
        val holderView =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherDataHolder(holderView)
    }

    override fun getItemCount(): Int = forecastWeatherData.size

    override fun onBindViewHolder(holder: WeatherDataHolder, position: Int) {
        val recentWeather = forecastWeatherData[position]
        holder.binding.apply {
            when (position) {
                0 -> {
                    with(this.tvDate) {
                        this.visibility = View.VISIBLE
                        this.text = DateTimeUtils.formatDateTime(
                            dateTimeString = recentWeather.dateTime,
                            inputFormat = DateTimeUtils.INPUT_DATE_TIME_FORMAT,
                            outputFormat = DateTimeUtils.OUTPUT_DATE_FORMAT
                        )
                    }
                    holder.binding.tvTime.visibility = View.GONE
                    holder.binding.tvWeatherType.visibility = View.GONE
                }

                else -> {
                    holder.bindData(recentWeather.weatherType)

                    val previousWeather = forecastWeatherData[position - 1]
                    val previousDateTime = DateTimeUtils.formatDateTime(
                        dateTimeString = previousWeather.dateTime,
                        inputFormat = DateTimeUtils.INPUT_DATE_TIME_FORMAT,
                        outputFormat = DateTimeUtils.OUTPUT_DATE_FORMAT
                    )
                    val recentDateTime = DateTimeUtils.formatDateTime(
                        dateTimeString = recentWeather.dateTime,
                        inputFormat = DateTimeUtils.INPUT_DATE_TIME_FORMAT,
                        outputFormat = DateTimeUtils.OUTPUT_DATE_FORMAT
                    )

                    with(this.tvDate) {
                        visibility = if (previousDateTime == recentDateTime) View.GONE
                        else {
                            this.text = DateTimeUtils.formatDateTime(
                                dateTimeString = recentWeather.dateTime,
                                inputFormat = DateTimeUtils.INPUT_DATE_TIME_FORMAT,
                                outputFormat = DateTimeUtils.OUTPUT_DATE_FORMAT
                            )
                            View.VISIBLE
                        }
                    }

                    this.tvTime.text = DateTimeUtils.formatDateTime(
                        dateTimeString = recentWeather.dateTime,
                        inputFormat = DateTimeUtils.INPUT_DATE_TIME_FORMAT,
                        outputFormat = DateTimeUtils.OUTPUT_TIME_FORMAT
                    )
                    this.tvWeatherType.visibility = View.VISIBLE
                }
            }
        }
    }
}