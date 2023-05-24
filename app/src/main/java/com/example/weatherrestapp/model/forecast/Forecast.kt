package com.example.weatherrestapp.model.forecast

import com.example.weatherrestapp.model.Clouds
import com.example.weatherrestapp.model.Main
import com.example.weatherrestapp.model.SysForecast
import com.example.weatherrestapp.model.Weather
import com.example.weatherrestapp.model.Wind
data class Forecast(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sysForecast: SysForecast,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)