package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.system.Os.accept
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restProgress = 0
    private var restTimer: CountDownTimer? = null

    private var runningProgress = 0
    private var runningTimer: CountDownTimer? = null

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = -1 //made for convenient increasing index at the RestBar`s finish

    private var tts: TextToSpeech? = null

    private var soundPlayer: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val exitDialog: Dialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.supporting_text))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                finish()
                dialog.dismiss()
            }.create()

        btn_cheat_forward.setOnClickListener{
            val intent = Intent(this@ExerciseActivity, FinishExerciseActivity::class.java)
            startActivity(intent)
        }

        btn_cheat_forward_two.setOnClickListener{
            exitDialog.show()
        }

        //implementing a toolbar at the start rest countdown timer menu
        setSupportActionBar(upper_case_exercise)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_exercise.setNavigationOnClickListener {
            exitDialog.show()
        }
        upper_case_exercise_running.setNavigationOnClickListener {
            exitDialog.show()
        }

        //toolbar at the start exercise timer menu
        setSupportActionBar(upper_case_exercise_running)
        val actionbarRunning = supportActionBar
        if (actionbarRunning != null) {
            actionbarRunning.setDisplayHomeAsUpEnabled(true)
        }
        upper_case_exercise.setNavigationOnClickListener {
            exitDialog.show()
        }
        upper_case_exercise_running.setNavigationOnClickListener {
            exitDialog.show()
        }

        tts = TextToSpeech(this,this)

        //implementing an exercise list, setting up a rest view and a recycle view
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
        setUpExerciseStatusRecycleView()
    }

    //destroying all the var`s needed to rest and running views working
    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (runningTimer != null){
            runningTimer!!.cancel()
            runningProgress = 0
        }
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(soundPlayer != null){
            soundPlayer!!.stop()
        }
        super.onDestroy()
    }

    //setting up rest view:
    //play success sound
    //hide running view, reset RestTimer
    //setting up incoming exercise text and RestBar
    private fun setUpRestView() {
        try {
            soundPlayer = MediaPlayer.create(applicationContext, R.raw.success_sound)
            soundPlayer!!.isLooping = false
            soundPlayer!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

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
    //скрывает экран отдыха, сбрасывает таймер выполнения, уст. картинки и текст,
    // устанавливает таймер выполнения
    private fun setUpRunningView() {

        llRunning.visibility = View.VISIBLE
        llStartCountDown.visibility = View.GONE

        if (runningTimer != null) {
            runningTimer!!.cancel()
            runningProgress = 0
        }

        speakOut(exerciseList!![currentExercise].getName())
        //setup exercise details
            ivImage.setImageResource(exerciseList!![currentExercise].getImage())
            tvTextRunning.text = exerciseList!![currentExercise].getName()
        setRunningProgressBar()
    }

    //установка таймера отдыха, по завершению +1 к значению текущего упражнения,
    // установка прогресса таймера отдыха на ноль, вызов установки Running view
    private fun setRestProgressBar() {

        exerProgressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                exerProgressBar.progress = 10 - restProgress
                ibStartExercise.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                //timer is gone - adjusting current exercise count - reset resProgress - setup running view
                currentExercise++
                restProgress = 0

                exerciseList!![currentExercise].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpRunningView()
            }
        }.start()
    }

    //установка и указания прогрессбара-таймера выполнения упражнения
    private fun setRunningProgressBar() {

        //timer algorithm setup
        exerProgressBar_running.progress = runningProgress
        runningTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                runningProgress++
                exerProgressBar_running.progress = 30 - runningProgress
                ibStartExercise_running.text = (30 - runningProgress).toString()
            }

            override fun onFinish() {
                //timer is gone - reset running progress - another view setup
                runningProgress = 0
                //setting up rest view if next exercise is available
                if (currentExercise+1<exerciseList!!.size){

                    exerciseList!![currentExercise].setIsSelected(false)
                    exerciseList!![currentExercise].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                setUpRestView()
                }else{ //otherwise showing something
                    val intent = Intent(this@ExerciseActivity,
                        FinishExerciseActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(this@ExerciseActivity,
                        "gospodi blyat",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported.")
            }else{
                Log.e("TTS", "Initialization failed")
            }
        }
    }
    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusRecycleView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter

    }


}

//установка отдыха с таймером - таймер кончился (+1 тек.упр -
// установка выполнения с таймером - таймер кончился -
// установка отдыха с таймером - таймер кончился (+1 тек.упр

//возникает проблема с установкой таймера отдыха
