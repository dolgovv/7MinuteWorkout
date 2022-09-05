package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish_exercise.*

class FinishExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_exercise)

        btn_finishExercise.setOnClickListener{
            val intent = Intent(this@FinishExerciseActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}