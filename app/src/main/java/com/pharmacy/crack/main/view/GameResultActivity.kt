package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.main.view.mainActivities.StartGameActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.toolbar.*

class GameResultActivity : AppCompatActivity(),View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_game_result)

        initAll()
    }

    private fun initAll() {
        txtToolbar.text = "Game Result"
        imgBackToolbar.setOnClickListener(this)
        txtPlayAgainGame.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtPlayAgainGame){
            startActivity(Intent(this,StartGameActivity::class.java))
            finishAffinity()
        }
        if(v==imgBackToolbar){
           startActivity(Intent(this,DashboardActivity::class.java))
            finishAffinity()
        }
    }
}