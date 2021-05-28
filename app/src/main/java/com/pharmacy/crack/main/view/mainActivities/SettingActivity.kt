package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.LoginSignUpActivity.LoginActivity
import com.pharmacy.crack.main.view.SettingActivities.ChangeProfileActivity
import com.pharmacy.crack.main.view.SettingActivities.EmailSupportActivity
import com.pharmacy.crack.main.view.SettingActivities.TutorialScreenActivity
import com.pharmacy.crack.main.view.TermsConditionActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity(),View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_setting)

        imgBackSetting.setOnClickListener(this)
        txtChangeProfile.setOnClickListener(this)
        txtEmaiSupport.setOnClickListener(this)
        txtTutorial.setOnClickListener(this)
        txtTermsCondition.setOnClickListener(this)
        txtLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtChangeProfile){
            startActivity(Intent(this,ChangeProfileActivity::class.java))
        }
        if(v==txtEmaiSupport){
            startActivity(Intent(this,EmailSupportActivity::class.java))
        }
        if(v==txtTutorial){
            startActivity(Intent(this, TutorialScreenActivity::class.java))
        }
        if(v==txtTermsCondition){
            startActivity(Intent(this, TermsConditionActivity::class.java))
        }
        if(v==txtLogout){
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
        if(v==imgBackSetting){
            super.onBackPressed()
        }
    }

}