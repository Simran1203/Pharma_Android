package com.pharmacy.crack.main.view.mainActivities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.TermsConditionActivity
import com.pharmacy.crack.utils.*
import com.pharmacy.crack.utils.viewUtils.HardBoldTextView
import com.pharmacy.crack.utils.viewUtils.RegularTextView
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dialogTutorial: Dialog
    private lateinit var imgDashTutorial: ImageView
    private lateinit var txtHeadDashTutorial: HardBoldTextView
    private lateinit var imgDescDashTutorial: RegularTextView
    private lateinit var consDasDialog: ConstraintLayout
    private var doubleBackToExitPressedOnce = false
    lateinit var pref: PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_dashboard)
        pref = PrefHelper(this)

        imgSetting.setOnClickListener(this)
        txtFreeRewards.setOnClickListener(this)
        txtDrugStore.setOnClickListener(this)
        txtLeaderBoard.setOnClickListener(this)
        txtStartGame.setOnClickListener(this)
        txtStatsIq.setOnClickListener(this)
        imgFifty.setOnClickListener(this)
        imgClock.setOnClickListener(this)
        imgSpin.setOnClickListener(this)
        imgLive.setOnClickListener(this)
        imgPills.setOnClickListener(this)
        imgTermDashBoard.setOnClickListener(this)
        txtIqMap.setOnClickListener(this)

        dialogTutorial = Dialog(
            this@DashboardActivity,
            android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
        )
        dialogTutorial.setContentView(R.layout.dialog_dashboard_tutorial)
        dialogTutorial.setCanceledOnTouchOutside(true)
        dialogTutorial.setCancelable(true)
        imgDashTutorial = dialogTutorial.findViewById(R.id.imgDashTutorial)
        txtHeadDashTutorial = dialogTutorial.findViewById(R.id.txtHeadDashTutorial)
        imgDescDashTutorial = dialogTutorial.findViewById(R.id.imgDescDashTutorial)
        consDasDialog = dialogTutorial.findViewById(R.id.consDasDialog)
        consDasDialog.setOnClickListener {
            dialogTutorial.dismiss()
        }

//        hideStatusBarForDialod()

        if((!pref.profilePic.isNullOrEmpty())&&(pref.profilePic != "null")){
            Glide.with(this).load(pref.profilePic).placeholder(R.drawable.profile_img).into(imgPhoto)
        }
    }

    private fun hideStatusBarForDialod() {


        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

    }

    override fun onClick(v: View?) {
        if (v == imgSetting) {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        if (v == txtFreeRewards) {
            startActivity(Intent(this, FreeRewardsActivity::class.java)
                .putExtra("fromSource","Dashboard"))
        }
        if (v == txtDrugStore) {
            startActivity(Intent(this, DrugStoreActivity::class.java))
        }
        if (v == txtLeaderBoard) {
            startActivity(Intent(this, LeaderBoardActivity::class.java))
        }
        if (v == txtStartGame) {
            startActivity(Intent(this, StartGameActivity::class.java)
                .putExtra("fromSource","Dashboard"))
        }
        if (v == txtStatsIq) {
            if(!isNetworkAvailable(this)){
                showToast(this, "Please check your internet connection and try again.")
            }else{
                startActivity(Intent(this, StatsIqActivity::class.java))
            }

        }
        if (v == txtIqMap) {
            startActivity(Intent(this, IQMapActivity::class.java))
        }
        if (v == imgTermDashBoard) {
            startActivity(Intent(this, TermsConditionActivity::class.java))
        }
        if (v == imgFifty) {
            imgDashTutorial.setImageResource(R.drawable.fifty_fifty)
            txtHeadDashTutorial.text = "50/50"
            imgDescDashTutorial.text = "You can eliminate 2 wrong\nanswer using this power."
            dialogTutorial.show()
        }
        if (v == imgClock) {
            imgDashTutorial.setImageResource(R.drawable.stopwatch)
            txtHeadDashTutorial.text = "Time Stop"
            imgDescDashTutorial.text = "You can stop the question\ntime using this."
            dialogTutorial.show()

        }
        if (v == imgSpin) {
            imgDashTutorial.setImageResource(R.drawable.respin)
            txtHeadDashTutorial.text = "Respin"
            imgDescDashTutorial.text = "You can respin during\nChoose Category."
            dialogTutorial.show()

        }
        if (v == imgLive) {
            imgDashTutorial.setImageResource(R.drawable.lives)
            txtHeadDashTutorial.text = "Lives"
            imgDescDashTutorial.text = "You can use Lives to\ncontinue the game after a\nwrong answer."
            dialogTutorial.show()

        }
        if (v == imgPills) {
            imgDashTutorial.setImageResource(R.drawable.pills)
            txtHeadDashTutorial.text = "Pills"
            imgDescDashTutorial.text = "You can use Pills to\npurchase all Power\nUps i.e respin, lives,\ntime stop & 50/50."
            dialogTutorial.show()

        }
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}