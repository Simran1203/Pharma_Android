package com.pharmacy.crack.main.view.SettingActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.editTextBackground
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.activity_forget_pasword.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.relMonth
import kotlinx.android.synthetic.main.toolbar.*

class ChangeProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_change_profile)

        initAll()

    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.setText("Change Profile")


        constarintProfile.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }

        })

    }
}