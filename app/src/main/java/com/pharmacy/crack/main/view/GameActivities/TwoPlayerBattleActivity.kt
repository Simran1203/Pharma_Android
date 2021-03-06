package com.pharmacy.crack.main.view.GameActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.CategorySpinActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.isNetworkAvailable
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_two_player_battle.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class TwoPlayerBattleActivity : AppCompatActivity(),View.OnClickListener {

    var playerName = ""
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_battle)

        initAll()
    }

    private fun initAll() {
        pref = PrefHelper(this)
        playerName = intent.getStringExtra("playername").toString()
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "2 Player Battle"
//        txtToolbar.text = "2 Player Battle".toUpperCase(Locale.ROOT)

        txtStartGame.setOnClickListener(this)
        if(playerName != ""&& playerName!=null && (playerName != "null")){
            txtNameSecondPlayer.text = playerName
        }
        txtNameFirstPlayer.text = pref.fullName
        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.profile_img).into(imgFirstPlayer)
        }
    }

    override fun onClick(v: View?) {
        if(v==txtStartGame){
            if(!isNetworkAvailable(this)){
                showToast(this, "Please check your internet connection and try again.")
            }else{
                pref.opponentName = txtNameSecondPlayer.text.toString()
                startActivity(Intent(this,CategorySpinActivity::class.java))
            }
        }
    }



}