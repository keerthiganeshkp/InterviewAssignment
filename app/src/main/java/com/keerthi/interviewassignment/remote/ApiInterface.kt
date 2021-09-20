package com.keerthi.interviewassignment.remote

import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    suspend fun callApiForWeatherInfo(
        @Query("id") cityId: Int
    ): Response<WeatherInfoResponse>
}