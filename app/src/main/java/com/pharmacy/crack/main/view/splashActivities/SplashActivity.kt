package com.pharmacy.crack.main.view.splashActivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen

class SplashActivity : Activity() {
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