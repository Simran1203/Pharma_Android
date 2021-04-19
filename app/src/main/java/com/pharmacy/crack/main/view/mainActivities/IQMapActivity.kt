package com.pharmacy.crack.main.view.mainActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen

class IQMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_q_map)
        setFullScreen(this)
    }
}