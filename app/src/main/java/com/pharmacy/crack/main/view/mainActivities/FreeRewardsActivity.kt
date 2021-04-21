package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.ShareActivity
import com.pharmacy.crack.main.view.rewardsActivity.DailyBonusActivity
import com.pharmacy.crack.main.view.rewardsActivity.SubmitQuestionActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_free_rewards.*
import kotlinx.android.synthetic.main.toolbar.*

class FreeRewardsActivity : AppCompatActivity(), View.OnClickListener {
    var fromSource:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_free_rewards)

        imgBackToolbar.setOnClickListener(this)
        txtDailyBonus.setOnClickListener(this)
        txtSubmitQue.setOnClickListener(this)
        txtShareFreeReward.setOnClickListener(this)
        txtToolbar.setText("Free Rewards")

        if(intent.hasExtra("fromSource")){
            fromSource = intent.getStringExtra("fromSource").toString()
        }
    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
        else if(v==txtDailyBonus){
            startActivity(Intent(this,DailyBonusActivity::class.java))
        }
        else if(v==txtSubmitQue){
            startActivity(Intent(this,SubmitQuestionActivity::class.java))
        }
        else if(v==txtShareFreeReward){
            startActivity(Intent(this,ShareActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if(fromSource == "Dashboard"){
            super.onBackPressed()
        }
        else{
            startActivity(Intent(this,DashboardActivity::class.java))
            finishAffinity()
        }
    }
}