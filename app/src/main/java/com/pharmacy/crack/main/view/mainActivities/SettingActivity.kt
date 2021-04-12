package com.pharmacy.crack.main.view.mainActivities

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.SettingActivities.ChangeProfileActivity
import com.pharmacy.crack.main.view.SettingActivities.EmailSupportActivity
import com.pharmacy.crack.main.view.TutorialScreenActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import java.text.DateFormat


class SettingActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_setting)

        imgBackSetting.setOnClickListener(this)
        txtChangeProfile.setOnClickListener(this)
        txtEmaiSupport.setOnClickListener(this)
        txtTutorial.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==imgBackSetting){
            super.onBackPressed()
        }
        if(v==txtChangeProfile){
            startActivity(Intent(this,ChangeProfileActivity::class.java))
        }
        if(v==txtEmaiSupport){
            startActivity(Intent(this,EmailSupportActivity::class.java))
        }
        if(v==txtTutorial){
            startActivity(Intent(this,TutorialScreenActivity::class.java))
        }
    }

}