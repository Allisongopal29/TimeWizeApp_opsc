package com.opsc7311.timewizeapp

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PomodoroTimer : AppCompatActivity() {

    private lateinit var counterclockwise: TextView
    private lateinit var startBtn: Button
    private var timer: CountDownTimer? = null

    private fun startTimer() {
        val totalTime = 60 * 60 * 1000L // 60 minutes

        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / 1000) / 3600
                val minutes = ((millisUntilFinished / 1000) % 3600) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                counterclockwise.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                counterclockwise.text = "00:00:00"
                notifyUser()
            }
        }

        timer?.start()
    }

    private fun notifyUser() {
        Toast.makeText(this, "Your Timer finished! Take a break", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_timer)

        counterclockwise = findViewById(R.id.counterclockwise)
        startBtn = findViewById(R.id.startBtn)

        startBtn.setOnClickListener {
            startTimer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
