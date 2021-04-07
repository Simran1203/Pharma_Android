package com.pharmacy.crack.main.view.splashActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.LoginSignUpActivity.LoginActivity
import com.pharmacy.crack.utils.setFullScreen

class SplashActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_splash2)
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_in);

        Handler().postDelayed(
            {
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            },2000
        )
    }
}