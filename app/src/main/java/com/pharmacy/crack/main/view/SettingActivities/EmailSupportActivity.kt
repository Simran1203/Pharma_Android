package com.pharmacy.crack.main.view.SettingActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToasts
import kotlinx.android.synthetic.main.activity_email_support.*
import kotlinx.android.synthetic.main.activity_submit_question.*
import kotlinx.android.synthetic.main.toolbar.*

class EmailSupportActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_email_support)
        initAll()

        txtSubmitContact.setOnClickListener(this)
    }

    private fun initAll() {

        txtToolbar.setText("Contact Us")

        constantEmail.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                    hideKeyBoard(this)
            }
        }
        imgBackToolbar.setOnClickListener {
            super.onBackPressed()
        }

        edtmessage.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, p1: MotionEvent?): Boolean {
                if(edtmessage.hasFocus()){
                    v?.parent?.requestDisallowInterceptTouchEvent(true)
                    when (p1?.getAction()!! and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_SCROLL -> {
                            v!!.parent.requestDisallowInterceptTouchEvent(false)
                            return true
                        }
                    }
                }
                return false
            }

        })
    }

    override fun onClick(v: View?) {
        if(v==txtSubmitContact){
            if(edtmessage.text.toString().trim().isEmpty()){
                showToasts("Please write something.")
            }
        }
        if(v==imgBackToolbar){
            imgBackToolbar.setOnClickListener(this)
        }
    }

}