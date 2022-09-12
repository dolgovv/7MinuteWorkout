package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish_exercise.*
import java.text.SimpleDateFormat
import java.util.*

class FinishExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_exercise)

        btn_finishExercise.setOnClickListener{
            val intent = Intent(this@FinishExerciseActivity, MainActivity::class.java)
            startActivity(intent)
        }
        addDateToDataBase()
    }

    private fun addDateToDataBase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("DATE:", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("DATE:", "Added")

    }
}