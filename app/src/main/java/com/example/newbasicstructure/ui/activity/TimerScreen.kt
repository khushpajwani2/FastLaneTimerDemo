package com.example.newbasicstructure.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.newbasicstructure.CountDownClock
import com.example.newbasicstructure.databinding.ActivitySplashScreenBinding
import com.example.newbasicstructure.util.convertDateIntoMillis
import com.example.newbasicstructure.util.getDateData
import com.example.newbasicstructure.util.mServerDateFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class TimerScreen : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding

    //    var drawView: DrawView? = null
    val timeToNextEvent = convertDateIntoMillis("2022-03-25T04:50:12.208Z", mServerDateFormat) - System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countdownClockSecond.setCountdownListener(object :
            CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                Log.d("here", "Countdown second is about to finish")
            }

            override fun countdownFinished() {
                Log.d("here", "Countdown second finished")
                binding.countdownClockSecond.setCountDownTime("2022-03-14T12:58:25.208Z")
            }
        })
        binding.countdownClockSecond.setCountDownTime("2022-03-14T12:58:25.208Z")


        object : CountDownTimer(timeToNextEvent, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val date = getDateData("2022-03-14T12:58:25.208Z", mServerDateFormat)
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                binding.textView.text =
                    f.format(date.days) + ":" + f.format(date.hours).toString() + ":" + f.format(date.minutes) + ":" + f.format(date.seconds)
            }

            // When the task is over it will print 00:00:00:00 there
            override fun onFinish() {
                binding.textView.text = "00:00:00:00"
            }
        }.start()
    }
}
