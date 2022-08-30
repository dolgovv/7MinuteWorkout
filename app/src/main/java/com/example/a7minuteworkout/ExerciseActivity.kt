package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_main.*

class ExerciseActivity : AppCompatActivity() {

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null
    private var runningProgress = 0
    private var runningTimer: CountDownTimer? = null

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(upper_case_exercise) //toolbar at the start rest countdown timer menu
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_exercise.setNavigationOnClickListener {
            onBackPressed()
        }
        upper_case_exercise_running.setNavigationOnClickListener {
            onBackPressed()
        }

        setSupportActionBar(upper_case_exercise_running) //toolbar at the start exercise timer menu
        val actionbarRunning = supportActionBar
        if (actionbarRunning != null) {
            actionbarRunning.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_exercise.setNavigationOnClickListener {
            onBackPressed()
        }
        upper_case_exercise_running.setNavigationOnClickListener {
            onBackPressed()
        }
        setUpRestView()

        exerciseList = Constants.defaultExerciseList()

    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        exerProgressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                exerProgressBar.progress = 11 - restProgress
                tvTimer.text = (11 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercise++
                Toast.makeText(
                    this@ExerciseActivity, "dawaii pognall",
                    Toast.LENGTH_SHORT
                ).show()
                llRunning.visibility = View.VISIBLE
                llStartCountDown.visibility = View.GONE
                startRunningProgressBar()
                restProgress = 0
                setUpRunningView()
            }
        }.start()

    }

    private fun startRunningProgressBar() {
        exerProgressBar_running.progress = runningProgress
        runningTimer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                runningProgress++
                exerProgressBar_running.progress = 16 - runningProgress
                tvTimer_running.text = (16 - runningProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity, "done yept",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    private fun setUpRestView() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
    private fun setUpRunningView() {
        if (runningTimer != null) {
            runningTimer!!.cancel()
            runningProgress = 0
        }

        startRunningProgressBar()
    }
}