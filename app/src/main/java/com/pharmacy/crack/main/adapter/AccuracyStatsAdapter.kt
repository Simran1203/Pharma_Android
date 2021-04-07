package com.pharmacy.crack.main.adapter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.row_accuracy.view.*


class AccuracyStatsAdapter(
    private var context: Context,
    private var listPercent: ArrayList<Int>,
    private var listImage: ArrayList<Int>
) :
    RecyclerView.Adapter<AccuracyStatsAdapter.ChooseGameTurnHolder>(){

    inner class ChooseGameTurnHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseGameTurnHolder {
        return ChooseGameTurnHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_accuracy,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChooseGameTurnHolder, position: Int) {

          holder.itemView.imgStats.setImageResource(listImage.get(position))
          holder.itemView.progrssStats.progress = listPercent.get(position)

          holder.itemView.txtStatsPercent.text = listPercent.get(position).toString()+"%"

//         if(position==10){
//             val displaymetrics = DisplayMetrics()
//             (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
//
//             val devicewidth = displaymetrics.widthPixels / 2
//
//             holder.itemView.consStat.getLayoutParams().width = devicewidth
//
//         }


        if(listPercent.get(position) in 90..100){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#409B25"
                )
            )
        }
        else if(listPercent.get(position) in 80..89){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#EC8B46"
                )
            )
        }
        else if(listPercent.get(position) in 70..79){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FDAD74"
                )
            )
        }
        else if(listPercent.get(position) in 60..69){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#EEBF4B"
                )
            )
        }
       else if(listPercent.get(position) in 50..59){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FFD97C"
                )
            )
        }
        else if(listPercent.get(position) in 40..49){

            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FF5F5F"
                )
            )
        }
       else if(listPercent.get(position) in 30..39){
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#C63939"
                )
            )
        }


    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}