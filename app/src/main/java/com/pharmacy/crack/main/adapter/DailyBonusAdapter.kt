package com.pharmacy.crack.main.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import com.pharmacy.crack.data.model.masterBonus.Getbonu
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import com.pharmacy.crack.main.view.rewardsActivity.DailyBonusActivity
import com.pharmacy.crack.utils.viewUtils.HardBoldTextView
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.row_daily_bonus.view.*
import kotlinx.android.synthetic.main.row_friends.view.*

class DailyBonusAdapter(
    private var context: Context,
    private var list: ArrayList<String>,
    private var listBonus: ArrayList<Getbonu>,

) :
    RecyclerView.Adapter<DailyBonusAdapter.FriendsHolder>() {

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(
            LayoutInflater.from(context).inflate(R.layout.row_daily_bonus, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.itemView.txtBonusDay.text = list[position]

        if(position==6||position==8){
            holder.itemView.visibility = View.INVISIBLE
        }

        when (position) {
            0 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.pills)
            1 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.mystery)
            2 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.stopwatch)
            3 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.mystery)
            4 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.fifty_fifty)
            5 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.mystery)
            6 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.mystery)
            7 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.pills)
            8 -> holder.itemView.imgMainBonus.setImageResource(R.drawable.pills)
        }
        holder.itemView.setOnClickListener {
            if(holder.itemView.imgClaimed.visibility != View.VISIBLE){
                var dialogBonus = Dialog(context,android.R.style.Theme_Light)
                dialogBonus.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogBonus.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
                dialogBonus.setContentView(R.layout.dialog_collect_bonus)
                dialogBonus.setCancelable(false)
                var imgCollectBonus : ImageView = dialogBonus.findViewById(R.id.imgCollectBonus)
                var txtBonusDay4 : HardBoldTextView = dialogBonus.findViewById(R.id.txtBonusDay4)
                when (position) {
                    0 -> imgCollectBonus.setImageResource(R.drawable.pills)
                    1 -> imgCollectBonus.setImageResource(R.drawable.mystery)
                    2 -> imgCollectBonus.setImageResource(R.drawable.stopwatch)
                    3 -> imgCollectBonus.setImageResource(R.drawable.mystery)
                    4 -> imgCollectBonus.setImageResource(R.drawable.fifty_fifty)
                    5 -> imgCollectBonus.setImageResource(R.drawable.mystery)
                    6 -> imgCollectBonus.setImageResource(R.drawable.mystery)
                    7 -> imgCollectBonus.setImageResource(R.drawable.pills)
                    8 -> imgCollectBonus.setImageResource(R.drawable.pills)
                }
                when (position) {
                    0 -> txtBonusDay4.text = "250"
                    1 -> txtBonusDay4.text = "Mystery"
                    2 -> txtBonusDay4.text = "3"
                    3 -> txtBonusDay4.text = "Mystery"
                    4 -> txtBonusDay4.text = "5"
                    5 -> txtBonusDay4.text = "Mystery"
                    6 -> txtBonusDay4.text = ""
                    7 -> txtBonusDay4.text = "2000"
                    8 -> txtBonusDay4.text = ""
                }
                dialogBonus.show()
                var txtCollectBonus: RegularTextView = dialogBonus.findViewById(R.id.txtCollectBonus)
                txtCollectBonus.setOnClickListener(View.OnClickListener {

                    holder.itemView.imgClaimed.visibility = View.VISIBLE
                    holder.itemView.txtBonusDay.text = "Claim"
                    holder.itemView.txtBonusDay.setTextColor(Color.parseColor("#FF0091"))
                    dialogBonus.dismiss()

                })
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}