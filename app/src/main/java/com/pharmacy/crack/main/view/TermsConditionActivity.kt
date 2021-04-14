package com.pharmacy.crack.main.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pharmacy.crack.R
import kotlinx.android.synthetic.main.activity_terms_condition.*


class TermsConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.YELLOW)
        }
        setContentView(R.layout.activity_terms_condition)

        webTerm.loadUrl("https://www.agicent.com/")
        webTerm.settings.setSupportZoom(true)
        webTerm.settings.javaScriptEnabled = true

//        imgBackterms.setOnClickListener { super.onBackPressed() }
    }
}