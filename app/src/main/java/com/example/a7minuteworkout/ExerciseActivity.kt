package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_exercise)
        setSupportActionBar(upper_case_exercise)
        val actionbar = supportActionBar
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_exercise.setNavigationOnClickListener{
            onBackPressed()
        }

//        ibStartExercise.setOnClickListener{
//            setRestProgressBar()
//        }
        setUpRestView()

    }

    override fun onDestroy() {
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }


        super.onDestroy()
    }
    private fun setRestProgressBar(){
        exerProgressBar.progress = restProgress
        restTimer = object: CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                exerProgressBar.progress = 10-restProgress
                tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "dawaii pognall",
                    Toast.LENGTH_SHORT).show()
            }
        }.start()

    }
    private fun setUpRestView(){
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()


    }
}