package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmicalculator.*

class BMICalculatorActivity : AppCompatActivity() {

    private var bmiResult = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmicalculator)

        bmiCard.visibility = GONE

        setSupportActionBar(upper_case_bmi)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_calculate.setOnClickListener{
            bmiCard.visibility = VISIBLE
            setBMIindex()
            setDescriptionBMIResult(tvResultBMI.text.toString().toDouble().toInt())
        }
    }

    private fun setBMIindex(){
        bmiResult = calculateBMI(t_weight.text.toString(), t_height.text.toString())
        tvResultBMI.text = bmiResult
    }

    private fun setDescriptionBMIResult(bmiIndex: Int){
        if (bmiIndex <= 20){
            tvResultDescription.setText(R.string.lowBMIdescription)
        }
        else if(bmiIndex in 21..24){
            tvResultDescription.setText(R.string.normalBMIdescription)
        }
        else{
            tvResultDescription.setText(R.string.highBMIdescription)
        }
    }

    private fun calculateBMI(weight: String, height: String): String {

        val heightDouble = height.toDouble()/100
        val preResult = (weight.toDouble() / (heightDouble * heightDouble)).toString()
        val result = preResult.substring(0, 4)

        return result

    }


}