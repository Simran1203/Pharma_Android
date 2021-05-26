package com.pharmacy.crack.main.view

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.main.view.mainActivities.DrugStoreActivity
import com.pharmacy.crack.main.view.mainActivities.FreeRewardsActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.viewUtils.HardBoldTextView
import kotlinx.android.synthetic.main.activity_incorrect.*


class IncorrectActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var dialogBuyPillLife: Dialog
    lateinit var dialogResume: Dialog
    lateinit var imgCloseBuy: ImageView
    lateinit var imgCloseResume: ImageView
    lateinit var btnResumeIn: HardBoldTextView
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_incorrect)

        initAll()
    }

    private fun initAll() {
        txtTimeIncorrect.text = intent.getStringExtra("time")
        dialogBuyPillLife = Dialog(this, android.R.style.Theme_Light)
        dialogBuyPillLife.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBuyPillLife.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogBuyPillLife.setContentView(R.layout.dialog_buy_now)
        imgCloseBuy = dialogBuyPillLife.findViewById(R.id.imgCloseBuy)

        dialogResume = Dialog(this, android.R.style.Theme_Light)
        dialogResume.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogResume.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
        dialogResume.setContentView(R.layout.dialog_resume)
        imgCloseResume = dialogResume.findViewById(R.id.imgCloseResume)
        btnResumeIn = dialogResume.findViewById(R.id.btnResumeIn)

        txtReportQuest.setOnClickListener(this)
        txtendGame.setOnClickListener(this)
        relFreeReward.setOnClickListener(this)
        constarintUsePills.setOnClickListener(this)
        constarintUseHeart.setOnClickListener(this)
        relPurchasePill.setOnClickListener(this)
        imgCloseBuy.setOnClickListener(this)
        imgCloseResume.setOnClickListener(this)
        btnResumeIn.setOnClickListener(this)

        dialogBack()

    }

    private fun dialogBack() {
        dialogBuyPillLife.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                PrefHelper(this).wrongQuestion = 0
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            true
        })
        dialogResume.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                super.onBackPressed()
            }
            true
        })
    }

    override fun onClick(v: View?) {
        if (v == txtReportQuest) {
            PrefHelper(this).wrongQuestion = 0
            startActivity(
                Intent(this, ReportQuestionActivity::class.java)
                    .putExtra("que", txtQueInCorr.text.toString())
            )
        } else if (v == txtendGame) {
            PrefHelper(this).wrongQuestion = 0
            startActivity(Intent(this, LoseActivity::class.java))

        }
        else if (v == relFreeReward) {
            PrefHelper(this).wrongQuestion = 0
            startActivity(Intent(this, FreeRewardsActivity::class.java))

        }
        else if (v == relPurchasePill) {
            PrefHelper(this).wrongQuestion = 0
            startActivity(Intent(this, DrugStoreActivity::class.java))

        }
        else if (v == constarintUseHeart) {
            dialogBuyPillLife.show()
        }
        else if (v == imgCloseBuy) {
            dialogBuyPillLife.dismiss()
        }
        else if (v == constarintUsePills) {
            dialogResume.show()
        }
        else if (v == imgCloseResume) {
            dialogResume.dismiss()
        }
        else if (v == btnResumeIn) {
            dialogResume.dismiss()
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

}