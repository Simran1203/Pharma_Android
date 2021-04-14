package com.pharmacy.crack.main.view.SettingActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_tutorial_screen.*

class TutorialScreenActivity : AppCompatActivity(),View.OnClickListener {
    var tutorialScreen : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_tutorial_screen)

        listner()
    }

    private fun listner() {
        txtSkipTutorial.setOnClickListener(this)
        txtNextTutorial.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtSkipTutorial){
            startActivity(Intent(this,DashboardActivity::class.java))
            finish()
        }
        if(v==txtNextTutorial){
            if(tutorialScreen.equals("")){
                tutorialScreen = "soft"
                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_soft_screen))
            }
            else if(tutorialScreen.equals("soft")){
                tutorialScreen = "hard"
                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_hard_screen))
            }
            else if(tutorialScreen.equals("hard")){
                tutorialScreen = "free"
                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_free_screen))
            }
            else if(tutorialScreen.equals("free")){
                tutorialScreen = "drug"
                txtNextTutorial.text  = "Finish"
                constraintTutorial.setBackground(ContextCompat.getDrawable(this,R.drawable.tutorial_drug_screen))
            }
            else {
                startActivity(Intent(this,DashboardActivity::class.java))
                finish()
            }
        }
    }
}