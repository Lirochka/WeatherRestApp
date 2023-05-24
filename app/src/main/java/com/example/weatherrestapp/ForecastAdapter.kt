package com.example.weatherrestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherrestapp.databinding.ItemForecastBinding
import com.example.weatherrestapp.model.forecast.ForecastResult
import com.example.weatherrestapp.repository.WeatherRepositoryImpl
import java.text.SimpleDateFormat
import java.util.*
class ForecastAdapter(
    private val fragmentContext: Context,
    private val weatherList: MutableList<ForecastResult>,
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemForecastBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(forecast: ForecastResult) {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())

            val date = inputDateFormat.parse(forecast.date)
            val outputDate = date?.let { outputDateFormat.format(it) }

            binding.itemRecyclerDate.text = "Date: ${outputDate}"
            binding.itemRecyclerTemp.text = "Temp: ${forecast.temp}"
            binding.itemRecyclerDescription.text = "Temp: ${forecast.description}"

            when (forecast.main) {
                WeatherRepositoryImpl.WEATHER_TYPE_CLEAR -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.clear_sky
                        ))
                }
                WeatherRepositoryImpl.WEATHER_TYPE_CLOUDS -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.cloud
                        ))
                }
                WeatherRepositoryImpl.WEATHER_TYPE_RAIN -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.heavy_rain
                        ))
                }
                WeatherRepositoryImpl.WEATHER_TYPE_THUNDERSTORM -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.thunderstorm
                        ))
                }
                WeatherRepositoryImpl.WEATHER_TYPE_SNOW -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.snow
                        ))
                }
                else -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.mist
                        ))
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, fragmentContext)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val weather = weatherList[position]
        holder.bindItem(weather)
    }
    override fun getItemCount(): Int {
        return weatherList.size
    }
}