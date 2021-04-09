package com.pharmacy.crack.main.view.GameActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.CategorySpinActivity
import com.pharmacy.crack.main.view.QuestionActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_solo.*
import kotlinx.android.synthetic.main.toolbar.*

class SoloActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_solo)

        initAll()
    }

    private fun initAll() {
        imgBackToolbar.visibility = View.GONE
        txtToolbar.text = "Solo Game"
        txtStartGame.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtStartGame){
            startActivity(Intent(this,CategorySpinActivity::class.java))
        }
    }
}