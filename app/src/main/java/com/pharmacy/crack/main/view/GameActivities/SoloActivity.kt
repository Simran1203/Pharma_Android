package com.pharmacy.crack.main.view.GameActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.toolbar.*

class SoloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_solo)

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "Solo Game"

    }
}