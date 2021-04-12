package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_tutorial)
        listner()
    }

    private fun listner() {
        txtSkipTutorial.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==txtSkipTutorial){
            startActivity(Intent(this,DashboardActivity::class.java))
            finish()
        }
    }
}