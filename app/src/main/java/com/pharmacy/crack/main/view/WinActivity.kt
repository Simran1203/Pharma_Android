package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_win.*

class WinActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_win)

        txtContinue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == txtContinue) {
            if (PrefHelper(this).gametype.equals("Battle")){
                startActivity(Intent(this,GameResultActivity::class.java))
                }
            else{
                startActivity(Intent(this,DashboardActivity::class.java))
                finishAffinity()
            }
        }
    }
}