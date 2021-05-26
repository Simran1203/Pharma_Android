package com.pharmacy.crack.main.view.mainActivities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_i_q_map.*

class IQMapActivity : AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_q_map)
        setFullScreen(this)
        initClick()
    }

    private fun initClick() {
        btnContinueIQMap.setOnClickListener(this)
        imgBackIQMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==btnContinueIQMap){
            super.onBackPressed()
        }
        else if(v==imgBackIQMap){
            super.onBackPressed()
        }
    }
}