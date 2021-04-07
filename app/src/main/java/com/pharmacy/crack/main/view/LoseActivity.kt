package com.pharmacy.crack.main.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.toolbar.*

class LoseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_lose)

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "You Lose"

        txtToolbar.setTextColor(Color.parseColor("#FFFFFF"));

    }
}