package com.example.a7minuteworkout

class Constants {
    companion object{
          fun defaultExerciseList(): ArrayList<ExerciseModel>{
              val exerciseList = ArrayList<ExerciseModel>()
              val cableFly = ExerciseModel(1,
                  "Cable Fly",
                  R.drawable.chest_cable_fly_straight,
                  false,
                  false)
              exerciseList.add(cableFly)

              //a place for an exercises

              return exerciseList
          }
    }
}