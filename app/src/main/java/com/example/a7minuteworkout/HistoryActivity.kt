package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(upper_case_history)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_history.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }

    private fun getAllCompletedDates(){

        val dbHandler = SqliteOpenHelper(this, null)

        val allCompletedDatesList =
            dbHandler.getAllCompletedDatesList()

            if(allCompletedDatesList.size > 0){

                rvHistory.visibility = VISIBLE
                tv_noData.visibility = GONE

                for (i in allCompletedDatesList){
                    Log.i("DateHISTORYACTIVITY", "" + i)

                    rvHistory.layoutManager = LinearLayoutManager(this)
                val historyAdapter = HistoryAdapter(this, allCompletedDatesList)
                rvHistory.adapter = historyAdapter
                }
            }
            else{
                rvHistory.visibility = GONE
                tv_noData.visibility = VISIBLE
            }

    }
}