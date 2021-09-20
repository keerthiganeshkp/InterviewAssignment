package com.keerthi.interviewassignment.ui.weather

import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.WeatherInfoResponse

sealed class UiState {
    object Loading : UiState()
    sealed class Success : UiState() {
        data class WeatherInfo(val result: WeatherInfoResponse) : Success()
    }

    data class Error(val message: String) : UiState()
}