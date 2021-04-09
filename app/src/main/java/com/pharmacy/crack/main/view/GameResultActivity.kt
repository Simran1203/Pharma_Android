package com.pharmacy.crack.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.toolbar.*

class GameResultActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_game_result)

        initAll()
    }

    private fun initAll() {
        txtToolbar.setText("Game Result")
        imgBackToolbar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==imgBackToolbar){
            super.onBackPressed()
        }
    }
}