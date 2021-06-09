package com.pharmacy.crack.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.row_question.view.*

class QuestionAdapter(private var context : Context, private var list: ArrayList<String>,
     var onOptionClick: (pos:Int)->Unit) :
    RecyclerView.Adapter<QuestionAdapter.LeaderBoardHolder>(){

    inner class LeaderBoardHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardHolder {
        return LeaderBoardHolder(LayoutInflater.from(context).inflate(R.layout.row_question,parent,false))
    }

    override fun onBindViewHolder(holder: LeaderBoardHolder, position: Int) {
        holder.itemView.txtQueOption.text = list[position]

        holder.itemView.txtQueOption.setOnClickListener {
            onOptionClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}