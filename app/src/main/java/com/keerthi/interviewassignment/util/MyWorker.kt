package com.keerthi.interviewassignment.util

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.keerthi.interviewassignment.ui.weather.WeatherActivity

class MyWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {

        var weatherActivity: WeatherActivity = WeatherActivity()
        weatherActivity.getWeatherData()
        val outputData = Data.Builder().putString(WORK_RESULT, "Jobs Finished").build()
        return Result.success(outputData)
    }

    companion object {
        private const val WORK_RESULT = "work_result"
    }
}