package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.main.view.mainActivities.StartGameActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.toolbar.*

class GameResultActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_game_result)

        initAll()
    }

    private fun initAll() {
        txtToolbar.setText("Game Result")
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