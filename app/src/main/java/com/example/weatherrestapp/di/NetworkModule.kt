package com.example.weatherrestapp.di

import com.example.weatherrestapp.network.RetrofitHelper
import com.example.weatherrestapp.network.WeatherApi
import com.example.weatherrestapp.repository.WeatherRepository
import com.example.weatherrestapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi) : WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }
    @Provides
    fun provideRequestsApi(): WeatherApi {
        return RetrofitHelper.getInstance().create(WeatherApi::class.java)
    }
}