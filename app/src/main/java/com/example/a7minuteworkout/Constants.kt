package com.example.a7minuteworkout

class Constants {
    companion object{
          fun defaultExerciseList(): ArrayList<ExerciseModel>{
              val exerciseList = ArrayList<ExerciseModel>()

              val cableFly = ExerciseModel(1,
                  "Fire Fighter",
                  R.drawable.fire_fighters,
                  false,
                  false)
              exerciseList.add(cableFly)

              val kneeInCrunch = ExerciseModel(2,
              "Knee in Crunch",
              R.drawable.kneein_crunches,
              false,
              false)
              exerciseList.add(kneeInCrunch)

              val gluteBridge = ExerciseModel(3,
              "Glute Bridge",
              R.drawable.glute_bridge,
              false,
              false)
              exerciseList.add(gluteBridge)


              //a place for an exercises

              return exerciseList
          }
    }
}