package com.keerthi.interviewassignment.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.keerthi.interviewassignment.R
import com.keerthi.interviewassignment.databinding.ActivitySplashBinding
import com.keerthi.interviewassignment.ui.weather.WeatherActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        mBinding.lavWeather.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                mBinding.tvTitle.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(Intent(this@SplashActivity, WeatherActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
    }
}