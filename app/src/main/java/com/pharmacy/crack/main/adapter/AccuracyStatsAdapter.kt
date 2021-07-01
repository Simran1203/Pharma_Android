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
import com.pharmacy.crack.data.model.categoryModels.Category
import kotlinx.android.synthetic.main.row_accuracy.view.*
import kotlinx.android.synthetic.main.row_catspin.view.*


class AccuracyStatsAdapter(
    private var context: Context,
    private var listPercent: ArrayList<Int>,
    private var listImage: ArrayList<Category>
) :
    RecyclerView.Adapter<AccuracyStatsAdapter.ChooseGameTurnHolder>() {

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


        holder.itemView.progrssStats.progress = listPercent[position]
        holder.itemView.txtStatsPercent.text = listPercent.get(position).toString() + "%"

        when (listImage[position].id) {
            6 -> holder.itemView.imgStats.setImageResource(R.drawable.history)
            5 -> holder.itemView.imgStats.setImageResource(R.drawable.illegal_abused)
            7 -> holder.itemView.imgStats.setImageResource(R.drawable.otc_drugs_herbals)
            9 -> holder.itemView.imgStats.setImageResource(R.drawable.cardiology_hematology)
            1 -> holder.itemView.imgStats.setImageResource(R.drawable.infectious_disease_immunology)
            10 -> holder.itemView.imgStats.setImageResource(R.drawable.neurology_psychiatry)
            2 -> holder.itemView.imgStats.setImageResource(R.drawable.women_health_pediatrics)
            8 -> holder.itemView.imgStats.setImageResource(R.drawable.endocrinology_toxicology)
            3 -> holder.itemView.imgStats.setImageResource(R.drawable.new_drugs)
            4 -> holder.itemView.imgStats.setImageResource(R.drawable.pharmacy_law)
            11 -> holder.itemView.imgStats.setImageResource(R.drawable.oncology_misc)
        }

        if (listPercent[position] in 90..100) {
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#409B25"
                )
            )
        } else if (listPercent[position] in 80..89) {
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#EC8B46"
                )
            )
        } else if (listPercent[position] in 70..79) {
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FDAD74"
                )
            )
        } else if (listPercent[position] in 60..69) {
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#EEBF4B"
                )
            )
        } else if (listPercent[position] in 50..59) {
            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FFD97C"
                )
            )
        } else if (listPercent[position] in 40..49) {

            holder.itemView.progrssStats.progressTintList = ColorStateList.valueOf(
                Color.parseColor(
                    "#FF5F5F"
                )
            )
        } else if (listPercent[position] in 30..39) {
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