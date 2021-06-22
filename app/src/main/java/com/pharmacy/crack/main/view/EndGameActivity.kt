package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.main.view.mainActivities.DrugStoreActivity
import com.pharmacy.crack.main.view.mainActivities.FreeRewardsActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_end_game.*

class EndGameActivity : AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_end_game)

        txtendGame.setOnClickListener(this)
        txtFreeRewards.setOnClickListener(this)
        txtDrugStore.setOnClickListener(this)
        txtShareEarnfree.setOnClickListener(this)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        if(v==txtendGame){
            startActivity(Intent(this,DashboardActivity::class.java))
        }
        else if(v==txtFreeRewards){
            startActivity(Intent(this,FreeRewardsActivity::class.java))
        }
        else if(v==txtDrugStore){
            startActivity(Intent(this,DrugStoreActivity::class.java))
        }
        else if(v==txtShareEarnfree){
            startActivity(Intent(this,ShareActivity::class.java))
        }
    }
}