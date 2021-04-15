package com.pharmacy.crack.main.view.rewardsActivity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_daily_bonus.*
import kotlinx.android.synthetic.main.activity_free_rewards.*
import kotlinx.android.synthetic.main.dialog_collect_bonus.*
import kotlinx.android.synthetic.main.toolbar.*

class DailyBonusActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var dialogBonus : Dialog
    lateinit var txtCollectBonus : RegularTextView
    lateinit var imgCollectBonus : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_daily_bonus)


        initAll()
        clickListner()

        setTopLayout()

    }

    private fun initAll() {
        txtToolbar.setText("Daily Bonus")
        dialogBonus = Dialog(this@DailyBonusActivity,android.R.style.Theme_Light)
        dialogBonus.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBonus.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogBonus.setContentView(R.layout.dialog_collect_bonus)
        dialogBonus.setCancelable(false)
        txtCollectBonus = dialogBonus.findViewById(R.id.txtCollectBonus)
        imgCollectBonus = dialogBonus.findViewById(R.id.imgCollectBonus)
        txtCollectBonus.setOnClickListener(View.OnClickListener {
            imgClaimed1.visibility = View.VISIBLE
            dialogBonus.dismiss()
        })

    }

    private fun clickListner() {
        imgBackToolbar.setOnClickListener(this)
        constarintDay1.setOnClickListener(this)
    }

    private fun setTopLayout() {
        bonusFifty.title = "2"
        bonusFifty.icon = getDrawable(R.drawable.fifty_fifty)

        bonusStopWatch.title = "1"
        bonusStopWatch.icon = getDrawable(R.drawable.stopwatch)

        bonusSpin.title = "3"
        bonusSpin.icon = getDrawable(R.drawable.respin)
    }

    override fun onClick(v: View?) {
        if(v==constarintDay1){
            txtBonusDay1.setText("Claim")
            txtBonusDay1.setTextColor(Color.parseColor("#FF0091"))
            imgCollectBonus.setImageResource(R.drawable.pills)
            dialogBonus.show()
        }
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }
}