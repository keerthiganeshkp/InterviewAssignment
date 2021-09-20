package com.keerthi.interviewassignment.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keerthi.interviewassignment.ui.weather.WeatherInfoRepository
import com.keerthi.interviewassignment.ui.weather.WeatherInfoViewModel

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory(private val repository: SafeApiRequest) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WeatherInfoViewModel::class.java) -> WeatherInfoViewModel(
                repository as WeatherInfoRepository
            ) as T
            else -> throw IllegalArgumentException("View Model Not Found")
        }
    }
}