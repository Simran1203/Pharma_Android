package com.pharmacy.crack.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import kotlinx.android.synthetic.main.row_drug_store.view.*
import kotlinx.android.synthetic.main.row_friends.view.*
import kotlinx.android.synthetic.main.row_friends.view.txtFriends

class DrugStoreAdapter(
    private var context: Context,
    private var list: ArrayList<String>,
    val onClick:(position:Int)-> Unit
) :
    RecyclerView.Adapter<DrugStoreAdapter.FriendsHolder>(){

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        return FriendsHolder(LayoutInflater.from(context).inflate(R.layout.row_drug_store,parent,false))
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        if(position==0){
            holder.itemView.cnstntImgData.visibility = View.VISIBLE
            holder.itemView.cnsntTextData.visibility = View.GONE
            holder.itemView.txtTitleImage.text = list[position]
            holder.itemView.txtpills.text = "500 pills"
            holder.itemView.txtHeadingBottom.text = "Add lives"
            holder.itemView.imgDrug.setImageResource(R.drawable.heart)

        }
        if(position==1){
            holder.itemView.cnstntImgData.visibility = View.GONE
            holder.itemView.cnsntTextData.visibility = View.VISIBLE
            holder.itemView.txtDrugtitle2.text = list[position]
            holder.itemView.txtpricetext.text = "$ 4.99"
            holder.itemView.txtHeadingBottom.text = "Professional\nVersion"
        }
        if(position==2){
            holder.itemView.cnstntImgData.visibility = View.VISIBLE
            holder.itemView.cnsntTextData.visibility = View.GONE
            holder.itemView.txtTitleImage.text = list[position]
            holder.itemView.txtpills.text = "$ 0.99"
            holder.itemView.txtHeadingBottom.text = "Pill Pack"
            holder.itemView.imgDrug.setImageResource(R.drawable.pills)
        }
        if(position==3){
            holder.itemView.cnstntImgData.visibility = View.VISIBLE
            holder.itemView.cnsntTextData.visibility = View.GONE
            holder.itemView.txtTitleImage.text = list[position]
            holder.itemView.txtpills.text = "10000 pills"
            holder.itemView.txtHeadingBottom.text = "Pro\nStarter Pack"
            holder.itemView.imgDrug.setImageResource(R.drawable.pills)
        }
        if(position==4){
            holder.itemView.cnstntImgData.visibility = View.VISIBLE
            holder.itemView.cnsntTextData.visibility = View.GONE
            holder.itemView.txtTitleImage.text = list[position]
            holder.itemView.txtpills.text = "$ 1.99"
            holder.itemView.txtHeadingBottom.text = "Pro Bundle"
            holder.itemView.imgDrug.setImageResource(R.drawable.pills)
        }
        if(position==5){
            holder.itemView.cnstntImgData.visibility = View.VISIBLE
            holder.itemView.cnsntTextData.visibility = View.GONE
            holder.itemView.txtTitleImage.text = list[position]
            holder.itemView.txtpills.text = "$ 4.99"
            holder.itemView.txtHeadingBottom.text = "Pro Mega\nBundle"
            holder.itemView.imgDrug.setImageResource(R.drawable.pills)
        }
        if(position==6){
            holder.itemView.cnstntImgData.visibility = View.GONE
            holder.itemView.cnsntTextData.visibility = View.VISIBLE
            holder.itemView.txtDrugtitle2.text = list[position]
            holder.itemView.txtpricetext.text = "$ 9.99"
            holder.itemView.txtHeadingBottom.visibility = View.GONE
        }
        if(position==7){
            holder.itemView.cnstntImgData.visibility = View.GONE
            holder.itemView.cnsntTextData.visibility = View.VISIBLE
            holder.itemView.txtDrugtitle2.text = list[position]
            holder.itemView.txtpricetext.text = "$ 24.99"
            holder.itemView.txtHeadingBottom.visibility = View.GONE
        }
        if(position==8){
            holder.itemView.cnstntImgData.visibility = View.GONE
            holder.itemView.cnsntTextData.visibility = View.VISIBLE
            holder.itemView.txtDrugtitle2.text = list[position]
            holder.itemView.txtpricetext.text = "$ 24.99"
            holder.itemView.txtHeadingBottom.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}