package com.pharmacy.crack.main.view.GameActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.CategorySpinActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_two_player_battle.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class TwoPlayerBattleActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_battle)

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "2 Player Battle"
//        txtToolbar.text = "2 Player Battle".toUpperCase(Locale.ROOT)

        txtStartGame.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtStartGame){
            startActivity(Intent(this,CategorySpinActivity::class.java))
        }
    }

}