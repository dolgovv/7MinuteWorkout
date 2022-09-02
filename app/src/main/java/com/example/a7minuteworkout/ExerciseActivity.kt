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


        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    //установка экрана отдыха
    //скрывает экран выполнения, сбрасывает таймер отдыха, устанавливает таймер отдыха
    private fun setUpRestView() {
        llStartCountDown.visibility = View.VISIBLE
        llRunning.visibility = View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvTextRest.text = "Get ready to ${exerciseList!![currentExercise+1].getName()}"
        setRestProgressBar()
    }

    //установка экрана выполнения упражнения
    //скрывает экран отдыха, сбрасывает таймер выполнения, уст. картинки и текста, устанавливает таймер выполнения
    private fun setUpRunningView() {

        llRunning.visibility = View.VISIBLE
        llStartCountDown.visibility = View.GONE

        if (runningTimer != null) {
            runningTimer!!.cancel()
            runningProgress = 0
        }
        //setup exercise details
            ivImage.setImageResource(exerciseList!![currentExercise].getImage())
            tvTextRunning.text = exerciseList!![currentExercise].getName()
            startRunningProgressBar()
    }

    //установка и указания прогрессбара-таймера отдыха
    private fun setRestProgressBar() {

        exerProgressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                exerProgressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                //timer is gone - adjusting current exercise count - reset resProgress - setup running view
                currentExercise++
                restProgress = 0
                setUpRunningView()
            }
        }.start()
    }

    //установка и указания прогрессбара-таймера выполнения упражнения
    private fun startRunningProgressBar() {

        //timer algorithm setup
        exerProgressBar_running.progress = runningProgress
        runningTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                runningProgress++
                exerProgressBar_running.progress = 30 - runningProgress
                tvTimer_running.text = (30 - runningProgress).toString()
            }

            override fun onFinish() {
                //timer is gone - reset running progress - another view setup
                runningProgress = 0
                //setting up rest view if next exercise is available
                if (currentExercise+1<exerciseList!!.size){
                setUpRestView()
                }else{ //otherwise showing something
                    Toast.makeText(this@ExerciseActivity, "gospodi blyat", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }


}

//установка отдыха с таймером - таймер кончился (+1 тек.упр -
// установка выполнения с таймером - таймер кончился -
// установка отдыха с таймером - таймер кончился (+1 тек.упр

//возникает проблема с установкой таймера отдыха
