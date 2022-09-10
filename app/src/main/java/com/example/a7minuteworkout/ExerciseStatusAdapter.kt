package com.example.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvItem = view.tvItem
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        //set the selected exercise color
        if(model.getIsSelected()){
            holder.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_round_selected)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }

        //set the completed exercise color
        else if(model.getIsCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_round_completed)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))


        }

        //set the incoming exercise color
        else{
            holder.tvItem.background = ContextCompat.getDrawable(context,
                R.drawable.item_round_grey_background)
            holder.tvItem.setTextColor(Color.parseColor("#000000"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_exercise_status, parent, false))

    }

    override fun getItemCount(): Int {
        return items.size
    }
}