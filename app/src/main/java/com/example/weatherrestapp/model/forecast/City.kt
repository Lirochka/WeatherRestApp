package com.example.weatherrestapp.model.forecast

import com.example.weatherrestapp.model.Coord
data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)