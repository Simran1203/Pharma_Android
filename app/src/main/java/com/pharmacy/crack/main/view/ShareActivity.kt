package com.pharmacy.crack.main.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity(), View.OnClickListener {
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_share)

        listner()
    }

    private fun listner() {
        imgCloseShare.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v==imgCloseShare){
            super.onBackPressed()
        }
    }
}