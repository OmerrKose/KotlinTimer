package com.example.kotlintimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    // Variable for timer
    private var countDownTimer: CountDownTimer? = null

    // The duration of the timer in milliseconds
    private var timerDuration: Long = 60000

    // pauseOffset = timerDuration - timeLeft
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Change the text view of the counter with timer duration
        findViewById<TextView>(R.id.counterTextView).text = (timerDuration / 1000).toString()
        findViewById<Button>(R.id.startButton).setOnClickListener {
            startTimer(pauseOffset)
        }

        findViewById<Button>(R.id.stopButton).setOnClickListener {
            pauseTimer()
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetTimer()
        }
    }

    // Function to start the timer
    private fun startTimer(pauseOffsetLong: Long) {
        countDownTimer = object : CountDownTimer(
            timerDuration - pauseOffsetLong,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished
                findViewById<TextView>(R.id.counterTextView).text =
                    (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@MainActivity,
                    "Timer has finished",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    // Function to pause the timer
    private fun pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun resetTimer() {
        if(countDownTimer != null) {
            countDownTimer!!.cancel()
            findViewById<TextView>(R.id.counterTextView).text = (timerDuration / 1000).toString()
            countDownTimer = null
            pauseOffset = 0
        }
    }
}