package com.pharmacy.crack.main.view.GameActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.toolbar.*

class TwoPlayerBattleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_battle)

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "2 Player Battle"

    }

}