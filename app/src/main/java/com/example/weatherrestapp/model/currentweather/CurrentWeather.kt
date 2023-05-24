package com.example.weatherrestapp.model.currentweather

import com.example.weatherrestapp.model.Clouds
import com.example.weatherrestapp.model.Coord
import com.example.weatherrestapp.model.Main
import com.example.weatherrestapp.model.Weather
import com.example.weatherrestapp.model.Wind
data class CurrentWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)