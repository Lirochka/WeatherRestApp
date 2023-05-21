package com.example.weatherrestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherrestapp.databinding.ActivityMainBinding
import com.example.weatherrestapp.network.RetrofitHelper
import com.example.weatherrestapp.network.WeatherApi
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var binding: ActivityMainBinding

    private val appId = "9db8931477a2b163a4930c8c20ebd7d1"

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {

            val result = retrofitClient.getGeocoding("London", limit = "1", appId)

            val latResult = result.body()?.first()?.lat ?: 0.0
            val lonResult = result.body()?.first()?.lon ?: 0.0

            val currentWeather =
                retrofitClient.getCurrentWeather(latResult, lonResult, appId, "metric")
            val forecast = retrofitClient.getForecast(latResult, lonResult, appId, "metric")

            Log.d("testRetrofit", "geoCoding---> ${result.body()}")
            Log.d("testRetrofit", "currentWeather---> ${currentWeather.body()}")
            Log.d("testRetrofit", "forecast---> ${forecast.message()}")
            Log.d("testRetrofit", "forecast---> ${forecast.isSuccessful()}")
            Log.d("testRetrofit", "forecast---> ${forecast.body()}")

            withContext(Dispatchers.Main) {
            //        locationLabel.text = "Location $latResult $lonResult"
            //        currentWeatherLabel.text = currentWeather.body()?.weather?.first()?.description ?: ""
            //     forecastLabel.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""
            }
        }

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        prepareViewPager()
    }
    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            WeatherFragment.newInstance(),
            ForecastFragment.newInstance()
        )

        val tabTitlesArray = arrayOf("Weather", "ForeCast")

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitlesArray[position]
        }.attach()
    }
}