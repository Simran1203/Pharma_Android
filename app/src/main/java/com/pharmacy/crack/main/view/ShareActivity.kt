package com.pharmacy.crack.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity(), View.OnClickListener {
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