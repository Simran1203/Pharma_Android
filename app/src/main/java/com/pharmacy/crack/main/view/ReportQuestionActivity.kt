package com.pharmacy.crack.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pharmacy.crack.R
import com.pharmacy.crack.utils.editTextBackground
import com.pharmacy.crack.utils.hideKeyBoard
import com.pharmacy.crack.utils.setFullScreen
import com.pharmacy.crack.utils.showToast
import kotlinx.android.synthetic.main.activity_report_question.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.constarintSignup

class ReportQuestionActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var que: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_report_question)


        initBackground()
        consReportQue.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideKeyBoard(this)
            }
        }

        txtSubmitReportQue.setOnClickListener(this)
    }

    private fun initBackground() {
        editTextBackground(txtCatAns, "#FFFFA4", "#ADADAD")
        editTextBackground(txtQuestAns, "#FFFFA4", "#ADADAD")
        que = intent.getStringExtra("que").toString()
        txtQuestAns.text = que
    }

    override fun onClick(v: View?) {
        if(v==txtSubmitReportQue){
            if(edtReportAns.text.toString().isEmpty()){
                showToast(this,"Please Enter your Answer")
            }else{
                showToast(this,"success")
            }
        }
    }

}