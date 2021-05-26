package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_win.*

class WinActivity : AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_win)

        txtContinue.setOnClickListener(this)
        PrefHelper(this).wrongQuestion=0
    }

    override fun onClick(v: View?) {
        if (v == txtContinue) {
            if (PrefHelper(this).gametype == "Battle"){
                startActivity(Intent(this,GameResultActivity::class.java))
                }
            else{
                startActivity(Intent(this,DashboardActivity::class.java))
                finishAffinity()
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}