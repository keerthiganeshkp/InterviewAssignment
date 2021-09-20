package com.keerthi.interviewassignment.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.WeatherData
import com.keerthi.interviewassignment.util.*
import kotlinx.coroutines.launch

class WeatherInfoViewModel(private val mWeatherInfoRepository: WeatherInfoRepository) :
    BaseViewModel<UiState>() {

    val weatherInfoLiveData = MutableLiveData<WeatherData>()

    fun getWeatherInfo(
        cityId: Int
    ) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = mWeatherInfoRepository.getWeatherInfo(cityId)
                uiState.value = UiState.Success.WeatherInfo(response)

                val weatherData = WeatherData(
                    dateTime = response.dt.unixTimestampToDateTimeString(),
                    temperature = response.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${response.name}, ${response.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${response.weather[0].icon}.png",
                    weatherConditionIconDescription = response.weather[0].description,
                    humidity = "${response.main.humidity}%",
                    pressure = "${response.main.pressure} mBar",
                    visibility = "${response.visibility / 1000.0} KM",
                    sunrise = response.sys.sunrise.unixTimestampToTimeString(),
                    sunset = response.sys.sunset.unixTimestampToTimeString()
                )
                weatherInfoLiveData.postValue(weatherData)
            } catch (e: ApiException) {
                uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}