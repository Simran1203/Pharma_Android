package com.pharmacy.crack.main.view.SettingActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.LoseActivity
import com.pharmacy.crack.main.view.QuestionActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_tutorial_screen.*
import kotlinx.android.synthetic.main.toolbar.*

class TutorialScreenActivity : AppCompatActivity(), View.OnClickListener {
    private var tutorialScreen: String = ""
    lateinit var pref: PrefHelper
    lateinit var countDownTimer: CountDownTimer

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_tutorial_screen)

        listner()
        initAll()
    }

    private fun initAll() {
        pref = PrefHelper(this)

        if (pref.tutorialCount == 0) {
            txtNextTutorial.visibility = View.GONE
            txtSkipTutorial.visibility = View.GONE
            pref.tutorialCount = 1
            countDownTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    when((millisUntilFinished/1000).toString()){
                        "8" -> imgTutorial.setImageResource(R.drawable.tutorial_soft_screen)
                        "6" -> imgTutorial.setImageResource(R.drawable.tutorial_hard_screen)
                        "4" -> imgTutorial.setImageResource(R.drawable.tutorial_free_screen)
                        "2" -> imgTutorial.setImageResource(R.drawable.tutorial_drug_screen)
                    }
                }

                override fun onFinish() {
                    countDownTimer.cancel()
                    startActivity(Intent(this@TutorialScreenActivity, DashboardActivity::class.java))
                    finish()
                }

            }.start()
        }
    }

    private fun listner() {
        txtSkipTutorial.setOnClickListener(this)
        txtNextTutorial.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == txtSkipTutorial) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        if (v == txtNextTutorial) {
            if (tutorialScreen == "") {
                tutorialScreen = "soft"
                imgTutorial.setImageResource(R.drawable.tutorial_soft_screen)
//                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_soft_screen))
            } else if (tutorialScreen == "soft") {
                tutorialScreen = "hard"
                imgTutorial.setImageResource(R.drawable.tutorial_hard_screen)
//                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_hard_screen))
            } else if (tutorialScreen == "hard") {
                tutorialScreen = "free"
                imgTutorial.setImageResource(R.drawable.tutorial_free_screen)
//                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_free_screen))
            } else if (tutorialScreen == "free") {
                tutorialScreen = "drug"
                txtNextTutorial.text = "Finish"
                imgTutorial.setImageResource(R.drawable.tutorial_drug_screen)
//                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_drug_screen))
            } else {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }
}