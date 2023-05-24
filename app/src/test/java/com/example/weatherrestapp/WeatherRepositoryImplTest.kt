package com.example.weatherrestapp

import com.example.weatherrestapp.model.Clouds
import com.example.weatherrestapp.model.Coord
import com.example.weatherrestapp.model.Main
import com.example.weatherrestapp.model.SysForecast
import com.example.weatherrestapp.model.currentweather.CurrentWeather
import com.example.weatherrestapp.model.currentweather.Sys
import com.example.weatherrestapp.model.Weather
import com.example.weatherrestapp.model.Wind
import com.example.weatherrestapp.model.forecast.City
import com.example.weatherrestapp.model.forecast.FiveDayForecast
import com.example.weatherrestapp.model.forecast.Forecast
import com.example.weatherrestapp.model.forecast.Rain
import com.example.weatherrestapp.model.geocoding.Geocoding
import com.example.weatherrestapp.model.geocoding.GeocodingItem
import com.example.weatherrestapp.network.WeatherApi
import com.example.weatherrestapp.repository.WeatherRepositoryImpl
import com.example.weatherrestapp.repository.WeatherRepositoryImpl.Companion.APP_ID
import com.example.weatherrestapp.repository.WeatherRepositoryImpl.Companion.LIMIT
import com.example.weatherrestapp.repository.WeatherRepositoryImpl.Companion.METRIC
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class WeatherRepositoryImplTest {
    private lateinit var subject: WeatherRepositoryImpl

    private val weatherApiMock: WeatherApi = mock()

    private val lat = 1.0
    private val lon = 1.0

    @Before
    fun setup() {
        subject = WeatherRepositoryImpl(weatherApiMock)
    }

    @Test
    fun getLocationCoordinates_success(): Unit = runBlocking {
        val cityMock = "London"
        val expectedCoordinates = Coord(37.7749, -122.4194)
        val location = Geocoding()
        location.add(GeocodingItem("USA", 37.7749, null, -122.4194, "San Francisco"))
        location.add(GeocodingItem("USA", 40.7128, null, -74.0060, "New York"))
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getGeocoding(cityMock, LIMIT, APP_ID)).thenReturn(mockResponse)

        val result = subject.getLocationCoordinates(cityMock)

        assertEquals(expectedCoordinates.lat, result.lat)
        assertEquals(expectedCoordinates.lon, result.lon)
    }

    @Test
    fun getCurrentWeather_success(): Unit = runBlocking {
        val location = CurrentWeather(
            "base", Clouds(1), 1, Coord(lat, lon), 1, 1,
            Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0),
            "name", Sys("country", 1, 1, 1, 1), 1, 1,
            listOf(Weather("description", "icon", 1, "main")),
            Wind(1, 1.0, 1.0)
        )
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getCurrentWeather(lat, lon, APP_ID, METRIC)).thenReturn(mockResponse)

        val result = subject.getCurrentWeather(lat, lon)

        assertEquals("main", result.main)
        assertEquals("description", result.description)
    }

    @Test
    fun getForecast_success(): Unit = runBlocking {
        val location = FiveDayForecast(
            City(Coord(lat, lon), "San Francisco", 123, "US", 100, 200, 150, 5),
            1, "codTest", listOf(
                Forecast(
                    Clouds(1), 1, "dt_txt", Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0, 1.0),
                    1.0, Rain(1.0), SysForecast("podString"), 1, listOf(
                        Weather("description", "icon", 1, "main")
                    ),
                    Wind(1, 1.0, 1.0)
                )
            ), 0
        )
        val mockResponse = Response.success(location)
        `when`(weatherApiMock.getForecast(lat, lon, APP_ID, METRIC)).thenReturn(mockResponse)

        val result = subject.getForecast(lat, lon)

        assertEquals(location, result)
    }
}