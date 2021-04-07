package com.pharmacy.crack.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.TwoPlayerBattleActivity
import kotlinx.android.synthetic.main.row_friends.view.*
import kotlinx.android.synthetic.main.row_turn.view.*
import kotlinx.android.synthetic.main.row_turn.view.txtTurnName

class FriendsAdapter(private var context : Context, private var list: ArrayList<String>) :
    RecyclerView.Adapter<FriendsAdapter.FriendsHolder>(){

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(LayoutInflater.from(context).inflate(R.layout.row_friends,parent,false))
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.itemView.txtFriends.text = list[position]

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,TwoPlayerBattleActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}