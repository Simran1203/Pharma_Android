package com.pharmacy.crack.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.row_leaderboard.view.*
import kotlinx.android.synthetic.main.row_leaderboard.view.txtNameLeaderboard
import kotlinx.android.synthetic.main.row_turn.view.*

class ChooseGameTurnAdapter(private var context : Context, private var list: ArrayList<String>) :
    RecyclerView.Adapter<ChooseGameTurnAdapter.ChooseGameTurnHolder>(){

    inner class ChooseGameTurnHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseGameTurnHolder {
        return ChooseGameTurnHolder(LayoutInflater.from(context).inflate(R.layout.row_turn,parent,false))
    }

    override fun onBindViewHolder(holder: ChooseGameTurnHolder, position: Int) {
        holder.itemView.txtTurnName.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}