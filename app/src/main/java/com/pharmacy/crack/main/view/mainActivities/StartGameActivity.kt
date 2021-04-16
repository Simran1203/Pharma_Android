package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.adapter.ChooseGameTurnAdapter
import com.pharmacy.crack.main.view.GameActivities.SoloActivity
import com.pharmacy.crack.main.view.GameActivities.SelectOpponentActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_start_game.*
import kotlinx.android.synthetic.main.toolbar.*

class StartGameActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var list: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_start_game)

        initAll()
        listner()

    }

    private fun listner() {
        txtDashboardTurn.setOnClickListener(this)
        txtSolo.setOnClickListener(this)
        txtTwoPlayer.setOnClickListener(this)
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "Choose Game"

        list = ArrayList()
        list.add("Michel")
        list.add("Rassel")
        list.add("Maggi")
        list.add("Richard")
        list.add("Rx 83rd")

        rvTurn.adapter = ChooseGameTurnAdapter(this,list)
    }

    override fun onClick(v: View?) {
        if(v==txtDashboardTurn){
            super.onBackPressed()
        }
        if(v==txtSolo){
            PrefHelper(this).gametype = "Solo"
            startActivity(Intent(this,SoloActivity::class.java))
        }
        if(v==txtTwoPlayer){
            PrefHelper(this).gametype = "Battle"
            startActivity(Intent(this,SelectOpponentActivity::class.java))
        }
    }
}