package com.pharmacy.crack.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.row_leaderboard.view.*

class LeaderBoardAdapter(private var context : Context,private var list: ArrayList<String>) :
RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardHolder>(){

    inner class LeaderBoardHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardHolder {
        return LeaderBoardHolder(LayoutInflater.from(context).inflate(R.layout.row_leaderboard,parent,false))
    }

    override fun onBindViewHolder(holder: LeaderBoardHolder, position: Int) {
        holder.itemView.txtNameLeaderboard.text = list[position]
        holder.itemView.txtSequence.text = (position+1).toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}