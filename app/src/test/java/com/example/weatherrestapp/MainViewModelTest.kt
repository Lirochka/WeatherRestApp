package com.example.weatherrestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherrestapp.model.currentweather.WeatherResult
import com.example.weatherrestapp.model.forecast.City
import com.example.weatherrestapp.model.Coord
import com.example.weatherrestapp.model.forecast.FiveDayForecast
import com.example.weatherrestapp.repository.WeatherRepository
import com.example.weatherrestapp.viewmodel.MainViewModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val weatherRepositoryMock: WeatherRepository = mock()

    private lateinit var subject: MainViewModel

    private val cityMock: String = "London"
    private val latFake: Double = 1.0
    private val lonFake: Double = 1.0
    private val coordFake : Coord = Coord(latFake, lonFake)

    private val weatherResultFake : WeatherResult = WeatherResult("mainTest", "descriptionTest", 14.5, 45, 52, 3.3)

    private val fiveDayForecastFake : FiveDayForecast = FiveDayForecast(
        City(Coord(1.2,1.2), "Country", 1, "name", 100, 200, 150, 102),
    100, "codTest", emptyList(), 1)

    @Before
    fun setup(){
        subject = MainViewModel(weatherRepositoryMock)
    }

    @Test
    fun getCoordinates_success() = runBlocking {
        `when`(weatherRepositoryMock.getLocationCoordinates(cityMock)).thenReturn(coordFake)
        subject.getCoordinates(cityMock)
        val expectedResult = subject.coordinatesResult.value
        TestCase.assertEquals(coordFake, expectedResult)
    }

    @Test
    fun getCurrentWeather_success() = runBlocking {
        `when`(weatherRepositoryMock.getCurrentWeather(latFake, lonFake)).thenReturn(weatherResultFake)

        subject.getCurrentWeather(latFake, lonFake)
        val expectedResult = subject.currentWeatherResult.value
        TestCase.assertEquals(weatherResultFake, expectedResult)
    }

    @Test
    fun getForecast_success() = runBlocking {
        `when`(weatherRepositoryMock.getForecast(latFake, lonFake)).thenReturn(fiveDayForecastFake)

        subject.getForecast(latFake, lonFake)
        val expectedResult = subject.forecastResult.value
        TestCase.assertEquals(fiveDayForecastFake, expectedResult)
    }
}