package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_dashboard)

        imgSetting.setOnClickListener(this)
        txtFreeRewards.setOnClickListener(this)
        txtDrugStore.setOnClickListener(this)
        txtLeaderBoard.setOnClickListener(this)
        txtStartGame.setOnClickListener(this)
        txtStatsIq.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==imgSetting){
            startActivity(Intent(this, SettingActivity::class.java))
        }
        if(v==txtFreeRewards){
            startActivity(Intent(this, FreeRewardsActivity::class.java))
        }
        if(v==txtDrugStore){
            startActivity(Intent(this, DrugStoreActivity::class.java))
        }
        if(v==txtLeaderBoard){
            startActivity(Intent(this, LeaderBoardActivity::class.java))
        }
        if(v==txtStartGame){
            startActivity(Intent(this, StartGameActivity::class.java))
        }
        if(v==txtStatsIq){
            startActivity(Intent(this, StatsIqActivity::class.java))
        }
    }
}