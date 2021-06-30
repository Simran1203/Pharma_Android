package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_game_result.*
import kotlinx.android.synthetic.main.activity_win.*

class WinActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_win)

        txtContinue.setOnClickListener(this)
        QuestionActivity.wrongAns =0
        pref = PrefHelper(this)
        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.profile_img).into(imgPlayerWin)
        }
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