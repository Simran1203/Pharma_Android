package com.pharmacy.crack.main.view.splashActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.LoginSignUpActivity.LoginActivity
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.PrefHelper
import com.pharmacy.crack.utils.setFullScreen

class SplashActivity2 : AppCompatActivity() {
    lateinit var pref : PrefHelper
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_splash2)
        pref = PrefHelper(this)
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_in);

        Handler().postDelayed(
            {
                if(pref.authToken.isNullOrEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }else{
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finishAffinity()
                }
            },2000
        )
    }
}