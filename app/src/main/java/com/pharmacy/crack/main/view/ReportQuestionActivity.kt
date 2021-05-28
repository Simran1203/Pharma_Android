package com.pharmacy.crack.main.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.*
import kotlinx.android.synthetic.main.activity_email_support.*
import kotlinx.android.synthetic.main.activity_report_question.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.constarintSignup

class ReportQuestionActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var que: String
    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_report_question)


        initBackground()

        edtReportAns.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, p1: MotionEvent?): Boolean {
                if(edtReportAns.hasFocus()){
                    v?.parent?.requestDisallowInterceptTouchEvent(true)
                    when (p1?.action!! and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_SCROLL -> {
                            v!!.parent.requestDisallowInterceptTouchEvent(false)
                            return true
                        }
                    }
                }
                return false
            }

        })

        consReportQue.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        txtSubmitReportQue.setOnClickListener(this)
        imgBackreportQuest.setOnClickListener(this)
    }

    private fun initBackground() {
        editTextBackground(txtCatAns, "#FFFFA4", "#ADADAD")
        editTextBackground(txtQuestAns, "#FFFFA4", "#ADADAD")
        que = intent.getStringExtra("que").toString()
        txtQuestAns.text = que
    }

    override fun onClick(v: View?) {
        if(v==txtSubmitReportQue){
            if(edtReportAns.text.toString().trim().isEmpty()){
                showToasts("Please write something.")
            }else{
                startActivity(Intent(this,DashboardActivity::class.java))
                finishAffinity()
//                showToast(this,"success")
            }
        }
        else if(v==imgBackreportQuest){
            super.onBackPressed()
        }
    }

}