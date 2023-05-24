package com.example.weatherrestapp.repository

import com.example.weatherrestapp.model.currentweather.WeatherResult
import com.example.weatherrestapp.model.forecast.Coord
import com.example.weatherrestapp.model.forecast.FiveDayForecast


/**
 * Provides API connection with https://api.openweathermap.org/
 */
interface WeatherRepository {

    /**
     * Getting location info lat and lon
     */
    suspend fun getLocationCoordinates(city: String): Coord

    /**
     * Getting current weather for specific place by provide lat and lon
     */
    suspend fun getCurrentWeather(lat: Double, lon: Double) : WeatherResult

    /**
     * Getting forecast for specific place by provide lat and lon
     */
    suspend fun getForecast(lat:Double, lon: Double): FiveDayForecast
}