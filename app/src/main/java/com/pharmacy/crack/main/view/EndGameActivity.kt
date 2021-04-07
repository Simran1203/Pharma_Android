package com.pharmacy.crack.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen

class EndGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_end_game)
    }
}