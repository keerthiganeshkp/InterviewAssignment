package com.keerthi.interviewassignment.ui.weather

import android.content.Context
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.keerthi.interviewassignment.remote.ApiClient
import com.keerthi.interviewassignment.util.SafeApiRequest

class WeatherInfoRepository(private val context: Context) : SafeApiRequest() {

    suspend fun getWeatherInfo(
        cityId: Int
    ): WeatherInfoResponse {
        return apiRequest { ApiClient.retrofit.callApiForWeatherInfo(cityId) }
    }
}