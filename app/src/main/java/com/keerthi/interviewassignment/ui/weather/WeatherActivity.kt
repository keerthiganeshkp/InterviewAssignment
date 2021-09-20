package com.keerthi.interviewassignment.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hellohasan.weatherappmvvm.features.weather_info_show.model.data_class.WeatherData
import com.keerthi.interviewassignment.R
import com.keerthi.interviewassignment.databinding.ActivityWeatherBinding
import com.keerthi.interviewassignment.util.BaseViewModelFactory
import com.keerthi.interviewassignment.util.MyWorker
import java.util.concurrent.TimeUnit


class WeatherActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWeatherBinding
    private lateinit var mWeatherInfoViewModel: WeatherInfoViewModel
    private val sharedPrefFile = "sharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    var workRequest: PeriodicWorkRequest? = null
    var mWorkManager: WorkManager? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BaseViewModelFactory(WeatherInfoRepository(applicationContext))
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        mWeatherInfoViewModel =
            ViewModelProvider(this, factory).get(WeatherInfoViewModel::class.java)


        mWorkManager = WorkManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            workRequest =
                PeriodicWorkRequest.Builder(MyWorker::class.java, 2, TimeUnit.HOURS).build()
        }
        setLiveDataListeners()
        setViewClickListener(workRequest!!)

         sharedPreferences = this.getSharedPreferences(
             sharedPrefFile,
             Context.MODE_PRIVATE
         )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ServiceCast", "NewApi")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
            }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setViewClickListener(workRequest: PeriodicWorkRequest) {
        mBinding.layoutInput.btnViewWeather.setOnClickListener {
            val isFirstValue = sharedPreferences.getString("FirstTime", "default")
            if(isFirstValue!= null && isFirstValue == "no"){

                val gson = Gson()
                val json: String? = sharedPreferences.getString("weatherData", "")
                if(json != "") {
                    val weatherData: WeatherData = gson.fromJson(json, WeatherData::class.java)
                    setWeatherInfo(weatherData)
                } else {
                    Toast.makeText(applicationContext,"Connect to Wifi for Weather Updates",Toast.LENGTH_SHORT).show()
                }

                val isWifiAvailable = isOnline(applicationContext)
                if(isWifiAvailable) {
                    mWeatherInfoViewModel.getWeatherInfo(1261481)
                    mWorkManager?.enqueue(workRequest)
                } else {
                    Toast.makeText(applicationContext,"Connect to Wifi for Frequent Weather Updates",Toast.LENGTH_SHORT).show()
                }
            } else {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("FirstTime", "no")
                editor.apply()
                editor.commit()
                val isWifiAvailable = isOnline(applicationContext)
                if(isWifiAvailable) {
                    mWeatherInfoViewModel.getWeatherInfo(1261481)
                } else {
                    Toast.makeText(applicationContext,"Connect to Wifi for Frequent Weather Updates",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getWeatherData(){
        mWeatherInfoViewModel.getWeatherInfo(1261481)
    }

    private fun setLiveDataListeners() {
        mWeatherInfoViewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            setWeatherInfo(weatherData)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            val weatherDataString: String = gson.toJson(weatherData)
            editor.putString("weatherData", weatherDataString)
            editor.apply()
            editor.commit()
        })
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        mBinding.outputGroup.visibility = View.VISIBLE
        mBinding.tvErrorMessage.visibility = View.GONE

        mBinding.layoutWeatherBasic.tvDateTime.text = weatherData.dateTime
        mBinding.layoutWeatherBasic.tvTemperature.text = weatherData.temperature
        mBinding.layoutWeatherBasic.tvCityCountry.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl)
            .into(mBinding.layoutWeatherBasic.ivWeatherCondition)
        mBinding.layoutWeatherBasic.tvWeatherCondition.text =
            weatherData.weatherConditionIconDescription

        mBinding.layoutWeatherAdditional.tvHumidityValue.text = weatherData.humidity
        mBinding.layoutWeatherAdditional.tvPressureValue.text = weatherData.pressure
        mBinding.layoutWeatherAdditional.tvVisibilityValue.text = weatherData.visibility

        mBinding.layoutSunsetSunrise.tvSunriseTime.text = weatherData.sunrise
        mBinding.layoutSunsetSunrise.tvSunsetTime.text = weatherData.sunset
    }
}