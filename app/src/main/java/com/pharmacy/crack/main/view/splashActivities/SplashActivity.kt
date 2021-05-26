package com.pharmacy.crack.main.view.splashActivities

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen

class SplashActivity : Activity() {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_splash)

       Handler().postDelayed(
               {
                   startActivity(Intent(this, SplashActivity2::class.java))
                   finishAffinity()
               },2000
       )
    }
}