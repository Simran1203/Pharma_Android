package com.pharmacy.crack.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.CategorySpinActivity
import com.pharmacy.crack.main.view.LandedCasseyCapsuleActivity.Companion.selectionLandedcasey
import kotlinx.android.synthetic.main.row_catspin.view.*

class LandedCaseyCapsulAdapter(
    private var context: Context,
    private var list: ArrayList<String>,
    val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<LandedCaseyCapsulAdapter.FriendsHolder>() {

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(
            LayoutInflater.from(context).inflate(R.layout.row_catspin, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.itemView.txtCatSpin.text = list[position]

        if (selectionLandedcasey ==position) {
            holder.itemView.constraintCatspin.setBackgroundColor(Color.parseColor("#FF0091"))
            holder.itemView.txtCatSpin.setTextColor(Color.parseColor("#ffffff"))
        }
        else {
            holder.itemView.constraintCatspin.setBackgroundColor(Color.parseColor("#FFF773"))
            holder.itemView.txtCatSpin.setTextColor(Color.parseColor("#444444"))
        }
        when (list[position]) {
            "Oncology & misc" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.oncology_misc)
            "Women & Pediatrics" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.women_health_pediatrics)
            "Endocrinology & Toxicology" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.endocrinology_toxicology)
            "Infectious Disease & Immunology" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.infectious_disease_immunology)
            "Casey" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.cassy)
            "Abused Substances" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.illegal_abused)
            "Cardiology & Hematology" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.cardiology_hematology)
            "OTC & Herbal" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.otc_drugs_herbals)
            "Fun Facts" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.history)
            "Neurology & Psychology" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.neurology_psychiatry)
            "New Rx" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.new_drugs)
            "Law" -> holder.itemView.imgCatSpin.setImageResource(R.drawable.pharmacy_law)
        }

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}