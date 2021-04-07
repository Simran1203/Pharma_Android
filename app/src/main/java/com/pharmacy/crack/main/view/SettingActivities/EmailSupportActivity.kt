package com.pharmacy.crack.main.view.SettingActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_email_support.*
import kotlinx.android.synthetic.main.toolbar.*

class EmailSupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_email_support)
        initAll()

    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.setText("Contact Us")

        constantEmail.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                    hideKeyBoard(this)
            }
        }
    }
}