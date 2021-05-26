package com.pharmacy.crack.main.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_lose.*
import kotlinx.android.synthetic.main.toolbar.*

class LoseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_lose)

        initAll()


        txtGameOver.setOnClickListener{
            if (PrefHelper(this).gametype == "Battle"){
                startActivity(Intent(this,GameResultActivity::class.java))
            }
            else{
                startActivity(Intent(this, DashboardActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "You Lose"

        txtToolbar.setTextColor(Color.parseColor("#FFFFFF"));
        PrefHelper(this).wrongQuestion=0
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}