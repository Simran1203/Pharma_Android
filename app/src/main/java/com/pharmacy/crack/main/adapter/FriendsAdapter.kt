package com.pharmacy.crack.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity.Companion.selectedOpponent
import kotlinx.android.synthetic.main.row_friends.view.*

class FriendsAdapter(
    private var context: Context,
    private var list: ArrayList<String>,
    val onClick:(position:Int)-> Unit
) :
    RecyclerView.Adapter<FriendsAdapter.FriendsHolder>(){

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(LayoutInflater.from(context).inflate(R.layout.row_friends,parent,false))
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.itemView.txtFriends.text = list[position]

        holder.itemView.setOnClickListener {
            onClick(position)
        }
            if(selectedOpponent==position){
                holder.itemView.imgCheck.visibility = View.VISIBLE
            }else{
                holder.itemView.imgCheck.visibility = View.GONE
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}